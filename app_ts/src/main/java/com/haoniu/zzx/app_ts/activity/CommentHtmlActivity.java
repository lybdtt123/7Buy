package com.haoniu.zzx.app_ts.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.utils.AndroidJs;
import com.haoniu.zzx.app_ts.utils.WebViewSetUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zzx on 2018/04/19/上午 11:44
 */

public class CommentHtmlActivity extends BaseActivity {
    @BindView(R.id.web)
    WebView mWeb;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_comment_html);
    }


    @Override
    protected void initView() {
        //声明WebSettings子类
        WebSettings webSettings = mWeb.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
//        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        WebViewSetUtil.webSet(mWeb);
        mWeb.addJavascriptInterface(new AndroidJs(mWeb, this), "app");
        mWeb.setWebViewClient(new WebViewClient() {
                                 @Override
                                 public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                     if (url.startsWith("http"))
                                         view.loadUrl(url);
                                     return true;
                                 }

                             }
        );
//        web.setWebChromeClient(new WebChromeClient(){
//            @Override
//            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//                toast(message);
//                return super.onJsAlert(view, url, message, result);
//
//            }
//        });
        mWeb.loadUrl("");
    }

    @Override
    protected void initLogic() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

}
