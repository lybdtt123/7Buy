package com.haoniu.zzx.app_ts.http;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


import com.awen.photo.FrescoImageLoader;
import com.haoniu.zzx.app_ts.BuildConfig;
import com.haoniu.zzx.app_ts.Constant;
import com.haoniu.zzx.app_ts.activity.WebviewActivity;
import com.haoniu.zzx.app_ts.utils.DynamicTimeFormat;
import com.haoniu.zzx.app_ts.utils.PreferenceUtils;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.haoniu.zzx.app_ts.utils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.meiqia.core.MQManager;
import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.common.QueuedWork;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import self.androidbase.app.SelfAppContext;

/**
 * Created by Administrator on 2016/1/25.
 */
public class AppContext extends SelfAppContext {
    public static AppContext appContext;
    public Boolean isOpen = false;
    private List activityList = new LinkedList();
    public String COOKIE = "COOKIE";

    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.setLogEnabled(true);
        appContext = this;
        FrescoImageLoader.init(this);
        QueuedWork.isUseThreadPool = false;
//        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "597b076d9f06fd731b00148b");
//        UMConfigure.setLogEnabled(true);
        //修改key - 友盟
        UMConfigure.init(this, Constant.UMENG_KEY, "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
//        UMConfigure.init(this, "59c1dde2677baa0430000017", "百度", UMConfigure.DEVICE_TYPE_PHONE, "");
        UMShareAPI.get(this);
//        Config.DEBUG = true;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        OkGo.getInstance().init(this);
        initMeiqiaSDK();
        setSmartRefreshLayout();
    }

    /**
     * 下拉刷新全局设置
     */
    private void setSmartRefreshLayout() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout refreshLayout) {
                return new ClassicsHeader(appContext).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout refreshLayout) {
                return new ClassicsFooter(appContext).setDrawableSize(20);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initMeiqiaSDK() {
        MQManager.setDebugMode(true);
        // 替换成自己的key
        String meiqiaKey = Constant.MEIQIA;//修改key -- 美恰
//        String meiqiaKey = "965bb03f65fabc9336ec56fd1292ed9a";
        MQConfig.init(this, meiqiaKey, new OnInitCallback() {
            @Override
            public void onSuccess(String clientId) {
            }

            @Override
            public void onFailure(int code, String message) {
                ToastUtils.showTextToast(appContext, "nt failure message = " + message);
            }
        });
    }

    {
        //修改key -- 微信分享
        PlatformConfig.setWeixin(Constant.WECHAT, Constant.UMENG_SECRET);
//        PlatformConfig.setWeixin("wxdfa3df5407d590e4", "babcee4fb9f305adf792196f9eef9a26");
        PlatformConfig.setQQZone(Constant.QQ_KEY, Constant.QQ_SECRET);
        PlatformConfig.setSinaWeibo(Constant.WEIBO_KEY, Constant.WEIBO_SECRET, "http://sns.whalecloud.com");
    }

    public boolean isLogin(Context mContext) {
        String cookie = PreferenceUtils.getStringPreferences(mContext, COOKIE, null);
        if (cookie == null || StringUtils.isEmpty(cookie)) {
            mContext.startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestLogin));
            return false;
        } else {
            return true;
        }
    }

    /**
     * check if sdcard exist
     *
     * @return
     */
    public boolean isSdcardExist() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static AppContext getInstance() {
        if (appContext == null)
            return new AppContext();
        return appContext;
    }

    public void setIsOpen(Boolean isopen) {

        this.isOpen = isopen;

    }

    public Boolean getIsOpen() {

        return isOpen;

    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }


    /**
     * 获取软件版本名称
     *
     * @return
     */
    public String getVersionName() {
        return getPackageInfo().versionName;
    }

    /**
     * 获取软件版本号
     *
     * @return
     */
    public int getVersionCode() {
        return getPackageInfo().versionCode;
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    /**
     * 检测网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected() {
        Log.d("hahahah", getSystemService(Context.CONNECTIVITY_SERVICE) + "    123");
        if (null != appContext.getSystemService(Context.CONNECTIVITY_SERVICE)) {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            return ni != null && ni.isConnectedOrConnecting();
        } else {
            return false;
        }
    }

    public void callUser(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 100);
            ToastUtils.showTextToast(context, "通话权限未打开!");
            return;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 根据一个网络连接(String)获取bitmap图像
     *
     * @param imageUri
     * @return
     */
    public Bitmap getbitmap(String imageUri) {
        // 显示网络上的图片
        Bitmap bitmap = null;
        try {
            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();

        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            bitmap = null;
        } catch (IOException e) {
            e.printStackTrace();
            bitmap = null;
        }
        return bitmap;
    }
}
