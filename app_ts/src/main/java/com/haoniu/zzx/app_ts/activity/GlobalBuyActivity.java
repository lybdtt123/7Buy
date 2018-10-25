package com.haoniu.zzx.app_ts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.adapter.GlobalLeftAdapter;
import com.haoniu.zzx.app_ts.adapter.GlobalRightAdapter;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.HttpUtils;
import com.haoniu.zzx.app_ts.http.JsonCallback;
import com.haoniu.zzx.app_ts.model.CategoryModel;
import com.haoniu.zzx.app_ts.model.CotegoryListModel;
import com.lzy.okgo.model.Response;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 全球购
 */
public class GlobalBuyActivity extends BaseActivity {

    @BindView(R.id.leftRecyclerView)
    RecyclerView leftRecyclerView;
    @BindView(R.id.rightRecyclerView)
    RecyclerView rightRecyclerView;
    @BindView(R.id.mNestedScrollView)
    NestedScrollView mNestedScrollView;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_global_buy);
    }

    private List<CategoryModel> categoryModels;
    private GlobalLeftAdapter leftAdapter;

    private List<CotegoryListModel> listModels;
    private GlobalRightAdapter rightAdapter;
    private int indexSel = 0;

    @Override
    protected void initView() {
        categoryModels = new ArrayList<>();
        leftAdapter = new GlobalLeftAdapter(categoryModels);
        leftRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        leftRecyclerView.setAdapter(leftAdapter);
        leftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (indexSel == i) {
                    return;
                } else {
                    categoryModels.get(indexSel).setCheck(false);
                    indexSel = i;
                    categoryModels.get(indexSel).setCheck(true);
                    leftAdapter.notifyDataSetChanged();
                    requestGlobalDetial(i);
                }
            }
        });

        listModels = new ArrayList<>();
        rightAdapter = new GlobalRightAdapter(listModels);
        rightRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        rightRecyclerView.setHasFixedSize(true);
        rightRecyclerView.setNestedScrollingEnabled(false);
        rightRecyclerView.setAdapter(rightAdapter);
        requestGlobal();
    }

    @Override
    protected void initLogic() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @OnClick({R.id.llGlobalBack, R.id.llGlobalSearch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llGlobalBack:
                finish();
                break;
            case R.id.llGlobalSearch:
                startActivity(new Intent(mContext, GoodsListActivity.class).putExtra("id", "0"));
//                startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestSearchGeneral));
                break;
        }
    }

    private void requestGlobal() {
        HttpUtils.requestGet(mContext, AppConfig.requestCategory, null, new JsonCallback<List<CategoryModel>>(mContext, "加载中...") {
            @Override
            public void onSuccess(Response<List<CategoryModel>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    categoryModels.addAll(response.body());
                    categoryModels.get(0).setCheck(true);
                    leftAdapter.notifyDataSetChanged();
                    requestGlobalDetial(0);
                }
            }
        });
    }

    private void requestGlobalDetial(int position) {
        listModels.clear();
        Map<String, Object> map = new HashMap<>();
        map.put("cateid", categoryModels.get(position).getId());
        HttpUtils.requestPosts(mContext, AppConfig.requestCategoryList, map, new JsonCallback<List<CotegoryListModel>>(mContext, "加载中...") {
            @Override
            public void onSuccess(Response<List<CotegoryListModel>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    listModels.addAll(response.body());
                }
                rightAdapter.notifyDataSetChanged();
                mNestedScrollView.smoothScrollTo(0, 0);
            }
        });
    }
}
