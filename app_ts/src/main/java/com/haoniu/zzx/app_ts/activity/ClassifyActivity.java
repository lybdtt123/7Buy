package com.haoniu.zzx.app_ts.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.haoniu.zzx.app_ts.IndexBar.widget.IndexBar;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.adapter.BrandAdapter;
import com.haoniu.zzx.app_ts.decoration.DividerItemDecoration;
import com.haoniu.zzx.app_ts.decoration.TitleItemDecoration;
import com.haoniu.zzx.app_ts.http.ApiClient;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.ResultListener;
import com.haoniu.zzx.app_ts.model.BrandModel;
import com.haoniu.zzx.app_ts.model.ClassModel;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.haoniu.zzx.app_ts.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 品牌分类
 */
public class ClassifyActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edSearch)
    EditText edSearch;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;

    private ClassModel classModel;
    private String keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null) {
            classModel = (ClassModel) getIntent().getExtras().get("classModel");
        }
        mContext = this;
        initView();
        requestClassify();
    }

    private Context mContext;
    private LinearLayoutManager mManager;
    private TitleItemDecoration mDecoration;
    private List<BrandModel> models;
    private BrandAdapter adapter;

    private void initView() {
        tvTitle.setText("" + classModel.getCatename());
        models = new ArrayList<>();
        adapter = new BrandAdapter(mContext, models);
        rv.setLayoutManager(mManager = new LinearLayoutManager(mContext));
        rv.setAdapter(adapter);
        rv.addItemDecoration(mDecoration = new TitleItemDecoration(this, models));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
        ;//设置数据源
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0 && editable.toString().trim().length() > 0) {
                    keywords = editable.toString().trim();
                } else {
                    keywords = null;
                }
                requestClassify();
            }
        });
    }


    @OnClick(R.id.ll_back)
    public void onClick() {
        finish();
    }

    private void requestClassify() {
        models.clear();
        Map<String, Object> map = new HashMap<>();
        map.put("cid", classModel.getCid());
        map.put("groupid", classModel.getGroupid());
        if (keywords != null && !StringUtils.isEmpty(keywords)) {
            map.put("keywords", keywords);
        }
        ApiClient.requestNetHandle(mContext, AppConfig.searchUrl, "加载中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json) {
                if (json != null) {
                    List<BrandModel> brandModels = JSONArray.parseArray(json, BrandModel.class);
                    if (brandModels != null && brandModels.size() > 0) {
                        models.addAll(brandModels);
                        for (int i = 0; i < models.size(); i++) {
                            if (models.get(i).getMerchname().contains("*|%")) {
                                models.remove(i);
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                indexBar.setmSourceDatas(models);
            }

            @Override
            public void onFailure(String msg) {
                ToastUtils.showTextToast(mContext, msg);
            }
        });
    }
}
