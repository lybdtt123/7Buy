package com.haoniu.zzx.app_ts.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//

import com.bumptech.glide.Glide;
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
import com.haoniu.zzx.app_ts.utils.PreferenceUtils;
import com.haoniu.zzx.app_ts.utils.TrafficInfo;
import com.lzy.okgo.model.Response;
import com.qihoo.appstore.common.updatesdk.lib.UpdateHelper;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

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
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(mContext, MobclickAgent.EScenarioType.E_DUM_NORMAL);
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
//        getLiuLiang();
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
