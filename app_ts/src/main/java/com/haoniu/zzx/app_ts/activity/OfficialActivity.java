package com.haoniu.zzx.app_ts.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.AppContext;
import com.haoniu.zzx.app_ts.model.DianShangNavModel;
import com.haoniu.zzx.app_ts.utils.PreferenceUtils;
import com.just.library.AgentWeb;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 品牌官网
 */
public class OfficialActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.container)
    RelativeLayout container;
    private DianShangNavModel navModel;

    protected AgentWeb mAgentWeb;
    private String url = "";

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_official);
    }

    @Override
    protected void initView() {
        if (navModel != null) {
            if (!checkEmpty(navModel.getMerchname())) {
                tvTitle.setText(navModel.getMerchname());
            } else {
                tvTitle.setText("");
            }
            url = navModel.getAddress();
        } else {
            tvTitle.setText("");
        }
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1))//
                .useDefaultIndicator()//
                .setIndicatorColor(getResources().getColor(R.color.colorPrimary))
                .setSecutityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()//
                .ready()
                .go(url);
//        mAgentWeb = AgentWeb.with(this)//
//                .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1))//
//                .closeProgressBar()
////                .setWebChromeClient(mWebChromeClient)
////                .setWebViewClient(mWebViewClient)
//                .setSecutityType(AgentWeb.SecurityType.strict)
//                .createAgentWeb()//
//                .ready()
//                .go(url);
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }
    };

    @Override
    protected void initLogic() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        navModel = (DianShangNavModel) extras.getSerializable("navModel");
    }

    @OnClick({R.id.ll_back, R.id.tvWant})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tvWant:
                if (checkEmpty(PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null))) {
                    startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestLogin));
                    return;
                }
                startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestIntention + navModel.getId()));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
