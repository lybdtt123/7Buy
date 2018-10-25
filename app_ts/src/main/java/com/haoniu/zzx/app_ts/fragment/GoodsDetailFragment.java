package com.haoniu.zzx.app_ts.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awen.photo.photopick.controller.PhotoPagerConfig;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.ShareDialog;
import com.haoniu.zzx.app_ts.activity.CommentDetailActivity;
import com.haoniu.zzx.app_ts.activity.GoodsListActivity;
import com.haoniu.zzx.app_ts.activity.WebviewActivity;
import com.haoniu.zzx.app_ts.adapter.CommentListAdapter;
import com.haoniu.zzx.app_ts.adapter.GoodsLabelAdapter;
import com.haoniu.zzx.app_ts.adapter.RecommendAdapter;
import com.haoniu.zzx.app_ts.dialog.SpecDialog;
import com.haoniu.zzx.app_ts.http.ApiClient;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.AppContext;
import com.haoniu.zzx.app_ts.http.HttpUtils;
import com.haoniu.zzx.app_ts.http.JsonCallback;
import com.haoniu.zzx.app_ts.http.ResultListener;
import com.haoniu.zzx.app_ts.model.CommentInfo;
import com.haoniu.zzx.app_ts.model.CommentListInfo;
import com.haoniu.zzx.app_ts.model.GoodsDetailModel;
import com.haoniu.zzx.app_ts.model.RecommendModel;
import com.haoniu.zzx.app_ts.model.SpecificationsModel;
import com.haoniu.zzx.app_ts.utils.GoodsBannerImageLoader;
import com.haoniu.zzx.app_ts.utils.PreferenceUtils;
import com.haoniu.zzx.app_ts.utils.QiNiuGlideUtils;
import com.haoniu.zzx.app_ts.utils.ToastUtils;
import com.haoniu.zzx.app_ts.view.MyRecyclerView;
import com.lzy.okgo.model.Response;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.editorpage.ShareActivity;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2017/12/14 0014.
 */

public class GoodsDetailFragment extends BaseFragment {

    @BindView(R.id.mBanner)
    Banner mBanner;
    @BindView(R.id.tvGoodsName)
    TextView tvGoodsName;
    @BindView(R.id.tvGoodsMarketPrice)
    TextView tvGoodsMarketPrice;
    @BindView(R.id.tvGoodsInlandPrice)
    TextView tvGoodsInlandPrice;
    @BindView(R.id.tvGoodsPostType)
    TextView tvGoodsPostType;
    @BindView(R.id.tvGoodsSales)
    TextView tvGoodsSales;
    @BindView(R.id.tvGoodsCountry)
    TextView tvGoodsCountry;
    @BindView(R.id.labelRecyclerView)
    RecyclerView labelRecyclerView;
    @BindView(R.id.tvGoodsSpecifications)
    TextView tvGoodsSpecifications;
    @BindView(R.id.recommendRecyclerView)
    RecyclerView recommendRecyclerView;
    @BindView(R.id.ivGoodsBrand)
    ImageView ivGoodsBrand;
    @BindView(R.id.tvGoodsBrandName)
    TextView tvGoodsBrandName;
    @BindView(R.id.tvGoodsBrandDescribe)
    TextView tvGoodsBrandDescribe;
    @BindView(R.id.mWebView)
    WebView mWebView;
    @BindView(R.id.llGoodsBrand)
    LinearLayout llGoodsBrand;
    @BindView(R.id.llGoodsDetail)
    LinearLayout llGoodsDetail;
    @BindView(R.id.my_rc_comment)
    MyRecyclerView mMyRcComment;
    Unbinder unbinder;
    @BindView(R.id.llayout_line)
    LinearLayout mLlayoutLine;
    @BindView(R.id.llayout_content)
    LinearLayout mLlayoutContent;
    @BindView(R.id.tv_content_all)
    TextView tvContentAll;
    @BindView(R.id.tvGoodsLiJianPrice)
    TextView tvGoodsLiJianPrice;
    @BindView(R.id.img_tag)
    TextView mTvTag;
    @BindView(R.id.tv_tag1)
    TextView mTvTag1;
    @BindView(R.id.rllayout_tag)
    RelativeLayout mRllayoutTag;


    private GoodsDetailModel detailModel;
    private String id;

    private SpecDialog specDialog;
    private SpecificationsModel specificationsModel;

    private CommentListAdapter mAdapter;//评论适配器
    private List<CommentListInfo> mListData = new ArrayList<>();

    private int page = 1;//页数
    private int pageNum = 2;//这个默认显示两条


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_goodsdetail;
    }


    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        detailModel = (GoodsDetailModel) bundle.get("detailModel");
        id = bundle.getString("id");
    }

    @Override
    protected void initData() {
        super.initData();
        initBanner();
        initGoodsInfo();
        initLabel();
        initRecommend();
        initBrand();
        getCommentList();
        requestRecommendGoods();
        requestSpec();
        initComment();
    }


    /**
     * 评论
     */
    private void getCommentList() {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("pagenum", pageNum);
        map.put("goodid", id);
        HttpUtils.requestGet(mContext, AppConfig.comment_list, map, new JsonCallback<CommentInfo>(mContext) {
            @Override
            public void onSuccess(Response<CommentInfo> response) {
                if (response != null && response.body() != null && response.body().getList().size() > 0) {
                    mListData.addAll(response.body().getList());
                    if (tvContentAll != null){
                        tvContentAll.setText("评论列表(" + (TextUtils.isEmpty(response.body().getTotal()) ? "" : response.body().getTotal()) + ")");
                    }
                }
                if (mListData != null && mListData.size() > 0) {
                    mLlayoutLine.setVisibility(View.GONE);
                    mMyRcComment.setVisibility(View.VISIBLE);
                    mLlayoutContent.setVisibility(View.VISIBLE);
                } else {
                    mLlayoutLine.setVisibility(View.GONE);
                    mMyRcComment.setVisibility(View.GONE);
                    mLlayoutContent.setVisibility(View.GONE);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Response<CommentInfo> response) {
                super.onError(response);
                if (getActivity() != null) {
                    if (mListData != null && mListData.size() > 0) {
                        mLlayoutLine.setVisibility(View.GONE);
                        mMyRcComment.setVisibility(View.VISIBLE);
                        mLlayoutContent.setVisibility(View.VISIBLE);
                    } else {
                        mLlayoutLine.setVisibility(View.GONE);
                        mMyRcComment.setVisibility(View.GONE);
                        mLlayoutContent.setVisibility(View.GONE);
                    }
                }
                ToastUtils.showTextToast(mContext, response.message());
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    /**
     * 评论
     */
    private void initComment() {
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mMyRcComment.setLayoutManager(lm);
        mAdapter = new CommentListAdapter(mContext, mListData, 0);
        mMyRcComment.setAdapter(mAdapter);
        mAdapter.setOnMyClickListener(new CommentListAdapter.onMyClickListener() {
            @Override
            public void onMyClick(int position, List<String> mImg) {
                //TODO 这里传图片去放大
                List<String> itemImg = new ArrayList<>();
                for (String s : mImg) {
                    itemImg.add(QiNiuGlideUtils.boundary640(s));
                }
                new PhotoPagerConfig.Builder(getActivity())
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
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                startActivity(CommentDetailActivity.class, bundle);
            }
        });

    }

    /**
     * 商品
     */
    @SuppressLint("WrongConstant")
    private void initGoodsInfo() {
        if (detailModel.getGoods() != null) {
            if (!checkEmpty(detailModel.getGoods().getTitle())) {
                tvGoodsName.setText(detailModel.getGoods().getTitle());
            }
            double zPrice = TextUtils.isEmpty(detailModel.getGoods().getInlandprice()) ? 0.0 : Double.parseDouble(detailModel.getGoods().getInlandprice());
            if (detailModel.getGoods().getMinprice() == detailModel.getGoods().getMaxprice()) {
                tvGoodsMarketPrice.setText("￥" + detailModel.getGoods().getMinprice());
                tvGoodsLiJianPrice.setText("立减 " + String.format("%.2f", (zPrice - detailModel.getGoods().getMinprice())));
            } else {
                tvGoodsMarketPrice.setText("￥" + detailModel.getGoods().getMinprice() + "~" + detailModel.getGoods().getMaxprice());
                tvGoodsLiJianPrice.setText("立减 " + String.format("%.2f", (zPrice - detailModel.getGoods().getMaxprice())));
            }
            if (TextUtils.isEmpty(detailModel.getGoods().getLayer())) {
                mTvTag.setVisibility(View.GONE);
            } else {
                mTvTag.setText(detailModel.getGoods().getLayer());
                mTvTag.setVisibility(View.VISIBLE);
            }

            if (TextUtils.isEmpty(detailModel.getGoods().getSaleno())) {
                mTvTag1.setText("双十一促销 ");
            } else {
                Double price;
                try {
                    price = Double.parseDouble(detailModel.getGoods().getSaleno());
                } catch (Exception e) {
                    price = 0.00;
                }

                if (0.00 == price) {
                    mTvTag1.setText("双十一促销 ");
                } else {
                    mTvTag1.setText("双十一促销 | 下单立减 " + detailModel.getGoods().getSaleno() + " 元");
                }
            }
            int isDiscount = Integer.parseInt(detailModel.getGoods().getIsdiscount());
            long disTime = Long.parseLong(detailModel.getGoods().getIsdiscount_time()) * 1000;//毫秒
            if (isDiscount > 0 && disTime > System.currentTimeMillis()) {
                tvGoodsInlandPrice.getPaint().setAntiAlias(true);
                tvGoodsInlandPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                tvGoodsInlandPrice.setText("原价：" + detailModel.getGoods().getInlandprice());
            } else if (Float.parseFloat(detailModel.getGoods().getInlandprice()) > 0) {
                tvGoodsInlandPrice.setText("原价：" + detailModel.getGoods().getInlandprice());
            } else {
                tvGoodsInlandPrice.setVisibility(View.GONE);
            }
            tvGoodsSales.setText("销量：" + detailModel.getGoods().getSales() + detailModel.getGoods().getUnit());
            tvGoodsCountry.setText("产地：" + detailModel.getGoods().getCountry());
            if (!checkEmpty(detailModel.getGoods().getContent())) {
                mWebView.loadDataWithBaseURL(null, detailModel.getGoods().getContent() + "", "text/html", "UTF-8", null);
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.setWebChromeClient(new WebChromeClient());
                llGoodsDetail.setVisibility(View.VISIBLE);
            } else {
                llGoodsDetail.setVisibility(View.GONE);
            }
        }
    }

    private GoodsLabelAdapter labelAdapter;

    /**
     * label
     */
    private void initLabel() {
        labelAdapter = new GoodsLabelAdapter(detailModel.getLabelname());
//        labelRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        labelRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        labelRecyclerView.setAdapter(labelAdapter);
    }

    /**
     * 品牌
     */
    @SuppressLint("WrongConstant")
    private void initBrand() {
        if (detailModel.getShopdetail() != null) {
            if (!checkEmpty(detailModel.getShopdetail().getMerchname())) {
                tvGoodsBrandName.setText(detailModel.getShopdetail().getMerchname());
            }
            if (!checkEmpty(detailModel.getShopdetail().getDescription())) {
                tvGoodsBrandDescribe.setText(detailModel.getShopdetail().getDescription());
            }
            if (!checkEmpty(detailModel.getShopdetail().getLogo())) {
                if (detailModel.getShopdetail().getLogo().startsWith("http")) {
                    displayImage(detailModel.getShopdetail().getLogo(), ivGoodsBrand);
                } else {
                    displayImage("http://www.chanwu7.com/attachment/" + detailModel.getShopdetail().getLogo(), ivGoodsBrand);
                }
            }
            llGoodsBrand.setVisibility(View.VISIBLE);
        } else {
            llGoodsBrand.setVisibility(View.GONE);
        }
    }

    /**
     * banner
     */
    private void initBanner() {
        ViewGroup.LayoutParams layoutParams = mRllayoutTag.getLayoutParams();
        layoutParams.height = (int) (DensityUtils.getWidthInPx(mContext) + DensityUtils.dip2px(mContext, 100));
        mRllayoutTag.setLayoutParams(layoutParams);
        mBanner.setImageLoader(new GoodsBannerImageLoader());
        mBanner.setBannerAnimation(Transformer.Default);
        mBanner.isAutoPlay(true);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setDelayTime(3000);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setImages(detailModel.getThumbs());
        mBanner.start();
    }


    @OnClick({R.id.llGoodsShare, R.id.tvGoodsWebsite, R.id.tvGoodsMore, R.id.tvGoodsSpecifications})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llGoodsShare://分享
                if (detailModel != null) {
                    showShareDialog();
                }
                break;
            case R.id.tvGoodsWebsite://官网正品直发
                if (TextUtils.isEmpty(detailModel.getGoods().getLink())) {
                    ToastUtils.showTextToast(mContext, "暂无官网链接");
                    return;
                }
                startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", detailModel.getGoods().getLink()));
                break;
            case R.id.tvGoodsMore://更多产品
                startActivity(new Intent(mContext, GoodsListActivity.class)
                        .putExtra("id", detailModel.getShopdetail().getId()).putExtra("shop", true));
                break;
            case R.id.tvGoodsSpecifications://产品规格
                operateSpecDialog(false);
                break;
        }
    }


    private ShareDialog shareDialog;
    private UMWeb umWeb;

    private void showShareDialog() {
        final UMImage shareImage;
        shareImage = new UMImage(mContext, detailModel.getGoods().getThumb());//网络图片
        umWeb = new UMWeb("http://www.chanwu7.com/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=goods.detail&id=" + detailModel.getGoods().getId());
        umWeb.setTitle(detailModel.getGoods().getTitle());
        umWeb.setDescription(detailModel.getGoods().getDescription());
        umWeb.setThumb(shareImage);
        if (shareDialog == null) {
            shareDialog = new ShareDialog(mContext);
            shareDialog.setClick(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new ShareAction(getActivity())
                            .setPlatform(SHARE_MEDIA.QQ)//传入平台
                            .withMedia(umWeb)
                            .setCallback(shareListener)//回调监听器
                            .share();
                    shareDialog.dismiss();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new ShareAction(getActivity())
                            .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                            .withMedia(umWeb)
                            .setCallback(shareListener)//回调监听器
                            .share();
                    shareDialog.dismiss();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new ShareAction(getActivity())
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
                    new ShareAction(getActivity())
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
            shareSuccessData();
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

    /**
     * 分享成功后回调接口
     */
    private void shareSuccessData() {
        Map<String, Object> map = new HashMap<>();
        map.put("cookie", PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, ""));
        ApiClient.requestNetHandle(mContext, AppConfig.SHARE_SUCCESS, "分享成功...", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {

            }

            @Override
            public void onFailure(String msg) {
                showToast(msg);
            }
        });

    }

    /**
     * 操作规格对话框
     *
     * @param isAdd 是否加入购物车咯
     */
    public void operateSpecDialog(boolean isAdd) {
        if (specificationsModel != null) {
            specDialog.setAddCar(isAdd);
            specDialog.show();
        } else {
            requestSpec();
        }
    }

    /**
     * 推荐
     */
    private void initRecommend() {
        recommendModels = new ArrayList<>();
        recommendAdapter = new RecommendAdapter(recommendModels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recommendRecyclerView.setLayoutManager(linearLayoutManager);
        recommendRecyclerView.setHasFixedSize(true);
        recommendRecyclerView.setNestedScrollingEnabled(false);
        recommendRecyclerView.setAdapter(recommendAdapter);
        addFootView();
    }

    /**
     * 加尾部
     */
    private void addFootView() {
        recommendAdapter.removeAllFooterView();
//        RecommendFootView footView = new RecommendFootView(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_recommend_foot, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(DensityUtils.dip2px(mContext, 100)
                , DensityUtils.dip2px(mContext, 100));
        layoutParams.height = (int) ((DensityUtils.getWidthInPx(mContext) - DensityUtils.dip2px(mContext, 20)) / 3)
                + DensityUtils.dip2px(mContext, 60);
        view.setLayoutParams(layoutParams);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, GoodsListActivity.class)
                        .putExtra("id", detailModel.getGoods().getTcate()));
            }
        });
        recommendAdapter.addFooterView(view, -1, 0);
        recommendAdapter.notifyDataSetChanged();
    }

    private List<RecommendModel> recommendModels;
    private RecommendAdapter recommendAdapter;

    /**
     * 推荐商品
     */
    private void requestRecommendGoods() {
        Map<String, Object> map = new HashMap<>();
        map.put("goodid", id);
        HttpUtils.requestGet(mContext, AppConfig.requestGoodsRecommand, map, new JsonCallback<List<RecommendModel>>(mContext) {
            @Override
            public void onSuccess(Response<List<RecommendModel>> response) {
                if (response != null && response.body() != null && response.body().size() > 0) {
                    recommendModels.addAll(response.body());
                }
                recommendAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 规格
     */
    private void requestSpec() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        HttpUtils.requestGet(mContext, AppConfig.requestGoodsSpecifications, map, new JsonCallback<SpecificationsModel>(mContext) {
            @Override
            public void onSuccess(Response<SpecificationsModel> response) {
                if (response != null && response.body() != null) {
                    specificationsModel = response.body();
                    initSpecDialog();
                }
            }
        });
    }

    private SpecificationsModel.OptionsBean mOptionsBean;
    private int goodsNum = -1;

    private void initSpecDialog() {
        if (mContext == null)
            return;
        specDialog = new SpecDialog(mContext, specificationsModel);
        specDialog.setSpecInfo(new SpecDialog.SpecInfo() {
            @Override
            public void specDetailInfo(SpecificationsModel.OptionsBean optionsBean, int num) {
                if (optionsBean != null) {
                    mOptionsBean = optionsBean;
                    goodsNum = num;
                    tvGoodsSpecifications.setText("已选：数量 x" + goodsNum + "  " + optionsBean.getTitle());
                }
            }

            @Override
            public void specDetailInfo(int num) {
                goodsNum = num;
                tvGoodsSpecifications.setText("已选：数量 x" + goodsNum);
            }
        });
        if (specificationsModel.getOptions() == null || specificationsModel.getOptions().size() == 0) {
            goodsNum = 1;
            tvGoodsSpecifications.setText("已选：数量 x" + goodsNum);
        }
    }

    public boolean isHasOption() {
        if (specificationsModel == null) {
            return false;
        } else if (specificationsModel.getOptions() == null || specificationsModel.getOptions().size() == 0) {
            return false;
        }
        return true;
    }

    public SpecificationsModel.OptionsBean getmOptionsBean() {
        return mOptionsBean;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10103) {
            UMShareAPI.get(mContext).onActivityResult(requestCode, resultCode, data);
        }
    }

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
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart("GoodsDetail");
        MobclickAgent.onResume(mContext);
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd("GoodsDetail");
        MobclickAgent.onPause(mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
