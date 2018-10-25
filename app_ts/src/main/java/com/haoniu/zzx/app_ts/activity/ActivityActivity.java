package com.haoniu.zzx.app_ts.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.ShareDialog;
import com.haoniu.zzx.app_ts.adapter.ActivityAdapter;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.HttpUtils;
import com.haoniu.zzx.app_ts.http.JsonCallback;
import com.haoniu.zzx.app_ts.model.ActivityInfo;
import com.haoniu.zzx.app_ts.model.ColumnsModel;
import com.haoniu.zzx.app_ts.utils.ToastUtils;
import com.haoniu.zzx.app_ts.view.ActivityHeadView;
import com.just.library.IFileUploadChooser;
import com.lzy.okgo.model.Response;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 活动页面
 */
public class ActivityActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.llRight)
    LinearLayout llRight;
    @BindView(R.id.ivRight)
    ImageView ivRight;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mRefreshLayout)
    BGARefreshLayout mRefreshLayout;

    private int flag = 0; //  1 活动进入  2  国家进入
    private String thumb;
    private String id;
    private int page = 1;
    private String url;
    private String title;
    private String desc;
    private String shareUrl;
    private ActivityAdapter activityAdapter;
    private List<ActivityInfo> activityInfos;//

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_activity);
    }

    @Override
    protected void initView() {
        llRight.setVisibility(View.VISIBLE);
        ivRight.setBackgroundResource(R.mipmap.img_share);
        activityInfos = new ArrayList<>();
        activityAdapter = new ActivityAdapter(activityInfos);
        if (flag == 1) {
            addHeadView();
            tvTitle.setText(title);
        } else if (flag == 2) {
            requestContryImg();
        }
        mRefreshLayout.setDelegate(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(activityAdapter);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();
    }

    /**
     * 国家详情
     */
    private void requestContryImg() {
        Map<String, Object> map = new HashMap<>();
        map.put("countryid", id);
        HttpUtils.requestGet(mContext, AppConfig.requestColumndetail, map, new JsonCallback<ColumnsModel>() {
            @Override
            public void onSuccess(Response<ColumnsModel> response) {
                if (response.body() != null) {
                    title = response.body().getNavname();
                    thumb = response.body().getIcon();
                    shareUrl = response.body().getShareUrl();
                    desc = response.body().getDesc();
                    tvTitle.setText(title);
                    addHeadView();
                }
            }
        });
    }

    private void addHeadView() {
        activityAdapter.removeAllHeaderView();
        ActivityHeadView headView = new ActivityHeadView(mContext, flag, thumb, title);
        activityAdapter.addHeaderView(headView);
        activityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initLogic() {
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        flag = extras.getInt("flag");
        id = extras.getString("id");
        thumb = extras.getString("thumb");
        title = extras.getString("title");
        shareUrl = extras.getString("shareUrl");
        desc = extras.getString("desc");
        if (flag == 1) {
            url = AppConfig.requestActivities;
        } else if (flag == 2) {
            url = AppConfig.requestColumns;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        activityInfos.clear();
        requestActivityList();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        page++;
        requestActivityList();
        return true;
    }

    private void requestActivityList() {
        Map<String, Object> map = new HashMap<>();
        if (flag == 1) {
            map.put("activityid", id);
        } else if (flag == 2) {
            map.put("countryid", id);
        }
        map.put("page", page);
        HttpUtils.requestGet(mContext, url, map, new JsonCallback<List<ActivityInfo>>() {
            @Override
            public void onSuccess(Response<List<ActivityInfo>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    activityInfos.addAll(response.body());
                }
                activityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
            }

            @Override
            public void onError(Response<List<ActivityInfo>> response) {
                super.onError(response);
                if (page > 1) {
                    page--;
                }
            }
        });
    }

    @OnClick({R.id.ll_back, R.id.llRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.llRight:
                showShareDialog();
                break;
        }
    }

    private ShareDialog shareDialog;
    private UMWeb umWeb;

    private void showShareDialog() {
        if (thumb == null || title == null || desc == null) {
            requestContryImg();
            ToastUtils.showTextToast(mContext, "正在获取分享信息...");
            return;
        }
        final UMImage shareImage;
        shareImage = new UMImage(mContext, thumb);//网络图片
        umWeb = new UMWeb(shareUrl);
        umWeb.setTitle(title);
        umWeb.setDescription(desc);
        umWeb.setThumb(shareImage);
        if (shareDialog == null) {
            shareDialog = new ShareDialog(mContext);
            shareDialog.setClick(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new ShareAction(ActivityActivity.this)
                            .setPlatform(SHARE_MEDIA.QQ)//传入平台
                            .withMedia(umWeb)
                            .setCallback(shareListener)//回调监听器
                            .share();
                    shareDialog.dismiss();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new ShareAction(ActivityActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                            .withMedia(umWeb)
                            .setCallback(shareListener)//回调监听器
                            .share();
                    shareDialog.dismiss();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new ShareAction(ActivityActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                            .withMedia(umWeb)
                            .setCallback(shareListener)//回调监听器
                            .share();
                    shareDialog.dismiss();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    boolean checkSinaVersion = isWeiboInstalled();
                    if (!checkSinaVersion) {
                        ToastUtils.showTextToast(mContext, "请安装微博客户端");
                        return;
                    }
                    new ShareAction(ActivityActivity.this)
                            .setPlatform(SHARE_MEDIA.SINA)//传入平台
                            .withMedia(umWeb)
                            .setCallback(shareListener)//回调监听器
                            .share();
                    shareDialog.dismiss();
                }
            });
        }
        shareDialog.show();
    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            platform.toString();
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showTextToast(mContext, "分享成功!");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showTextToast(mContext, t.getMessage().toString());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showTextToast(mContext, "分享已取消!");
        }
    };

    public boolean isWeiboInstalled() {
        PackageManager pm;
        if ((pm = mContext.getApplicationContext().getPackageManager()) == null) {
            return false;
        }
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo info : packages) {
            String name = info.packageName.toLowerCase(Locale.ENGLISH);
            if ("com.sina.weibo".equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
