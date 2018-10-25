package com.haoniu.zzx.app_ts.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.awen.photo.photopick.controller.PhotoPagerConfig;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.adapter.CommentListAdapter;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.HttpUtils;
import com.haoniu.zzx.app_ts.http.JsonCallback;
import com.haoniu.zzx.app_ts.model.CommentInfo;
import com.haoniu.zzx.app_ts.model.CommentListInfo;
import com.haoniu.zzx.app_ts.utils.QiNiuGlideUtils;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zzx on 2018/04/18/下午 3:29
 */

public class CommentDetailActivity extends BaseActivity implements CommentListAdapter.onMyClickListener {
    @BindView(R.id.rc)
    RecyclerView mRc;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;


    private List<CommentListInfo> mListData = new ArrayList<>();
    private CommentListAdapter mAdapter;

    private int page = 1;//页数
    private int pageNum = 25;//数量
    private String id = "";

    private boolean isFirstOpen = true;//是否第一次进入页面

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_comment);
    }

    @Override
    protected void initView() {
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mRc.setLayoutManager(lm);
        mAdapter = new CommentListAdapter(this, mListData, 1);
        mRc.setAdapter(mAdapter);
        mAdapter.setOnMyClickListener(this);
    }


    /**
     * 评论
     */
    private void getCommentList(final int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("pagenum", pageNum);
        map.put("goodid", id);
        HttpUtils.requestGet(mContext, AppConfig.comment_list, map, new JsonCallback<CommentInfo>(mContext) {
            @Override
            public void onSuccess(Response<CommentInfo> response) {

                if (0 == type) {
                    mListData.clear();
                }
                if (response != null && response.body() != null && response.body().getList().size() > 0) {
                    mTvTitle.setText("评论数(" + response.body().getTotal() + ")");
                    mListData.addAll(response.body().getList());
                    page++;
                }
                mAdapter.notifyDataSetChanged();
                if (type != 0) {
                    mRefreshLayout.finishLoadmore();
                } else {
                    mRefreshLayout.finishRefresh();
                }

            }
        });
    }


    @Override
    protected void initLogic() {
        mRefreshLayout.setDisableContentWhenRefresh(true);//上拉或者下拉时禁止点击
        mRefreshLayout.setDisableContentWhenLoading(true);//上拉或者下拉时禁止点击
        /**
         * 下拉刷新
         */
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                getCommentList(0);
            }
        });

        /**
         * 上拉加载更多
         */
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshLayout) {
                getCommentList(1);
                mRefreshLayout.autoLoadmore(300);
            }
        });

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        id = extras.getString("id");
    }

    @OnClick(R.id.ll_back)
    public void onClick() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstOpen) {
            mRefreshLayout.autoRefresh(300);
            isFirstOpen = false;
        }
    }

    @Override
    public void onMyClick(int position, List<String> mImg) {
        List<String> itemImg = new ArrayList<>();
        for (String s : mImg) {
            itemImg.add(QiNiuGlideUtils.boundary640(s));
        }
        new PhotoPagerConfig.Builder(this)
                .setBigImageUrls((ArrayList<String>) itemImg)      //大图片url,可以是sd卡res，asset，网络图片.
//                        .setSmallImageUrls(ArrayList<String> smallImgUrls)  //小图图片的url,用于大图展示前展示的
//                        .addSingleBigImageUrl(String bigImageUrl)           //一张一张大图add进ArrayList
//                        .addSingleSmallImageUrl(String smallImageUrl)       //一张一张小图add进ArrayList
                .setSavaImage(false)                                 //开启保存图片，默认false
                .setPosition(position)                                     //默认展示第2张图片
//                        .setSaveImageLocalPath("Android/SD/xxx/xxx")        //这里是你想保存大图片到手机的地址,可在手机图库看到，不传会有默认地址
//                        .setBundle(bundle)                                  //传递自己的数据，如果数据中包含java bean，必须实现Parcelable接口
                .setOpenDownAnimate(false)                          //是否开启下滑关闭activity，默认开启。类似微信的图片浏览，可下滑关闭一样
                .build();

    }

    @Override
    public void omMyDetailClick(int position) {

    }
}
