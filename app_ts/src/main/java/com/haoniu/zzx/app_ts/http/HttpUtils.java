package com.haoniu.zzx.app_ts.http;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.haoniu.zzx.app_ts.utils.L;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.lzy.okgo.model.HttpParams.MEDIA_TYPE_PLAIN;


/**
 * Created by Administrator on 2017/6/22.
 */

public class HttpUtils {

    public static <T> void requestPosts(Context context, String url, Map<String, Object> map, Callback<T> callback) {
        if (!AppContext.getInstance().isNetworkConnected()) {
            //没网络
//            listener.onFailure("网络连接异常,请检查您的网络设置");
            Response response = new Response();
            response.setException(new Throwable("网络连接异常,请检查您的网络设置"));
            callback.onError(response);
            return;
        }
        HttpParams params = new HttpParams();
        if (map != null) {
            L.d("TAG", "上传的参数:" + map.toString() + "请求地址:" + url);
            for (String key : map.keySet()) {
                params.put(key, map.get(key) == null ? "" : map.get(key).toString());
            }
        }

        OkGo.<T>post(url)//
                .isMultipart(true)
                .tag(context)//
                .params(params)//
                .execute(callback);
    }

    public static <T> void requestPosts(Context context, String url, Map<String, Object> map, String fileKey, Callback<T> callback) {
        HttpParams params = new HttpParams();
        List<HttpParams.FileWrapper> list = new ArrayList<>();
        for (String key : map.keySet()) {
            if (map.get(key) instanceof File) {
                HttpParams.FileWrapper fileWrapper = new HttpParams.FileWrapper((File) map.get(key), ((File) map.get(key)).getName(), MEDIA_TYPE_PLAIN);
                list.add(fileWrapper);
            }
            params.put(key, map.get(key) == null ? "" : map.get(key).toString());
        }
        OkGo.<T>post(url)//
                .isMultipart(true)
                .addFileWrapperParams(fileKey, list)
                .tag(context)//
                .params(params)//
                .execute(callback);
    }

    public static <T> void requestGet(Context context, String url, Map<String, Object> map, Callback<T> callback) {
        if (!AppContext.getInstance().isNetworkConnected()) {
            //没网络
            Response response = new Response();
            response.setException(new Throwable("网络连接异常,请检查您的网络设置"));
            callback.onError(response);
            return;
        }
        HttpParams params = new HttpParams();
        if (map != null) {
            L.d("TAG", "上传的参数:" + map.toString() + "请求地址:" + url);
            for (String key : map.keySet()) {
                params.put(key, map.get(key) == null ? "" : map.get(key).toString());
            }
        }
        OkGo.<T>get(url)//
                .cacheKey(url)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .tag(context)//
                .params(params)//
                .execute(callback);
    }

    public static <T> void requestCacheGet(Context context, String url, Map<String, Object> map, Callback<T> callback) {
        if (!AppContext.getInstance().isNetworkConnected()) {
            //没网络
            Response response = new Response();
            response.setException(new Throwable("网络连接异常,请检查您的网络设置"));
            callback.onError(response);
            return;
        }
        HttpParams params = new HttpParams();
        if (map != null) {
            L.d("TAG", "上传的参数:" + map.toString() + "请求地址:" + url);
            for (String key : map.keySet()) {
                params.put(key, map.get(key) == null ? "" : map.get(key).toString());
            }
        }
        OkGo.<T>get(url)//
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .cacheKey(url)
                .tag(context)//
                .params(params)//
                .execute(callback);
    }


}
