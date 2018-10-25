package com.haoniu.zzx.app_ts.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.activity.ActivityActivity;
import com.haoniu.zzx.app_ts.activity.DianShangHuiActivity;
import com.haoniu.zzx.app_ts.activity.GlobalBuyActivity;
import com.haoniu.zzx.app_ts.activity.GoodsDetailActivity;
import com.haoniu.zzx.app_ts.activity.GoodsListActivity;
import com.haoniu.zzx.app_ts.activity.WebviewActivity;
import com.haoniu.zzx.app_ts.adapter.AdvantageAdapter;
import com.haoniu.zzx.app_ts.adapter.GoodsBottomItemAdapter;
import com.haoniu.zzx.app_ts.adapter.HomeNewAdapter;
import com.haoniu.zzx.app_ts.adapter.VBrandHotNewAdapter;
import com.haoniu.zzx.app_ts.adapter.VFixIconNewAdapter;
import com.haoniu.zzx.app_ts.http.ApiClient;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.HttpUtils;
import com.haoniu.zzx.app_ts.http.JsonCallback;
import com.haoniu.zzx.app_ts.http.ResultListener;
import com.haoniu.zzx.app_ts.model.AdvantageModel;
import com.haoniu.zzx.app_ts.model.HomeNewDataInfo;
import com.haoniu.zzx.app_ts.model.HomeNewInfo;
import com.haoniu.zzx.app_ts.model.HotBrandChangeBatchInfo;
import com.haoniu.zzx.app_ts.model.MainClassifyModel;
import com.haoniu.zzx.app_ts.model.NormalModel;
import com.haoniu.zzx.app_ts.utils.GlideImageLoader;
import com.haoniu.zzx.app_ts.utils.L;
import com.haoniu.zzx.app_ts.utils.SPUtils;
import com.haoniu.zzx.app_ts.utils.ScreenUtil;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.lzy.okgo.model.Response;
import com.squareup.picasso.Picasso;
import com.umeng.commonsdk.debug.E;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2017/9/27 0027.
 */

public class HomeNestedFragment extends BaseFragment {

    private Banner mBanner;
    private RecyclerView recycleViewIcon;
    private NestedScrollView mNestedScrollView;
    private ImageView ivImgBg;
    private RecyclerView recycleViewHot;
    private RecyclerView recycleView;
    private ImageView ivImgGoodsBg;
    private LinearLayout llSearch;
    private LinearLayout llFixHeadTop;
    private View vTabCursor;
    private LinearLayout llCursor;
    private LinearLayout llFixHead;
    private LinearLayout llFixHeadTop1;
    private View vTabCursor1;
    private LinearLayout llCursor1;
    private RecyclerView recycleViewBottom;
    private RecyclerView recycleViewAdvantage;
    private RelativeLayout rlToTop;
    private TextView mTvHyp;

    private static HomeNestedFragment homeNestedFragment;

    private ImageView mLeftIv, mRightIv;
    private HomeNewDataInfo.ActivitycategorymodelBean.ActivitylistBean mLeftEntity, mRightEntity;

    public HomeNestedFragment(){}
    public static HomeNestedFragment getInstance(Bundle bundle) {
        if (homeNestedFragment == null)
            homeNestedFragment = new HomeNestedFragment();
        if (bundle != null)
            homeNestedFragment.setArguments(bundle);
        return homeNestedFragment;
    }
    private void imgClickActivity(HomeNewDataInfo.ActivitycategorymodelBean.ActivitylistBean info) {
            startActivity(new Intent(mContext, ActivityActivity.class)
                    .putExtra("shareUrl", info.getLink())//TODO 暂无分享连接
                    .putExtra("flag", 1)
                    .putExtra("id", info.getId())
                    .putExtra("title", info.getBannername())
                    .putExtra("desc", info.getDesc())
                    .putExtra("thumb", info.getThumb()));
    }
    private void requestMsg() {
        HttpUtils.requestCacheGet(context, AppConfig.HOME_TEST_DATA, null, new JsonCallback<String>(context) {
            @Override
            public void onSuccess(Response<String> response) {
                if (response.body() != null) {//TODO 第二版修改
//                    bigModel = JSON.parseObject(response.body(), BigModel.class);
                    mNewData = JSON.parseObject(response.body(), HomeNewDataInfo.class);
                    mLeftEntity = mNewData.getActivitycategorymodel().get(6).getActivitylist().get(0);
                    mRightEntity = mNewData.getActivitycategorymodel().get(6).getActivitylist().get(1);
//                    GlideImageUtil.normalImg(mContext, mLeftEntity.getThumb(), mLeftIv);
//                    GlideImageUtil.normalImg(mContext, mRightEntity.getThumb(), mRightIv);
                    try{

                        int width = ScreenUtil.getScreenWidth(getActivity());

                        Picasso.with(context).load(mLeftEntity.getThumb())
                                .error(R.mipmap.img_square).resize(width / 2,width / 3)
                                .placeholder(R.mipmap.img_square)
//                            .resize(ScreenUtil.getScreenWidth(getActivity()) / 2, ScreenUtil.getScreenHeight(getActivity()))
                                .into(mLeftIv);
                        Picasso.with(context).load(mRightEntity.getThumb())
                                .error(R.mipmap.img_square).resize(width / 2,width / 3)
                                .placeholder(R.mipmap.img_square)
                                .into(mRightIv);
                        initView();
                    }catch (Exception e){

                    }
                    requestClassify();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }
        });
    }
    private void initMyView() {
        mLeftIv = (ImageView) mRoot.findViewById(R.id.leftIv);
        mRightIv = (ImageView) mRoot.findViewById(R.id.rightIv);
        mLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgClickActivity(mLeftEntity);
            }
        });
        mRightIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgClickActivity(mRightEntity);
            }
        });
        mBanner = (Banner) mRoot.findViewById(R.id.mBanner);
        ViewGroup.LayoutParams layoutParams = mBanner.getLayoutParams();
        layoutParams.width = (int) DensityUtils.getWidthInPx(context);
        layoutParams.height = layoutParams.width * 35 / 64;
        mBanner.setLayoutParams(layoutParams);
        recycleViewIcon = (RecyclerView) mRoot.findViewById(R.id.recycleViewIcon);
        recycleViewAdvantage = (RecyclerView) mRoot.findViewById(R.id.recycleViewAdvantage);
        mNestedScrollView = (NestedScrollView) mRoot.findViewById(R.id.mNestedScrollView);

        ivImgBg = (ImageView) mRoot.findViewById(R.id.ivImgBg);
        ViewGroup.LayoutParams layoutParams1 = ivImgBg.getLayoutParams();
        layoutParams1.width = (int) DensityUtils.getWidthInPx(mContext);
        layoutParams1.height = (int) (layoutParams1.width / 6.4);
        ivImgBg.setLayoutParams(layoutParams1);

        mTvHyp = (TextView) mRoot.findViewById(R.id.tv_hyp);
        recycleViewHot = (RecyclerView) mRoot.findViewById(R.id.recycleViewHot);
        recycleView = (RecyclerView) mRoot.findViewById(R.id.recycleView);
        ivImgGoodsBg = (ImageView) mRoot.findViewById(R.id.ivImgGoodsBg);
        ViewGroup.LayoutParams layoutParams2 = ivImgGoodsBg.getLayoutParams();
        layoutParams2.width = (int) DensityUtils.getWidthInPx(mContext);
        layoutParams2.height = (int) (layoutParams2.width / 6.4);
        ivImgGoodsBg.setLayoutParams(layoutParams2);

        llSearch = (LinearLayout) mRoot.findViewById(R.id.llSearch);
        llFixHeadTop = (LinearLayout) mRoot.findViewById(R.id.llFixHeadTop);
        vTabCursor = mRoot.findViewById(R.id.v_tab_cursor);
        llCursor = (LinearLayout) mRoot.findViewById(R.id.llCursor);
        llFixHead = (LinearLayout) mRoot.findViewById(R.id.llFixHead);
        llFixHeadTop1 = (LinearLayout) mRoot.findViewById(R.id.llFixHeadTop1);
        vTabCursor1 = mRoot.findViewById(R.id.v_tab_cursor1);
        llCursor1 = (LinearLayout) mRoot.findViewById(R.id.llCursor1);
        recycleViewBottom = (RecyclerView) mRoot.findViewById(R.id.recycleViewBottom);
        rlToTop = (RelativeLayout) mRoot.findViewById(R.id.rlToTop);
        rlToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNestedScrollView.smoothScrollTo(0, 0);
            }
        });


        mTvHyp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNewData != null && mNewData.getHot() != null) {
                    getHotBrandChangeBatch();
                }
            }
        });

    }

    /**
     * 热门品牌换一批
     */
    private void getHotBrandChangeBatch() {
        ApiClient.requestNetHandle(mContext, AppConfig.HOT_BRAND_CHANGE_BATCH, "", null, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                HotBrandChangeBatchInfo info = JSON.parseObject(json, HotBrandChangeBatchInfo.class);
                hotChangeBatch(info);
                showToast("更换成功");
            }

            @Override
            public void onFailure(String msg) {
                showToast(msg);
            }
        });
    }


    private void hotChangeBatch(HotBrandChangeBatchInfo info) {
        mNewData.getHot().getGoods().clear();
        vBrandHotAdapter.notifyDataSetChanged();
        //  热门品牌
        if (!StringUtils.isEmpty(info.getImg())) {
            Picasso.with(context).load(info.getImg())
                    .fit()
                    .placeholder(R.mipmap.img_banner_normal)
                    .into(ivImgBg);
        } else {
            ivImgBg.setImageResource(R.mipmap.img_banner_normal);
        }
        if (!StringUtils.isEmpty(mNewData.getSelectPic())) {
            Picasso.with(context).load(mNewData.getSelectPic())
                    .fit()
                    .error(R.mipmap.img_square)
                    .placeholder(R.mipmap.img_square)
                    .into(ivImgGoodsBg);
        } else {
            ivImgGoodsBg.setImageResource(R.mipmap.img_square);
        }

        String goods = JSON.toJSONString(info.getGoods());
        List<HomeNewDataInfo.HotBean.GoodsBean> bean = new ArrayList<>();
        bean.addAll(JSON.parseArray(goods, HomeNewDataInfo.HotBean.GoodsBean.class));
        mNewData.getHot().getGoods().addAll(bean);
        vBrandHotAdapter.notifyDataSetChanged();
        vBrandHotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                mContext.startActivity(new Intent(mContext, GoodsListActivity.class)
                        .putExtra("id", mNewData.getHot().getGoods().get(i).getId()).putExtra("shop", true));
//                context.startActivity(new Intent(context, WebviewActivity.class)
//                        .putExtra("url", AppConfig.requestBrand + mNewData.getHot().getGoods().get(i).getId()));
            }
        });
    }

    private List<String> bannerUrls;
    //    private BigModel bigModel;
    private List<NormalModel> normalModels;
    private List<MainClassifyModel> classifyModels;
    private HomeNewDataInfo mNewData;

    // 固定
    private VFixIconNewAdapter vFixIconAdapter;
    // 热门品牌
    private VBrandHotNewAdapter vBrandHotAdapter;
    // 不同国家
//    private VBannerGoodsAdapter vBannerGoodsAdapter;
//
    private HomeNewAdapter mAdapter;
    private List<HomeNewInfo> mListData = new ArrayList<>();
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView != null) {
            return mRootView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    private GoodsBottomItemAdapter bottomItemAdapter;
    private int page = 1;

    @Override
    protected void initData() {
        super.initData();
        if (mRootView == null) {
            String json = SPUtils.getInstance(mContext).getString("json", "");
            if (!TextUtils.isEmpty(json)) {
                mListData.clear();
                List<HomeNewInfo> listData = new ArrayList<>();
                listData.addAll(JSON.parseArray(json, HomeNewInfo.class));
                mListData.addAll(listData);
            }
            initLogic();
        }
    }

    private void initLogic() {
        context = getContext();
        initMyView();
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, GoodsListActivity.class).putExtra("id", "0"));
//                context.startActivity(new Intent(context, WebviewActivity.class).putExtra("url", AppConfig.requestSearchGeneral));
            }
        });
        normalModels = new ArrayList<>();
        classifyModels = new ArrayList<>();
        bannerUrls = new ArrayList<>();
        initRc();

        requestMsg();
        initAdvantage();
    }

    /**
     * 初始化RC
     */
    private void initRc() {
        recycleView.setLayoutManager(new LinearLayoutManager(context));
        recycleView.setHasFixedSize(true);
        recycleView.setNestedScrollingEnabled(false);   // 外部对RecyclerView设置监听
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // 查看源码可知State有三种状态：SCROLL_STATE_IDLE（静止）、SCROLL_STATE_DRAGGING（上升）、SCROLL_STATE_SETTLING（下落）
                if (newState == RecyclerView.SCROLL_STATE_IDLE) { // 滚动静止时才加载图片资源，极大提升流畅度
//                    mAdapter.setScrolling(false);
                    mAdapter.notifyDataSetChanged(); // notify调用后onBindViewHolder会响应调用
                    Glide.with(mContext).resumeRequests();
                } else {
//                    mAdapter.setScrolling(true);
                    Glide.with(mContext).pauseRequests();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        mAdapter = new HomeNewAdapter(mContext, mListData);
        recycleView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void initGoods() {
        bottomItemAdapter = new GoodsBottomItemAdapter(normalModels);
        final GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        recycleViewBottom.setLayoutManager(layoutManager);
        recycleViewBottom.setHasFixedSize(true);
        recycleViewBottom.setNestedScrollingEnabled(false);
        // 外部对RecyclerView设置监听
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (llFixHead1 == null || llFixHead == null) {
//                    llFixHead1 = (LinearLayout) getView().findViewById(R.id.llFixHead1);
//                    llFixHead = (LinearLayout) getView().findViewById(R.id.llFixHead);
//                }

//                if (isVisibleLocal(recycleViewBottom)) {//判断recycler是否可见
//                    L.d("-------------------------");
//                    // 顶部菜单可见 且 滑动的距离+状态栏的距离  小于  底部菜单 get top 的距离
//                    if (llFixHead.getVisibility() == View.VISIBLE
//                            && scrollY + DensityUtils.dip2px(context, 10) < llFixHeadTop1.getTop()) {
//                        L.d("11111111111111111111111");
//                        llFixHead.setVisibility(View.INVISIBLE);
//                        rlToTop.setVisibility(View.GONE);
//                    } else if ((llFixHead.getVisibility() == View.INVISIBLE || llFixHead.getVisibility() == View.GONE)
//                            && !isVisibleLocal(vTabCursor1)) {// if (!llFixHead.isShown())
//                        L.d("2222222222222222222222222");
//                        rlToTop.setVisibility(View.VISIBLE);
//                        llFixHead.setVisibility(View.VISIBLE);
//                    }
//                } else if (llFixHead.getVisibility() == View.VISIBLE
//                        && scrollY + DensityUtils.dip2px(context, 10) < llFixHeadTop1.getTop()) {
//                    L.d("+++++++++++++++++++");
//                    llFixHead.setVisibility(View.INVISIBLE);
//                }
                int[] location = new int[2];
                llFixHeadTop1.getLocationOnScreen(location);
                // recycleview 头部列表高度  不在屏幕中
                if (DensityUtils.dip2px(mContext, 60) > location[1]
                        && (llFixHead.getVisibility() == View.INVISIBLE || llFixHead.getVisibility() == View.GONE)) {//
                    llFixHead.setVisibility(View.VISIBLE);
                    rlToTop.setVisibility(View.VISIBLE);
                } else if (llFixHead.getVisibility() == View.VISIBLE
                        && DensityUtils.dip2px(mContext, 60) < location[1]) {
                    rlToTop.setVisibility(View.GONE);
                    llFixHead.setVisibility(View.GONE);
                }
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    page++;
                    requestGoods(selIndex);
                }
            }
        });
        recycleViewBottom.setAdapter(bottomItemAdapter);
    }

    //当 View 有一点点不可见时立即返回false!
    public boolean isVisibleLocal(View target) {
        Rect rect = new Rect();
        target.getLocalVisibleRect(rect);
        return rect.top == 0;//DensityUtils.dip2px(mContext, 40)
    }

    private void initView() {
        initBanner();

        initFix();

        initHot();

        setListData();


//        requestAdvantages();
//        recycleView.setLayoutManager(new LinearLayoutManager(context));
//        recycleView.setHasFixedSize(true);
//        recycleView.setNestedScrollingEnabled(false);   // 外部对RecyclerView设置监听
//        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                // 查看源码可知State有三种状态：SCROLL_STATE_IDLE（静止）、SCROLL_STATE_DRAGGING（上升）、SCROLL_STATE_SETTLING（下落）
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) { // 滚动静止时才加载图片资源，极大提升流畅度
////                    mAdapter.setScrolling(false);
//                    mAdapter.notifyDataSetChanged(); // notify调用后onBindViewHolder会响应调用
//                    Glide.with(mContext).resumeRequests();
//                } else {
////                    mAdapter.setScrolling(true);
//                    Glide.with(mContext).pauseRequests();
//                }
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//        });
//
//        mAdapter = new HomeNewAdapter(mContext, mListData);
//        recycleView.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged();
    }

    /**
     * 热门
     */
    private void initHot() {
        //  热门品牌
        if (!StringUtils.isEmpty(mNewData.getHot().getImg())) {
            Picasso.with(context).load(mNewData.getHot().getImg())
                    .fit()
                    .placeholder(R.mipmap.img_banner_normal)
                    .into(ivImgBg);
        } else {
            ivImgBg.setImageResource(R.mipmap.img_banner_normal);
        }
        if (!StringUtils.isEmpty(mNewData.getSelectPic())) {
            Picasso.with(context).load(mNewData.getSelectPic())
                    .fit()
                    .error(R.mipmap.img_square)
                    .placeholder(R.mipmap.img_square)
                    .into(ivImgGoodsBg);
        } else {
            ivImgGoodsBg.setImageResource(R.mipmap.img_square);
        }
        vBrandHotAdapter = new VBrandHotNewAdapter(mNewData.getHot().getGoods());
        vBrandHotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                mContext.startActivity(new Intent(mContext, GoodsListActivity.class)
                        .putExtra("id", mNewData.getHot().getGoods().get(i).getId()).putExtra("shop", true));
//                context.startActivity(new Intent(context, WebviewActivity.class)
//                        .putExtra("url", AppConfig.requestBrand + mNewData.getHot().getGoods().get(i).getId()));
            }
        });
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(context, 3);
        recycleViewHot.setLayoutManager(gridLayoutManager1);
        recycleViewHot.setHasFixedSize(true);
        recycleViewHot.setNestedScrollingEnabled(false);
        recycleViewHot.setAdapter(vBrandHotAdapter);
    }

//    private void requestAdvantages() {
//        HttpUtils.requestGet(mContext, AppConfig.requestAdvantage, null, new JsonCallback<List<AdvantageModel>>() {
//            @Override
//            public void onSuccess(Response<List<AdvantageModel>> response) {
//                if (response.body() != null && response.body().size() > 0) {
//                    advantageModels.addAll(response.body());
//                }
//                advantageAdapter.notifyDataSetChanged();
//            }
//        });
//    }

    /**
     * 热门活动
     */
    private void requestAdvantages() {
        HttpUtils.requestGet(mContext, AppConfig.requestAdvantage, null, new JsonCallback<List<HomeNewInfo.OneModeBeam>>() {
            @Override
            public void onSuccess(Response<List<HomeNewInfo.OneModeBeam>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    List<HomeNewInfo.OneModeBeam> listBean = new ArrayList<>();
                    listBean.addAll(response.body());
                    List<HomeNewInfo> list = new ArrayList<>();
                    HomeNewInfo info = new HomeNewInfo();
                    info.setOneModeBeam(listBean);
                    list.add(info);
                    mListData.addAll(list);
                }
            }
        });


    }

    /**
     * 设置数据
     */
    private void setListData() {
        mListData.clear();
        // 全球销量版单
        HomeNewInfo info1 = new HomeNewInfo();
        List<HomeNewInfo> list1 = new ArrayList<>();
        String jsonActivityOne = JSON.toJSONString(mNewData.getActivityglobalmodel());
        HomeNewInfo.ActivityglobalmodelBean bean = JSON.parseObject(jsonActivityOne, HomeNewInfo.ActivityglobalmodelBean.class);
        info1.setActivityglobalmodel(bean);
        list1.add(info1);
        mListData.addAll(list1);

        //8个分类
        String jsonActivity = JSON.toJSONString(mNewData.getActivitycategorymodel());
        List<HomeNewInfo.ActivitycategorymodelBean> beans = new ArrayList<>();
        beans.addAll(JSON.parseArray(jsonActivity, HomeNewInfo.ActivitycategorymodelBean.class));
        List<HomeNewInfo> list = new ArrayList<>();
        HomeNewInfo info = new HomeNewInfo();
        info.setActivitycategorymodel(beans);
        list.add(info);
        mListData.addAll(list);

        //缓存
        SPUtils.getInstance(mContext).remove("json");
        String json = JSON.toJSONString(mListData);
        SPUtils.getInstance(mContext).putString("json", json);

        mAdapter.notifyDataSetChanged();
    }

    private AdvantageAdapter advantageAdapter;
    private List<AdvantageModel> advantageModels;

    /**
     * 广告
     */
    private void initAdvantage() {
        advantageModels = new ArrayList<>();
        advantageAdapter = new AdvantageAdapter(advantageModels);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        recycleViewAdvantage.setLayoutManager(layoutManager);
        recycleViewAdvantage.setHasFixedSize(true);
        recycleViewAdvantage.setNestedScrollingEnabled(false);
        recycleViewAdvantage.setAdapter(advantageAdapter);
    }

    /**
     * fix 四个固定标题
     */
    private void initFix() {
        //TODO 去掉达人社 第二版修改
        List<HomeNewDataInfo.IconBean> itemInfo = new ArrayList<>();
        for (int i = 0; i < mNewData.getIcon().size(); i++) {
            if (i <= 2) {
                HomeNewDataInfo.IconBean bean = mNewData.getIcon().get(i);
                itemInfo.add(bean);
            }
        }
        vFixIconAdapter = new VFixIconNewAdapter(itemInfo);
        vFixIconAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                switch (i) {
                    case 0: //全球购
                        context.startActivity(new Intent(context, GlobalBuyActivity.class));
                        break;
                    case 2: //电商汇
                        context.startActivity(new Intent(context, DianShangHuiActivity.class));
                        break;
                    default:
                        context.startActivity(new Intent(context, WebviewActivity.class)
                                .putExtra("url", mNewData.getIcon().get(i).getLink()));
                        break;
                }
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        recycleViewIcon.setLayoutManager(gridLayoutManager);
        recycleViewIcon.setHasFixedSize(true);
        recycleViewIcon.setNestedScrollingEnabled(false);
        recycleViewIcon.setAdapter(vFixIconAdapter);
    }

    /**
     * banner
     * 第二版修改
     */
    private void initBanner() {//TODO 第二版修改
        for (int i = 0; i < mNewData.getBanner().size(); i++) {
            bannerUrls.add(mNewData.getBanner().get(i).getThumb());
        }
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerAnimation(Transformer.Default);
        mBanner.isAutoPlay(true);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setDelayTime(3000);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setImages(bannerUrls);
        mBanner.setOnBannerListener(new OnBannerListener() {//TODO banner图点击事件
            @Override
            public void OnBannerClick(int position) {
                String[] ids = mNewData.getBanner().get(position).getLink().split(":");
                String id = "";
                String tag = "";
                if (ids.length > 1) {
                    id = ids[1];
                    tag = ids[0];
                }
                if (tag.contains("activity")){
                    startActivity(new Intent(mContext, ActivityActivity.class)
                            .putExtra("shareUrl", mNewData.getBanner().get(position).getLink())//TODO 暂无分享连接
                            .putExtra("flag", 1).putExtra("id", id)
                            .putExtra("title", mNewData.getBanner().get(position).getBannername())
                            .putExtra("desc", "")
                            .putExtra("thumb", mNewData.getBanner().get(position).getThumb()));
                }else if (tag.contains("goods")){
                    startActivity(new Intent(mContext, GoodsDetailActivity.class).putExtra("id", id));
                }else if (tag.contains("country")){
                    startActivity(new Intent(mContext, ActivityActivity.class)
                            .putExtra("shareUrl", mNewData.getBanner().get(position).getLink())//TODO 暂无分享连接
                            .putExtra("flag", 2).putExtra("id", id)
                            .putExtra("title", mNewData.getBanner().get(position).getBannername())
                            .putExtra("desc", "")
                            .putExtra("thumb", mNewData.getBanner().get(position).getThumb()));
                }
            }
        });
        mBanner.start();
    }

//    private void requestMsg() {
//        HttpUtils.requestGet(context, AppConfig.requestHomeMsg, null, new JsonCallback<String>(context) {
//            @Override
//            public void onSuccess(Response<String> response) {
//                if (response.body() != null) {
//                    bigModel = JSON.parseObject(response.body(), BigModel.class);
//                    initView();
//                    requestClassify();
//                }
//            }
//
//            @Override
//            public void onError(Response<String> response) {
//                super.onError(response);
//            }
//        });
//    }

    private int selIndex;
    private List<TextView> tabTextList, tabTextListItem;
    private List<View> cursorViewList, cursorViewListItem;

    private void initClassify() {
        tabTextList = new ArrayList<>();
        cursorViewList = new ArrayList<>();
        tabTextListItem = new ArrayList<>();
        cursorViewListItem = new ArrayList<>();
//        llCursor.setWeightSum(classifyModels.size());
//        llCursor1.setWeightSum(classifyModels.size());
        int width = (int) (DensityUtils.getWidthInPx(context) / classifyModels.size());
        int heigth = DensityUtils.dip2px(context, 49);
        int heigth1 = DensityUtils.dip2px(context, 49);
        for (int i = 0; i < classifyModels.size(); i++) {
            final TextView textView = new TextView(context);
            textView.setWidth(width);
            textView.setHeight(heigth);
            textView.setGravity(Gravity.CENTER);
            try {
                textView.setTextColor(getResources().getColor(R.color.colorGrayText88));
            } catch (Exception e) {
            }
            textView.setText(classifyModels.get(i).getBannername() == null ? "" : classifyModels.get(i).getBannername());
            tabTextList.add(textView);
            llFixHeadTop.addView(textView);
            View view = new View(context);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, heigth1);
            view.setLayoutParams(layoutParams);
            llCursor.addView(view);
            cursorViewList.add(view);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == selIndex) {
                        return;
                    } else {
                        page = 1;
                        textView.requestFocus();
                        selIndex = finalI;
                        initTextViewUI();
                        requestGoods(finalI);
                    }
                }
            });
        }
        initBottomView();
        try {
            tabTextList.get(0).setTextColor(getResources().getColor(R.color.colorRed));
            tabTextListItem.get(0).setTextColor(getResources().getColor(R.color.colorRed));
            cursorViewList.get(0).setBackgroundColor(getResources().getColor(R.color.colorRed));
            cursorViewListItem.get(0).setBackgroundColor(getResources().getColor(R.color.colorRed));
        } catch (Exception e) {
            L.d("Exception1:" + e.toString());
        }
    }

    /**
     * 固定
     */
    private void initBottomView() {
        int width = (int) (DensityUtils.getWidthInPx(context) / classifyModels.size());
        int heigth = DensityUtils.dip2px(context, 49);
        int heigth1 = DensityUtils.dip2px(context, 1);
        for (int i = 0; i < classifyModels.size(); i++) {
            final TextView textView = new TextView(context);
            textView.setWidth(width);
            textView.setHeight(heigth);
            textView.setGravity(Gravity.CENTER);
            try {
                textView.setTextColor(getResources().getColor(R.color.colorGrayText88));
            } catch (Exception e) {

            }
            textView.setText(classifyModels.get(i).getBannername());
            tabTextListItem.add(textView);
            llFixHeadTop1.addView(textView);
            View view = new View(context);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, heigth1);
            view.setLayoutParams(layoutParams);
            llCursor1.addView(view);
            cursorViewListItem.add(view);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == selIndex) {
                        return;
                    } else {
                        page = 1;
                        selIndex = finalI;
                        textView.requestFocus();
                        initTextViewUI();
                        requestGoods(finalI);
                    }
                }
            });
        }
    }

    /**
     * 分类
     */
    private void requestClassify() {
        HttpUtils.requestCacheGet(context, AppConfig.requestMainClassify, null, new JsonCallback<List<MainClassifyModel>>(context) {
            @Override
            public void onSuccess(Response<List<MainClassifyModel>> response) {
                if (response.body() != null) {
                    classifyModels.addAll(response.body());
                    initClassify();
                    requestGoods(0);
                }
                initGoods();
            }

            @Override
            public void onError(Response<List<MainClassifyModel>> response) {
                super.onError(response);
            }
        });
    }

    private void requestGoods(final int index) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        HttpUtils.requestGet(context, classifyModels.get(index).getEachUrl(), map, new JsonCallback<List<NormalModel>>(context) {
            @Override
            public void onSuccess(Response<List<NormalModel>> response) {
                normalModels.clear();
                if (index == selIndex) {
                    if (response.body() != null && response.body().size() > 0) {
                        normalModels.addAll(response.body());
                    }
                    initTextViewUI();
                }
            }

            @Override
            public void onError(Response<List<NormalModel>> response) {
                super.onError(response);
                if (index == selIndex) {
                    if (page > 1) {
                        page--;
                    }
                    initTextViewUI();
                }
            }
        });
    }

    private void initTextViewUI() {
        bottomItemAdapter.notifyDataSetChanged();
        if (page == 1) {
            scrollCursor();
            if (llFixHeadTop.isShown()) {
//                mHandle.sendEmptyMessageDelayed(310, 500);
                int top = recycleViewBottom.getTop() - DensityUtils.dip2px(context, 40);
                mNestedScrollView.smoothScrollTo(0, top - 5);
            }
        }
    }

    Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int top = recycleViewBottom.getTop() - DensityUtils.dip2px(context, 40);
            mNestedScrollView.smoothScrollTo(0, top - 5);
        }
    };

    private int fromX;

    /**
     * 滑动游标
     */
    private void scrollCursor() {
//        int width = (int) (DensityUtils.getWidthInPx(context) / classifyModels.size());
//        TranslateAnimation anim = new TranslateAnimation(fromX, selIndex * width, 0, 0);
//        anim.setFillAfter(true);//设置动画结束时停在动画结束的位置
//        anim.setDuration(100);
//        //保存动画结束时游标的位置,作为下次滑动的起点
//        fromX = selIndex * width;
//        vTabCursor.startAnimation(anim);
//        vTabCursor1.startAnimation(anim);
        //设置Tab切换颜色
        for (int i = 0; i < tabTextList.size(); i++) {
            try {
                tabTextList.get(i).setTextColor(i == selIndex ? getResources().getColor(R.color.colorRed) : getResources().getColor(R.color.colorGrayText88));
                tabTextListItem.get(i).setTextColor(i == selIndex ? getResources().getColor(R.color.colorRed) : getResources().getColor(R.color.colorGrayText88));
                cursorViewList.get(i).setBackgroundColor(i == selIndex ? getResources().getColor(R.color.colorRed) : getResources().getColor(R.color.transparent));
                cursorViewListItem.get(i).setBackgroundColor(i == selIndex ? getResources().getColor(R.color.colorRed) : getResources().getColor(R.color.transparent));
            } catch (Exception e) {
                L.d("Exception:" + e.toString());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        initMyView();
//        if (selIndex != 0) {
//            scrollCursor();
//            int width = (int) (DensityUtils.getWidthInPx(context) / classifyModels.size());
//            TranslateAnimation anim = new TranslateAnimation(fromX, selIndex * width, 0, 0);
//            anim.setFillAfter(true);//设置动画结束时停在动画结束的位置
//            anim.setDuration(100);
//            //保存动画结束时游标的位置,作为下次滑动的起点
//            fromX = selIndex * width;
//            vTabCursor.startAnimation(anim);
//            vTabCursor1.startAnimation(anim);
//        }
    }

    private static View mRootView;

    @Override
    public void onDestroyView() {
        mRootView = mRoot;
        super.onDestroyView();
        L.d("onDestroyView-------------------");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d("onDestroy+++++++++++++++++++++");
    }

    public void releaseThis() {
        if (mRootView != null) {
            mRootView = null;
        }
        if (homeNestedFragment != null) {
            homeNestedFragment = null;
        }
    }
}
