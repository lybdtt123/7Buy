package com.haoniu.zzx.app_ts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.adapter.DianShangContryAdapter;
import com.haoniu.zzx.app_ts.adapter.DianShangNavAdapter;
import com.haoniu.zzx.app_ts.decoration.DividerGridItemDecoration;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.HttpUtils;
import com.haoniu.zzx.app_ts.http.JsonCallback;
import com.haoniu.zzx.app_ts.model.DianShangContryModel;
import com.haoniu.zzx.app_ts.model.DianShangNavModel;
import com.haoniu.zzx.app_ts.utils.GlideImageLoader;
import com.lzy.okgo.model.Response;
import com.umeng.analytics.MobclickAgent;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import self.androidbase.utils.DensityUtils;

/**
 * 电商汇
 */
public class DianShangHuiActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mBanner)
    Banner mBanner;
    @BindView(R.id.recycleView1)
    RecyclerView recycleView1;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mNestedScrollView)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.recycleView2)
    RecyclerView recycleView2;
    @BindView(R.id.llNoData)
    RelativeLayout llNoData;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_dian_shang_hui);
    }

    private List<DianShangContryModel> contryModels;
    private DianShangContryAdapter contryAdapter1, contryAdapter2;
    private List<DianShangNavModel> navModels;
    private List<BannerUrl> bannerUrlList;
    private DianShangNavAdapter navAdapter;
    private int contryIndex = -1;

    @Override
    protected void initView() {
        ViewGroup.LayoutParams layoutParams = mBanner.getLayoutParams();
        layoutParams.width = (int) DensityUtils.getWidthInPx(mContext);
        layoutParams.height = layoutParams.width * 35 / 64;
        mBanner.setLayoutParams(layoutParams);
        tvTitle.setText("电商汇");
        contryModels = new ArrayList<>();
        contryAdapter1 = new DianShangContryAdapter(contryModels);
        contryAdapter2 = new DianShangContryAdapter(contryModels);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mContext);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleView1.setLayoutManager(linearLayoutManager1);
        recycleView1.setHasFixedSize(true);
        recycleView1.setNestedScrollingEnabled(false);
        recycleView1.setAdapter(contryAdapter1);
        recycleView2.setLayoutManager(linearLayoutManager2);
        recycleView2.setAdapter(contryAdapter2);
        contryAdapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (navModels.size() == 0 || contryIndex != i) {
                    contryModels.get(contryIndex).setCheck(false);
                    contryIndex = i;
                    contryModels.get(contryIndex).setCheck(true);
                    contryAdapter1.notifyDataSetChanged();
                    contryAdapter2.notifyDataSetChanged();
                    navModels.clear();
                    requestNavs(i);
                }
            }
        });
        contryAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter,View view, int i) {
                if (navModels.size() == 0 || contryIndex != i) {
                    contryModels.get(contryIndex).setCheck(false);
                    contryIndex = i;
                    contryModels.get(contryIndex).setCheck(true);
                    contryAdapter1.notifyDataSetChanged();
                    contryAdapter2.notifyDataSetChanged();
                    navModels.clear();
                    requestNavs(i);
                }
            }
        });
        navModels = new ArrayList<>();
        navAdapter = new DianShangNavAdapter(navModels);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(mContext, 1, R.color.grey));
        mRecyclerView.setAdapter(navAdapter);
        navAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter,View view, int i) {
                startActivity(new Intent(mContext, OfficialActivity.class).putExtra("navModel", navModels.get(i)));
            }
        });
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY >= recycleView1.getTop()) {
                    recycleView1.setVisibility(View.INVISIBLE);
                    recycleView2.setVisibility(View.VISIBLE);
                } else {
                    recycleView1.setVisibility(View.VISIBLE);
                    recycleView2.setVisibility(View.INVISIBLE);
                }
            }
        });
        bannerUrls = new ArrayList<>();
        bannerUrlList = new ArrayList<>();
        requestBanner();
    }

    private List<String> bannerUrls;

    private void initBanner() {
        for (int i = 0; i < bannerUrlList.size(); i++) {
            bannerUrls.add(bannerUrlList.get(i).getBanner());
        }
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerAnimation(Transformer.Default);
        mBanner.isAutoPlay(true);
        if (bannerUrls.size() > 1) {
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            mBanner.setIndicatorGravity(BannerConfig.CENTER);
            mBanner.setDelayTime(3000);
        } else {
            mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        }
        mBanner.setImages(bannerUrls);
        mBanner.start();
    }

    @Override
    protected void initLogic() {
        requestContrys();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @OnClick(R.id.ll_back)
    public void onClick() {
        finish();
    }

    /**
     * 国家列表
     */
    private void requestBanner() {
        HttpUtils.requestGet(mContext, AppConfig.requestBanner, null, new JsonCallback<List<BannerUrl>>(mContext) {
            @Override
            public void onSuccess(Response<List<BannerUrl>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    bannerUrlList.addAll(response.body());
                    initBanner();
                }
            }
        });
    }

    /**
     * 国家列表
     */
    private void requestContrys() {
        HttpUtils.requestGet(mContext, AppConfig.requestContrys, null, new JsonCallback<List<DianShangContryModel>>(mContext) {
            @Override
            public void onSuccess(Response<List<DianShangContryModel>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    contryModels.addAll(response.body());
                }
                contryModels.get(0).setCheck(true);
                contryAdapter1.notifyDataSetChanged();
                contryAdapter2.notifyDataSetChanged();
                contryIndex = 0;
                requestNavs(0);
            }
        });
    }

    /**
     * 国家品牌列表
     */
    private void requestNavs(int contryIndex) {
        HttpUtils.requestGet(mContext, AppConfig.requestNavBrands + contryModels.get(contryIndex).getId(),
                null, new JsonCallback<List<DianShangNavModel>>(mContext) {
                    @Override
                    public void onSuccess(Response<List<DianShangNavModel>> response) {
                        if (response.body() != null && response.body().size() > 0) {
                            navModels.addAll(response.body());
                            mRecyclerView.setVisibility(View.VISIBLE);
                            llNoData.setVisibility(View.GONE);
                        } else {
                            llNoData.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                        }
                        navAdapter.notifyDataSetChanged();
                        if (recycleView2.getVisibility() == View.VISIBLE) {
                            int top = mBanner.getHeight();
                            mNestedScrollView.scrollTo(0, mBanner.getHeight());
                        }
                    }

                    @Override
                    public void onError(Response<List<DianShangNavModel>> response) {
                        super.onError(response);
                        llNoData.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                        navAdapter.notifyDataSetChanged();
                    }
                });
    }


    class BannerUrl {
        /**
         * banner : http://www.chanwu7.com/getData/Public/img/shophui.png
         */

        private String banner;

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }
    }
}
