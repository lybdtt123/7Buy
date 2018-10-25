package com.haoniu.zzx.app_ts.utils;

import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @author Administrator
 * 日期 2018/4/3
 * 描述
 */
public class WebViewSetUtil {
    public static void webSet(WebView webView) {
        webView.setInitialScale(25);
        WebSettings settings = webView.getSettings();
        //适应屏幕
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);// 设置允许访问文件数据
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }



}
