package com.haoniu.zzx.app_ts;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.haoniu.zzx.app_ts.adapter.GoodsBottomItemAdapter;
import com.haoniu.zzx.app_ts.adapter.VBannerGoodsAdapter;
import com.haoniu.zzx.app_ts.adapter.VBrandHotAdapter;
import com.haoniu.zzx.app_ts.adapter.VFixIconAdapter;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.HttpUtils;
import com.haoniu.zzx.app_ts.http.JsonCallback;
import com.haoniu.zzx.app_ts.model.BigModel;
import com.haoniu.zzx.app_ts.model.MainClassifyModel;
import com.haoniu.zzx.app_ts.model.NormalModel;
import com.haoniu.zzx.app_ts.utils.L;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import self.androidbase.utils.DensityUtils;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.mBanner)
    Banner mBanner;
    @BindView(R.id.recycleViewIcon)
    RecyclerView recycleViewIcon;
    @BindView(R.id.mNestedScrollView)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.ivImgBg)
    ImageView ivImgBg;
    @BindView(R.id.recycleViewHot)
    RecyclerView recycleViewHot;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.ivImgGoodsBg)
    ImageView ivImgGoodsBg;
    @BindView(R.id.llFixHeadTop)
    LinearLayout llFixHeadTop;
    @BindView(R.id.v_tab_cursor)
    View vTabCursor;
    @BindView(R.id.llCursor)
    LinearLayout llCursor;
    @BindView(R.id.llFixHead)
    LinearLayout llFixHead;
    @BindView(R.id.llFixHeadTop1)
    LinearLayout llFixHeadTop1;
    @BindView(R.id.v_tab_cursor1)
    View vTabCursor1;
    @BindView(R.id.llCursor1)
    LinearLayout llCursor1;
    @BindView(R.id.llFixHead1)
    LinearLayout llFixHead1;
    @BindView(R.id.recycleViewBottom)
    RecyclerView recycleViewBottom;

    private Context mContext;
    private BigModel bigModel;
    private List<NormalModel> normalModels;
    private List<MainClassifyModel> classifyModels;

    // 固定
    private VFixIconAdapter vFixIconAdapter;
    // 热门品牌
    private VBrandHotAdapter vBrandHotAdapter;
    // 不同国家
    private VBannerGoodsAdapter vBannerGoodsAdapter;


    private GoodsBottomItemAdapter bottomItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        mContext = this;
        initData();
    }

    private int page;

    private void initData() {
        normalModels = new ArrayList<>();
        classifyModels = new ArrayList<>();
        bottomItemAdapter = new GoodsBottomItemAdapter(normalModels);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        recycleViewBottom.setLayoutManager(layoutManager);
        recycleViewBottom.setHasFixedSize(true);
        recycleViewBottom.setNestedScrollingEnabled(false);
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY + DensityUtils.dip2px(mContext, 50) < llFixHead1.getTop()) {
                    llFixHead.setVisibility(View.INVISIBLE);
                    llFixHead1.setVisibility(View.VISIBLE);
                } else {
                    llFixHead.setVisibility(View.VISIBLE);
                    llFixHead1.setVisibility(View.INVISIBLE);
                }
                L.d("scrollY   :" + scrollY + "  oldScrollY  :" + oldScrollY + " TOP   :" + llFixHead1.getTop());
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    page++;
                    requestGoods(selIndex);
                }
            }
        });
        recycleViewBottom.setAdapter(bottomItemAdapter);
        requestMsg();
    }

    private void requestMsg() {
        HttpUtils.requestGet(mContext, AppConfig.requestHomeMsg, null, new JsonCallback<String>(mContext, "加载中...") {
            @Override
            public void onSuccess(Response<String> response) {
                if (response.body() != null) {
                    bigModel = JSON.parseObject(response.body(), BigModel.class);
                    initTop();
                    requestClassify();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
//                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void initTop() {
        //  四个固定标题
        vFixIconAdapter = new VFixIconAdapter(bigModel.getIcon());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        recycleViewIcon.setLayoutManager(gridLayoutManager);
        recycleViewIcon.setHasFixedSize(true);
        recycleViewIcon.setNestedScrollingEnabled(false);
        recycleViewIcon.setAdapter(vFixIconAdapter);

        vBrandHotAdapter = new VBrandHotAdapter(bigModel.getHot().getGoods());
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(mContext, 4);
        recycleViewHot.setLayoutManager(gridLayoutManager1);
        recycleViewHot.setHasFixedSize(true);
        recycleViewHot.setNestedScrollingEnabled(false);
        recycleViewHot.setAdapter(vBrandHotAdapter);

        recycleView.setLayoutManager(new LinearLayoutManager(mContext));
        recycleView.setHasFixedSize(true);
        recycleView.setNestedScrollingEnabled(false);
        vBannerGoodsAdapter = new VBannerGoodsAdapter(bigModel.getHome());
        recycleView.setAdapter(vBannerGoodsAdapter);
    }

    private void requestClassify() {
        HttpUtils.requestGet(mContext, AppConfig.requestMainClassify, null, new JsonCallback<List<MainClassifyModel>>(mContext, "加载中...") {
            @Override
            public void onSuccess(Response<List<MainClassifyModel>> response) {
                if (response.body() != null) {
                    classifyModels.addAll(response.body());
                    initClassify();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    private int selIndex;
    private List<TextView> tabTextList, tabTextListItem;

    private void initClassify() {
        tabTextList = new ArrayList<>();
        tabTextListItem = new ArrayList<>();
        llCursor.setWeightSum(classifyModels.size());
        int width = (int) (DensityUtils.getWidthInPx(mContext) / classifyModels.size());
        int heigth = DensityUtils.dip2px(mContext, 49);
        for (int i = 0; i < classifyModels.size(); i++) {
            TextView textView = new TextView(mContext);
            textView.setWidth(width);
            textView.setHeight(heigth);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(R.color.colorGrayText88));
            textView.setText(classifyModels.get(i).getBannername());
            tabTextList.add(textView);
            llFixHeadTop.addView(textView);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == selIndex) {
                        return;
                    } else {
                        page = 1;
                        normalModels.clear();
                        requestGoods(finalI);
                    }
                }
            });
        }
        initBottomView();
        tabTextList.get(0).setTextColor(getResources().getColor(R.color.colorRed));
        requestGoods(0);
//        mRefreshLayout.beginRefreshing();
    }

    private void initBottomView() {
        int width = (int) (DensityUtils.getWidthInPx(mContext) / classifyModels.size());
        int heigth = DensityUtils.dip2px(mContext, 49);
        for (int i = 0; i < classifyModels.size(); i++) {
            TextView textView = new TextView(mContext);
            textView.setWidth(width);
            textView.setHeight(heigth);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(R.color.colorGrayText88));
            textView.setText(classifyModels.get(i).getBannername());
            tabTextListItem.add(textView);
            llFixHeadTop1.addView(textView);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == selIndex) {
                        return;
                    } else {
                        page = 1;
                        normalModels.clear();
                        requestGoods(finalI);
                    }
                }
            });
        }
    }

    private void requestGoods(final int index) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        HttpUtils.requestGet(mContext, classifyModels.get(index).getEachUrl(), map, new JsonCallback<List<NormalModel>>(mContext, "加载中...") {
            @Override
            public void onSuccess(Response<List<NormalModel>> response) {
                selIndex = index;
                if (response.body() != null && response.body().size() > 0) {
                    normalModels.addAll(response.body());
                    bottomItemAdapter.notifyDataSetChanged();
                }
//                recycleViewBottom.scrollToPosition(0);
                if (page == 1 && llFixHead.getVisibility() == View.VISIBLE){
                    int top = recycleViewBottom.getTop() - DensityUtils.dip2px(mContext, 80);
                    mNestedScrollView.smoothScrollTo(0, top);
                }
            }
        });
    }
}
