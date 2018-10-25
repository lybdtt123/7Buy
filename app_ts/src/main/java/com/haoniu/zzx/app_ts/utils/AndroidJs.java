package com.haoniu.zzx.app_ts.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.haoniu.zzx.app_ts.activity.CommentHtmlActivity;

/**
 * Created by zzx on 2018/04/19/上午 11:51
 */

public class AndroidJs {
    private WebView web;
    private Activity mActivity;

    public AndroidJs(WebView mWeb, Activity mActivity) {
        this.web = mWeb;
        this.mActivity = mActivity;
    }

    /**
     * 更新图片
     */
    @JavascriptInterface
    public void headimg() {

    }

    /**
     *
     */
    @JavascriptInterface
    public void uploadCard(int i) {

    }


}
