package com.haoniu.zzx.app_ts.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.model.GoodsDetailModel;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;

/**
 * Created by zzx on 2017/12/14 0014.
 */

public class QuestFragment extends BaseFragment {
    @BindView(R.id.mWebView)
    WebView mWebView;

    private GoodsDetailModel.QuestionBean questionBean;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quest;
    }

    @Override
    protected void initData() {
        super.initData();
        mWebView.loadDataWithBaseURL(null, questionBean.getContent() + "", "text/html", "UTF-8", null);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        questionBean = (GoodsDetailModel.QuestionBean) bundle.get("questionBean");
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(mContext);
//        MobclickAgent.onPageStart("Quest");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(mContext);
//        MobclickAgent.onPageEnd("Quest");
    }

}
