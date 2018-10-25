package com.haoniu.zzx.app_ts.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.activity.DianShangHuiActivity;
import com.haoniu.zzx.app_ts.activity.GlobalBuyActivity;
import com.haoniu.zzx.app_ts.activity.GoodsListActivity;
import com.haoniu.zzx.app_ts.activity.WebviewActivity;
import com.haoniu.zzx.app_ts.adapter.AdvantageAdapter;
import com.haoniu.zzx.app_ts.vlayout.BannerAdapter;
import com.haoniu.zzx.app_ts.adapter.GoodsBottomItemAdapter;
import com.haoniu.zzx.app_ts.adapter.VBannerGoodsAdapter;
import com.haoniu.zzx.app_ts.adapter.VBrandHotAdapter;
import com.haoniu.zzx.app_ts.adapter.VFixIconAdapter;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.HttpUtils;
import com.haoniu.zzx.app_ts.http.JsonCallback;
import com.haoniu.zzx.app_ts.model.AdvantageModel;
import com.haoniu.zzx.app_ts.model.BigModel;
import com.haoniu.zzx.app_ts.model.MainClassifyModel;
import com.haoniu.zzx.app_ts.model.NormalModel;
import com.haoniu.zzx.app_ts.utils.L;
import com.haoniu.zzx.app_ts.vlayout.VActivityAdapter;
import com.haoniu.zzx.app_ts.vlayout.VFixAdapter;
import com.haoniu.zzx.app_ts.vlayout.VGoodsAdapter;
import com.haoniu.zzx.app_ts.vlayout.VHotAdapter;
import com.haoniu.zzx.app_ts.vlayout.VLayoutBannerGoodsAdapter;
import com.haoniu.zzx.app_ts.vlayout.VTopClassifyAdapter;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2017/9/27 0027.
 */

public class HomeVLayoutFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private LinearLayout llSearch;
    private LinearLayout llFixHeadTop;
    private View vTabCursor;
    private LinearLayout llCursor;
    private LinearLayout llFixHead;

    private void initMyView() {
        mRecyclerView = (RecyclerView) mRoot.findViewById(R.id.mRecyclerView);
        llSearch = (LinearLayout) mRoot.findViewById(R.id.llSearch);
        llFixHeadTop = (LinearLayout) mRoot.findViewById(R.id.llFixHeadTop);
        vTabCursor = mRoot.findViewById(R.id.v_tab_cursor);
        llCursor = (LinearLayout) mRoot.findViewById(R.id.llCursor);
        llFixHead = (LinearLayout) mRoot.findViewById(R.id.llFixHead);
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, GoodsListActivity.class).putExtra("id", "0"));
            }
        });
    }

    private int page = 1;

    private List<String> bannerUrls;
    private BigModel bigModel;
    private List<NormalModel> normalModels;
    private List<MainClassifyModel> classifyModels;

    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_homefragment_vlayout;
    }

    private VirtualLayoutManager layoutManager;
    private List<DelegateAdapter.Adapter> mAdapters;
    private DelegateAdapter delegateAdapter;

    @Override
    protected void initData() {
        super.initData();
        context = getContext();
        initMyView();
        showPro("加载中...");
        normalModels = new ArrayList<>();
        classifyModels = new ArrayList<>();
        bannerUrls = new ArrayList<>();
        requestMsg();
    }

    private VGoodsAdapter vGoodsAdapter;

    private void initGoods() {
//        bottomItemAdapter = new GoodsBottomItemAdapter(normalModels, classifyModels);
        mAdapters.add(new VTopClassifyAdapter(mContext, classifyModels));
        vGoodsAdapter = new VGoodsAdapter(mContext, normalModels);
        mAdapters.add(vGoodsAdapter);
        delegateAdapter.setAdapters(mAdapters);
    }

    /**
     * banner  fix 四个固定标题
     */
    private void initTop() {
        layoutManager = new VirtualLayoutManager(context);
        mAdapters = new LinkedList<>();
        mRecyclerView.setLayoutManager(layoutManager);
        //  ViewHolder的对象池
        final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        mRecyclerView.setAdapter(delegateAdapter);

        for (int i = 0; i < bigModel.getBanner().size(); i++) {
            bannerUrls.add(bigModel.getBanner().get(i).getThumb());
        }
        mAdapters.add(new BannerAdapter(mContext, bannerUrls));
        mAdapters.add(new VFixAdapter(mContext, bigModel.getIcon()));
    }

    private void requestAdvantages() {
        HttpUtils.requestGet(mContext, AppConfig.requestAdvantage, null, new JsonCallback<List<AdvantageModel>>() {
            @Override
            public void onSuccess(Response<List<AdvantageModel>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    initAdvantage(response.body());
                }
            }
        });
    }

    /**
     * 活动  /   热门
     */
    private void initAdvantage(List<AdvantageModel> advantageModels) {
        mAdapters.add(new VActivityAdapter(mContext, advantageModels));
        mAdapters.add(new VHotAdapter(mContext, bigModel.getHot().getGoods()));
        mAdapters.add(new VLayoutBannerGoodsAdapter(mContext, bigModel.getHome()));
        requestClassify();
    }

    /**
     * 首页数据
     */
    private void requestMsg() {
        HttpUtils.requestGet(context, AppConfig.requestHomeMsg, null, new JsonCallback<String>(context) {
            @Override
            public void onSuccess(Response<String> response) {
                if (response.body() != null) {
                    bigModel = JSON.parseObject(response.body(), BigModel.class);
                    initTop();
                    requestAdvantages();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                hidePro();
            }
        });
    }

    private int selIndex;
    private List<TextView> tabTextList, tabTextListItem;

    private void initClassify() {
        tabTextList = new ArrayList<>();
        tabTextListItem = new ArrayList<>();
        llCursor.setWeightSum(classifyModels.size());
        int width = (int) (DensityUtils.getWidthInPx(context) / classifyModels.size());
        int heigth = DensityUtils.dip2px(context, 49);
        for (int i = 0; i < classifyModels.size(); i++) {
            TextView textView = new TextView(context);
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
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == selIndex) {
                        return;
                    } else {
                        normalModels.clear();
                        page = 1;
                        selIndex = finalI;
                        requestGoods(finalI);
                    }
                }
            });
        }
        initBottomView();
        try {
            tabTextList.get(0).setTextColor(getResources().getColor(R.color.colorRed));
            tabTextListItem.get(0).setTextColor(getResources().getColor(R.color.colorRed));
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
        for (int i = 0; i < classifyModels.size(); i++) {
            TextView textView = new TextView(context);
            textView.setWidth(width);
            textView.setHeight(heigth);
            textView.setGravity(Gravity.CENTER);
            try {
                textView.setTextColor(getResources().getColor(R.color.colorGrayText88));
            } catch (Exception e) {

            }
            textView.setText(classifyModels.get(i).getBannername());
            tabTextListItem.add(textView);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == selIndex) {
                        return;
                    } else {
                        normalModels.clear();
                        page = 1;
                        selIndex = finalI;
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
        HttpUtils.requestGet(context, AppConfig.requestMainClassify, null, new JsonCallback<List<MainClassifyModel>>(context) {
            @Override
            public void onSuccess(Response<List<MainClassifyModel>> response) {
                if (response.body() != null) {
                    classifyModels.addAll(response.body());
//                    initClassify();
                }
                hidePro();
                initGoods();
                requestGoods(0);
            }

            @Override
            public void onError(Response<List<MainClassifyModel>> response) {
                super.onError(response);
                hidePro();
            }
        });
    }

    private void requestGoods(final int index) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        HttpUtils.requestGet(context, classifyModels.get(index).getEachUrl(), map, new JsonCallback<List<NormalModel>>(context) {
            @Override
            public void onSuccess(Response<List<NormalModel>> response) {
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
//                    initTextViewUI();
                }
            }
        });
    }

    private void initTextViewUI() {
        vGoodsAdapter.notifyDataSetChanged();
        if (page == 1) {
//            scrollCursor();
        }
        if (llFixHead.getVisibility() == View.VISIBLE) {
            mHandle.sendEmptyMessageDelayed(310, 500);
        }
    }

    Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            int top = recycleViewBottom.getTop() - DensityUtils.dip2px(context, 40);
//            mNestedScrollView.smoothScrollTo(0, top - 5);
        }
    };

    private int fromX;

    /**
     * 滑动游标
     */
    private void scrollCursor() {
        int width = (int) (DensityUtils.getWidthInPx(context) / classifyModels.size());
        TranslateAnimation anim = new TranslateAnimation(fromX, selIndex * width, 0, 0);
        anim.setFillAfter(true);//设置动画结束时停在动画结束的位置
        anim.setDuration(100);
        //保存动画结束时游标的位置,作为下次滑动的起点
        fromX = selIndex * width;
        vTabCursor.startAnimation(anim);
        //设置Tab切换颜色
        for (int i = 0; i < tabTextList.size(); i++) {
            try {
                tabTextList.get(i).setTextColor(i == selIndex ? getResources().getColor(R.color.colorRed) : getResources().getColor(R.color.colorGrayText88));
                tabTextListItem.get(i).setTextColor(i == selIndex ? getResources().getColor(R.color.colorRed) : getResources().getColor(R.color.colorGrayText88));
            } catch (Exception e) {
                L.d("Exception:" + e.toString());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initMyView();
        if (selIndex != 0) {
            int width = (int) (DensityUtils.getWidthInPx(context) / classifyModels.size());
            TranslateAnimation anim = new TranslateAnimation(fromX, selIndex * width, 0, 0);
            anim.setFillAfter(true);//设置动画结束时停在动画结束的位置
            anim.setDuration(100);
            //保存动画结束时游标的位置,作为下次滑动的起点
            fromX = selIndex * width;
            vTabCursor.startAnimation(anim);
        }
    }

}
