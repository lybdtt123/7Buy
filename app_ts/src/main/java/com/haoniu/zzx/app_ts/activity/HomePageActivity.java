package com.haoniu.zzx.app_ts.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//

import com.androidadvance.topsnackbar.TSnackbar;
import com.bumptech.glide.Glide;
import com.haoniu.zzx.app_ts.BaseFloatDailog;
import com.haoniu.zzx.app_ts.MyFloatDialog;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.dialog.AdvantageDialog;
import com.haoniu.zzx.app_ts.fragment.AgentWebFragment;
import com.haoniu.zzx.app_ts.fragment.FragmentKeyDown;
import com.haoniu.zzx.app_ts.fragment.GlobalBuyFragment;
import com.haoniu.zzx.app_ts.fragment.HomeNestedFragment;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.AppContext;
import com.haoniu.zzx.app_ts.http.HttpUtils;
import com.haoniu.zzx.app_ts.http.JsonCallback;
import com.haoniu.zzx.app_ts.myinterface.CommonEnity;
import com.haoniu.zzx.app_ts.myinterface.EventInterface;
import com.haoniu.zzx.app_ts.statusbar.ColorFinal;
import com.haoniu.zzx.app_ts.statusbar.StatusBarUtil;
import com.haoniu.zzx.app_ts.utils.PreferenceUtils;
import com.haoniu.zzx.app_ts.utils.TrafficInfo;
import com.lzy.okgo.model.Response;
import com.meiqia.core.MQManager;
import com.meiqia.core.MQMessageManager;
import com.meiqia.core.bean.MQAgent;
import com.meiqia.core.bean.MQMessage;
import com.meiqia.core.callback.OnGetMessageListCallback;
import com.meiqia.meiqiasdk.activity.MQConversationActivity;
import com.meiqia.meiqiasdk.controller.MQController;
import com.meiqia.meiqiasdk.controller.MessageReceiver;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.pgyersdk.update.DownloadFileListener;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;
import com.qihoo.appstore.common.updatesdk.lib.UpdateHelper;
import com.umeng.analytics.MobclickAgent;
import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.MoveType;
import com.yhao.floatwindow.PermissionListener;
import com.yhao.floatwindow.Screen;
import com.yhao.floatwindow.ViewStateListener;
import com.yw.game.floatmenu.FloatItem;
import com.yw.game.floatmenu.FloatLogoMenu;
import com.yw.game.floatmenu.FloatMenuView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页
 */
public class HomePageActivity extends BaseFragmentActivity {

    @BindView(R.id.llMenu)
    LinearLayout llMenu;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.ivClass)
    ImageView ivClass;
    @BindView(R.id.tvClass)
    TextView tvClass;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.iv4)
    ImageView iv4;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.iv5)
    ImageView iv5;
    @BindView(R.id.tv5)
    TextView tv5;
    private Fragment[] fragments;
    private HomeNestedFragment homeFragment;
    //    private HomeNestedFragment homeFragment;
    private GlobalBuyFragment globalBuyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(ColorFinal.HOME_COLOR);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(mContext, MobclickAgent.EScenarioType.E_DUM_NORMAL);

        /** 新版本 **/
        new PgyUpdateManager.Builder()
                .setForced(true)                //设置是否强制更新,非自定义回调更新接口此方法有用
                .setUserCanRetry(false)         //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
                .setDeleteHistroyApk(false)     // 检查更新前是否删除本地历史 Apk， 默认为true
                .setUpdateManagerListener(new UpdateManagerListener() {
                    @Override
                    public void onNoUpdateAvailable() {
                        //没有更新是回调此方法
                        Log.d("pgyer", "there is no new version");
                    }
                    @Override
                    public void onUpdateAvailable(AppBean appBean) {
                        //有更新回调此方法
                        Log.d("pgyer", "there is new version can update"
                                + "new versionCode is " + appBean.getVersionCode());
                        //调用以下方法，DownloadFileListener 才有效；
                        //如果完全使用自己的下载方法，不需要设置DownloadFileListener
                        PgyUpdateManager.downLoadApk(appBean.getDownloadURL());
                    }

                    @Override
                    public void checkUpdateFailed(Exception e) {
                        //更新检测失败回调
                        //更新拒绝（应用被下架，过期，不在安装有效期，下载次数用尽）以及无网络情况会调用此接口
                        Log.e("pgyer", "check update failed ", e);
                    }
                })
                //注意 ：
                //下载方法调用 PgyUpdateManager.downLoadApk(appBean.getDownloadURL()); 此回调才有效
                //此方法是方便用户自己实现下载进度和状态的 UI 提供的回调
                //想要使用蒲公英的默认下载进度的UI则不设置此方法
                .setDownloadFileListener(new DownloadFileListener() {
                    @Override
                    public void downloadFailed() {
                        //下载失败
                        Log.e("pgyer", "download apk failed");
                    }

                    @Override
                    public void downloadSuccessful(Uri uri) {
                        Log.e("pgyer", "download apk failed");
                        // 使用蒲公英提供的安装方法提示用户 安装apk
                        PgyUpdateManager.installApk(uri);
                    }

                    @Override
                    public void onProgressUpdate(Integer... integers) {
                        Log.e("pgyer", "update download apk progress" + integers);
                    }})
                .register();

        MessageReceiver mMessageReceiver = new MessageReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MQController.ACTION_AGENT_INPUTTING);
        intentFilter.addAction(MQController.ACTION_NEW_MESSAGE_RECEIVED);
        intentFilter.addAction(MQController.ACTION_CLIENT_IS_REDIRECTED_EVENT);
        intentFilter.addAction(MQController.ACTION_INVITE_EVALUATION);
        intentFilter.addAction(MQController.ACTION_AGENT_STATUS_UPDATE_EVENT);
        intentFilter.addAction(MQController.ACTION_BLACK_ADD);
        intentFilter.addAction(MQController.ACTION_BLACK_DEL);
        intentFilter.addAction(MQController.ACTION_QUEUEING_REMOVE);
        intentFilter.addAction(MQController.ACTION_QUEUEING_INIT_CONV);
        intentFilter.addAction(MQMessageManager.ACTION_END_CONV_AGENT);
        intentFilter.addAction(MQMessageManager.ACTION_END_CONV_TIMEOUT);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, intentFilter);
        initFloatingView();
    }
    /**
     * 设置状态栏的颜色
     */
    public void setStatusBarColor(int color){
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        //设置状态栏透明
//        StatusBarUtil.setTranslucentStatus(this)
        if (color == 0){
            color = ColorFinal.NORMAL_COLOR;
        }
        StatusBarUtil.setStatusBarColor(this, color);
    }
//    private void initTrafficInfo() {
//        try {
//            mHandler = new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//                    if (msg.what == 1) {
////                        L.d("TAG", "------" + trafficInfo.getRcvTraffic());
////                        L.d("TAG", "------" + msg.obj + "kb/s");
//                    }
//                    super.handleMessage(msg);
//                }
//
//            };
//            trafficInfo = new TrafficInfo(this, mHandler, TrafficInfo.getUid(mContext));
//            trafficInfo.startCalculateNetSpeed(); // 开启网速监测
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    Handler mHandler;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_home_page);
    }

    private FragmentManager manager;
    private FragmentTransaction ft;
    private int selIndex = 0;

    @Override
    protected void initView() {
        manager = getSupportFragmentManager();
        ft = manager.beginTransaction();
        fragments = new Fragment[5];
        homeFragment = HomeNestedFragment.getInstance(null);
//        homeFragment = new HomeNestedFragment();
        globalBuyFragment = new GlobalBuyFragment();
        Bundle bundle1 = new Bundle();
//        Bundle bundle2 = new Bundle();
        Bundle bundle3 = new Bundle();
        Bundle bundle4 = new Bundle();
        bundle1.putSerializable(AgentWebFragment.URL_KEY, AppConfig.requestShare);
//        bundle2.putSerializable(AgentWebFragment.URL_KEY, AppConfig.requestTiao);
        bundle3.putSerializable(AgentWebFragment.URL_KEY, AppConfig.requestShop);
        bundle4.putSerializable(AgentWebFragment.URL_KEY, AppConfig.requestMy);
        fragments[0] = homeFragment;
        fragments[1] = globalBuyFragment;
        fragments[2] = AgentWebFragment.getInstance(bundle1);
//        fragments[2] = AgentWebFragment.getInstance(bundle2);
        fragments[3] = AgentWebFragment.getInstance(bundle3);
        fragments[4] = AgentWebFragment.getInstance(bundle4);
        ft.replace(R.id.homeFrameLayout, fragments[0]);
        ft.commit();
//        initSelect(0);
        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        iv1.setBackgroundResource(R.mipmap.img_main1b);

        tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        iv1.setBackgroundResource(R.mipmap.img_main1b);

        if (checkEmpty(PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null))) {
//            showAdvantageDialog();
        } else {
            requestDiscount();
        }
//        //百度
//        checkBauDuVersionCode();
        //360
//        check360VersionCode();
    }

    private void requestDiscount() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, ""));
        HttpUtils.requestPosts(mContext, AppConfig.requestDiscount, map, new JsonCallback<String>() {
            @Override
            public void onSuccess(Response<String> response) {
                if (response.body() != null) {
                    JSONObject obj;
                    try {
                        obj = new JSONObject(response.body());
                        int status = obj.getInt("status");
                        if (status != 0) {
//                            showAdvantageDialog();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private AdvantageDialog advantageDialog;

    private void showAdvantageDialog() {
        if (advantageDialog == null) {
            advantageDialog = new AdvantageDialog(mContext);
            advantageDialog.setAdvantageClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkEmpty(PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null))) {
                        startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestLogin));
                    } else {
                        startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url",
                                "http://www.chanwu7.com/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=sale.coupon"));
                        advantageDialog.dismiss();
                    }
                }
            });
        }
        advantageDialog.show();
    }

    @Override
    protected void initLogic() {
        setEventInterface(new EventInterface() {
            @Override
            public void setEvent(CommonEnity enity) {
                if (enity.getType().equals("showMenu")) {
                    llMenu.setVisibility(View.VISIBLE);
                } else if (enity.getType().equals("hideMenu")) {
                    llMenu.setVisibility(View.GONE);
                } else if (enity.getType().equals("logout")) {
                    ft = getSupportFragmentManager().beginTransaction();
                    initSelect(0);
                    tv1.setTextColor(getResources().getColor(R.color.colorAccent));
                    iv1.setBackgroundResource(R.mipmap.img_main1b);
                } else if (enity.getType().equals("loginSuc")) {
                    ft = getSupportFragmentManager().beginTransaction();
                    initSelect(0);
                    tv1.setTextColor(getResources().getColor(R.color.colorAccent));
                    iv1.setBackgroundResource(R.mipmap.img_main1b);
                } else if (enity.getType().equals("switch")) {
                    ft = getSupportFragmentManager().beginTransaction();
                    initSelect(0);
                    tv1.setTextColor(getResources().getColor(R.color.colorAccent));
                    iv1.setBackgroundResource(R.mipmap.img_main1b);
                }
            }
        });
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    @SuppressLint("RestrictedApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 10103) {
            return;
        }
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> frags = fm.getFragments();
        if (frags != null) {
            handleResult(frags.get(frags.size() - 1), requestCode, resultCode, data);
        }
        return;
    }

    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param frag
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment frag, int requestCode, int resultCode,
                              Intent data) {
        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
    }


    @OnClick({R.id.ll1, R.id.ll2, R.id.ll3, R.id.ll4, R.id.ll5, R.id.llClass})
    public void onClick(View view) {
        ft = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.ll1:
                initSelect(0);
                tv1.setTextColor(getResources().getColor(R.color.colorAccent));
                iv1.setBackgroundResource(R.mipmap.img_main1b);
                break;
            case R.id.ll2:
                if (checkEmpty(PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null))) {
                    startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestLogin));
                    return;
                }
                initSelect(2);
                tv2.setTextColor(getResources().getColor(R.color.colorAccent));
                iv2.setBackgroundResource(R.mipmap.img_main2b);
                break;
            case R.id.ll3:
//                if (checkEmpty(PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null))) {
//                    startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestLogin));
//                    return;
//                }
//                initSelect(2);
//                tv3.setTextColor(getResources().getColor(R.color.colorAccent));
//                iv3.setBackgroundResource(R.mipmap.img_main3b);
                break;
            case R.id.ll4:
                if (checkEmpty(PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null))) {
                    startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestLogin));
                    return;
                }
                initSelect(3);
                tv4.setTextColor(getResources().getColor(R.color.colorAccent));
                iv4.setBackgroundResource(R.mipmap.img_main4b);
                break;
            case R.id.ll5:
                if (checkEmpty(PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null))) {
                    startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestLogin));
                    return;
                }
                initSelect(4);
                tv5.setTextColor(getResources().getColor(R.color.colorAccent));
                iv5.setBackgroundResource(R.mipmap.img_main5b);
                break;
            case R.id.llClass:
                initSelect(1);
                tvClass.setTextColor(getResources().getColor(R.color.colorAccent));
                ivClass.setBackgroundResource(R.mipmap.img_classify_b);
                break;
        }
    }

    private void initSelect(int index) {
        if (selIndex == index) {
            return;
        }
        selIndex = index;
        tv1.setTextColor(getResources().getColor(R.color.colorGrayText88));
        tv2.setTextColor(getResources().getColor(R.color.colorGrayText88));
        tvClass.setTextColor(getResources().getColor(R.color.colorGrayText88));
//        tv3.setTextColor(getResources().getColor(R.color.colorGrayText88));
        tv4.setTextColor(getResources().getColor(R.color.colorGrayText88));
        tv5.setTextColor(getResources().getColor(R.color.colorGrayText88));
        iv1.setBackgroundResource(R.mipmap.img_main1g);
        iv2.setBackgroundResource(R.mipmap.img_main2g);
        ivClass.setBackgroundResource(R.mipmap.img_classify);
//        iv3.setBackgroundResource(R.mipmap.img_main3g);
        iv4.setBackgroundResource(R.mipmap.img_main4g);
        iv5.setBackgroundResource(R.mipmap.img_main5g);
        ft.replace(R.id.homeFrameLayout, fragments[index]);
        ft.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (selIndex == 0 || selIndex == 1) {
            finishAll();
        } else {
            if (fragments[selIndex] instanceof AgentWebFragment) {
                AgentWebFragment mAgentWebFragment = (AgentWebFragment) fragments[selIndex];
                if (mAgentWebFragment != null) {
                    FragmentKeyDown mFragmentKeyDown = mAgentWebFragment;
                    if (mFragmentKeyDown.onFragmentKeyDown(keyCode, event))
                        return true;
                    else
                        return super.onKeyDown(keyCode, event);
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        Glide.with(getApplicationContext()).resumeRequests();

    }

    private TrafficInfo trafficInfo;

//    private void getLiuLiang() {
//        //1.获取一个包管理器。
//        PackageManager pm = getPackageManager();
//        //2.遍历手机操作系统 获取所有的应用程序的uid
//        ApplicationInfo info = getApplicationInfo();
//        int rx = (int) (TrafficStats.getUidRxBytes(info.uid) / 1048576);//下载的流量 byte
//    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.with(getApplicationContext()).pauseRequests();
        homeFragment.releaseThis();
        homeFragment = null;
    }

    // 360 版本检测
    private void check360VersionCode() {
        UpdateHelper.getInstance().init(getApplicationContext(), Color.parseColor("#00a0e9"));
        UpdateHelper.getInstance().setDebugMode(false);
        long intervalMillis = 10 * 1000L;           //第一次调用startUpdateSilent出现弹窗后，如果10秒内进行第二次调用不会查询更新
        UpdateHelper.getInstance().autoUpdate(getPackageName(), false, intervalMillis);
    }
    FloatWindow.B floatingView;
    public void initFloatingView(){
        setView();
//        floatingView = FloatWindow.with(getApplicationContext())
//                .setView(view)
//                .setWidth(Screen.width, 0.2f) //设置悬浮控件宽高
//                .setHeight(Screen.width, 0.2f)
//                .setX(Screen.width, 0.8f)
//                .setY(Screen.height, 0.3f)
//                .setMoveType(MoveType.slide,100,-100)
//                .setMoveStyle(500, new BounceInterpolator())
//                .setDesktopShow(false);
//        floatingView.build();
    }
    View view = null;
    TextView tv;
    public void setView(){
        view = LayoutInflater.from(this).inflate(R.layout.kefulayout, null);
        tv = view.findViewById(R.id.contentSize);
    }
    public void setFloatingView(String size){
        try{
            if (FloatWindow.get().isShowing()){
                FloatWindow.destroy();
            }
        }catch (NullPointerException e){
            Log.e("第一次", "show");
        }

//        ImageView imageView = new ImageView(getApplicationContext());
//        imageView.setImageResource(R.mipmap.img_banner_normal);
        tv.setText(size);
//        floatingView.setView(view).setViewStateListener(mViewStateListener)
//                .setPermissionListener(mPermissionListener);
        FloatWindow
                .with(getApplicationContext())
                .setFilter(false, MQConversationActivity.class)
//                .setFilter(true, GoodsDetailActivity.class)
//                .setFilter(true, HomePageActivity.class)
                .setView(view)
                .setWidth(Screen.width, 0.2f) //设置悬浮控件宽高
                .setHeight(Screen.width, 0.2f)
                .setX(Screen.width, 0.8f)
                .setY(Screen.height, 0.3f)
                .setMoveType(MoveType.slide,-100,-100)
                .setMoveStyle(500, new BounceInterpolator())
                .setViewStateListener(mViewStateListener)
                .setPermissionListener(mPermissionListener)
                .setDesktopShow(true)
                .build();
        FloatWindow.get().show();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conversationWrapper();
                Toast.makeText(HomePageActivity.this, "onClick", Toast.LENGTH_SHORT).show();
                FloatWindow.get().hide();
                FloatWindow.destroy();
            }
        });
    }
    /*private String getRunningActivityName() {
        String contextString = MQConversationActivity.this.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
    }*/
    private String getRunningActivityName(){
        ActivityManager activityManager=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }
    private PermissionListener mPermissionListener = new PermissionListener() {
        @Override
        public void onSuccess() {
            Log.d(TAG, "onSuccess");
        }

        @Override
        public void onFail() {
            Log.d(TAG, "onFail");
        }
    };

    private final static String TAG = "FloatingView";

    private ViewStateListener mViewStateListener = new ViewStateListener() {
        @Override
        public void onPositionUpdate(int x, int y) {
            Log.d(TAG, "onPositionUpdate: x=" + x + " y=" + y);
        }

        @Override
        public void onShow() {
            Log.d(TAG, "onShow");
        }

        @Override
        public void onHide() {
            Log.d(TAG, "onHide");
        }

        @Override
        public void onDismiss() {
            Log.d(TAG, "onDismiss");
        }

        @Override
        public void onMoveAnimStart() {
            Log.d(TAG, "onMoveAnimStart");
        }

        @Override
        public void onMoveAnimEnd() {
            Log.d(TAG, "onMoveAnimEnd");
        }

        @Override
        public void onBackToDesktop() {
            Log.d(TAG, "onBackToDesktop");
        }
    };
    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            if (getRunningActivityName().equals("com.meiqia.meiqiasdk.activity.MQConversationActivity")){
                return;
            }
            MQMessageManager messageManager = MQMessageManager.getInstance(context);
            // 获取 ACTION
            final String action = intent.getAction();

            // 接收新消息
            if (MQMessageManager.ACTION_NEW_MESSAGE_RECEIVED.equals(action)) {
                // 从 intent 获取消息 id
                String msgId = intent.getStringExtra("msgId");
                // 从 MCMessageManager 获取消息对象

                MQMessage message = messageManager.getMQMessage(msgId);
                String msg = message.getContent();
                TSnackbar snackbar = TSnackbar.make(getWindow().getDecorView().getRootView(), msg, TSnackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                textView.setTextColor(Color.BLACK);
                snackbar.setActionTextColor(Color.BLACK);
                snackbar.setAction("点击查看", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        conversationWrapper();
                    }
                });
                Toast.makeText(context, "客服消息：" + msg, Toast.LENGTH_SHORT).show();
                snackbar.show();
                MQManager.getInstance(context).getUnreadMessages(new OnGetMessageListCallback() {
                    @Override
                    public void onFailure(int i, String s) {

                    }

                    @Override
                    public void onSuccess(List<MQMessage> list) {
                        setFloatingView(String.valueOf(list.size()));
                    }
                });

                // do something
            }

            // 客服正在输入
            else if (MQMessageManager.ACTION_AGENT_INPUTTING.equals(action)) {
                // do something
            }

            // 客服转接
            else if (MQMessageManager.ACTION_AGENT_CHANGE_EVENT.equals(action)) {
                // 获取转接后的客服
                MQAgent mqAgent = messageManager.getCurrentAgent();
                // do something
            }
        }
    }
    /**
     * 美洽客服
     */
    private void conversationWrapper() {
        HashMap<String, String> clientInfo = new HashMap<>();
//        clientInfo.put("name", goodsDetailModel.getUser().getNickname());
//        clientInfo.put("avatar", AppConfig.main + goodsDetailModel.getUser().getAvatar());
        Intent intent = new MQIntentBuilder(mContext)
                .setClientInfo(clientInfo)
//                .setPreSendTextMessage("商品名称:" + goodsDetailModel.getGoods().getTitle() + "\n商品链接:" + goodsDetailModel.getUser().getLink())
//                .setPreSendImageMessage(new File(goodsDetailModel.getGoods().getThumb()))
                .build();
        startActivity(intent);
    }
    /**
     //     * 百度更新
     //     */
//    private void checkBauDuVersionCode() {
//        BDAutoUpdateSDK.cpUpdateCheck(this, new MyCPCheckUpdateCallback());
//    }

//    //
//    //
//    private class MyCPCheckUpdateCallback implements CPCheckUpdateCallback {
//
//        @Override
//        public void onCheckUpdateCallback(final AppUpdateInfo info, AppUpdateInfoForInstall infoForInstall) {
//            if (infoForInstall != null && !TextUtils.isEmpty(infoForInstall.getInstallPath())) {
//                BDAutoUpdateSDK.cpUpdateInstall(getApplicationContext(), infoForInstall.getInstallPath());
//            } else if (info != null) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                builder.setTitle("版本更新")
//                        .setMessage(Html.fromHtml(info.getAppChangeLog()))
//                        .setNeutralButton("普通升级", null)
//                        .setPositiveButton("智能升级", null)
//                        .setCancelable(info.getForceUpdate() != 1)
//                        .setOnKeyListener(new DialogInterface.OnKeyListener() {
//                            @Override
//                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                                    return true;
//                                }
//                                return false;
//                            }
//                        });
//                if (info.getForceUpdate() != 1) {
//                    builder.setNegativeButton("暂不升级", null);
//                }
//                final AlertDialog dialog = builder.show();
//                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                                BDAutoUpdateSDK.cpUpdateDownloadByAs(mContext);
//                            }
//                        });
//                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        BDAutoUpdateSDK.cpUpdateDownload(mContext, info, new UpdateDownloadCallback());
//                    }
//                });
//            } else {
//            }
//        }
//    }

//    private class UpdateDownloadCallback implements CPUpdateDownloadCallback {
//
//        @Override
//        public void onDownloadComplete(String apkPath) {
//            BDAutoUpdateSDK.cpUpdateInstall(getApplicationContext(), apkPath);
//        }
//
//        @Override
//        public void onStart() {
//        }
//
//        @Override
//        public void onPercent(int percent, long rcvLen, long fileSize) {
//        }
//
//        @Override
//        public void onFail(Throwable error, String content) {
//        }
//
//        @Override
//        public void onStop() {
//        }
//
//    }

}
