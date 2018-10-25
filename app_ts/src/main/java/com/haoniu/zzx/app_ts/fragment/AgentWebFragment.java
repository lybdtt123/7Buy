package com.haoniu.zzx.app_ts.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.H5PayCallback;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.ShareDialog;
import com.haoniu.zzx.app_ts.activity.ActivityActivity;
import com.haoniu.zzx.app_ts.activity.ClassifyActivity;
import com.haoniu.zzx.app_ts.activity.GoodsDetailActivity;
import com.haoniu.zzx.app_ts.activity.GoodsListActivity;
import com.haoniu.zzx.app_ts.activity.HomePageActivity;
import com.haoniu.zzx.app_ts.activity.WebviewActivity;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.AppContext;
import com.haoniu.zzx.app_ts.http.FileUploadService;
import com.haoniu.zzx.app_ts.http.ServerData;
import com.haoniu.zzx.app_ts.model.ClassModel;
import com.haoniu.zzx.app_ts.model.JsToGoToActivityInfo;
import com.haoniu.zzx.app_ts.model.MeiQiaModel;
import com.haoniu.zzx.app_ts.model.ShareModel;
import com.haoniu.zzx.app_ts.myinterface.CommonEnity;
import com.haoniu.zzx.app_ts.utils.DataCleanManager;
import com.haoniu.zzx.app_ts.utils.PreferenceUtils;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.haoniu.zzx.app_ts.utils.ToastUtils;
import com.haoniu.zzx.app_ts.wxapi.WXPay;
import com.haoniu.zzx.app_ts.wxapi.WeChatPayService;
import com.jph.takephoto.app.TakePhotoFragment;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;
import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.just.library.WebDefaultSettingsManager;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import iosdialog.dialog.listener.OnOperItemClickL;
import iosdialog.dialog.widget.ActionSheetDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import self.androidbase.utils.ImageUtils;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by zzx on 2017/9/27 0027.
 */

public class AgentWebFragment extends TakePhotoFragment implements FragmentKeyDown {

    public static final String URL_KEY = "url_key";
    protected AgentWeb mAgentWeb;
    //    private String url;
    private String nowUrl;

    public static AgentWebFragment getInstance(Bundle bundle) {
        AgentWebFragment mAgentWebFragment = new AgentWebFragment();
        if (bundle != null)
            mAgentWebFragment.setArguments(bundle);

        return mAgentWebFragment;
    }
//    public AgentWebFragment(String url) {
//        this.url = url;
//    }

    private Context mContext;
    private boolean isCard;
    private boolean isZM;//判断是都是正面
    private String mUrl;

    private void setCookies(String cookie) {
        CookieSyncManager.createInstance(getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        StringBuilder sbCookie = new StringBuilder();
        sbCookie.append("57da___ewei_shopv2_member_session_3=" + cookie);
        sbCookie.append(";" + "path=/");
        String cookieValue = sbCookie.toString();
        cookieManager.setCookie(getUrl()
                , cookieValue);//cookies是在HttpClient中获得的cookie
//        cookieManager.setCookie(getUrl()
//                , "57da___ewei_shopv2_member_session_3=" + cookie);//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_agentweb, container, false);
    }

    private View mRoot;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRoot = view;
        initAgentWeb();
        initView(view);
        EventBus.getDefault().register(this);
    }

    private void initAgentWeb() {
        if (PreferenceUtils.getStringPreferences(getContext(), AppContext.getInstance().COOKIE, null) != null
                && !StringUtils.isEmpty(PreferenceUtils.getStringPreferences(getContext(), AppContext.getInstance().COOKIE, null))) {
            setCookies(PreferenceUtils.getStringPreferences(getContext(), AppContext.getInstance().COOKIE, null));
        }
        mUrl = getUrl();
        mAgentWeb = AgentWeb.with(getActivity(), this)
                .setAgentWebParent((ViewGroup) mRoot, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//
                .setIndicatorColorWithHeight(-1, -1)
                .setWebSettings(getSettings())
                .setWebViewClient(mWebViewClient)
                .setWebChromeClient(mWebChromeClient)
                .setReceivedTitleCallback(mCallback)
                .setSecurityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()
                .ready()
                .go(mUrl);
        mContext = getContext();
        mAgentWeb.getJsInterfaceHolder().addJavaObject("app", new JavaScriptInterface());
    }

    /**
     * EventBus接收消息
     *
     * @param center 消息接收
     */
    @Subscribe
    public void onEventMainThread(CommonEnity center) {
        if (center.getType().equals("addGoodsSuc")) {
            mAgentWeb.loadUrl(getUrl());
        } else if (center.getType().equals("wxpay")) {
            mAgentWeb.loadUrl(mUrl);
            needClearHistory = true;
        }
    }

    public WebDefaultSettingsManager getSettings() {
        return WebDefaultSettingsManager.getInstance();
    }

    public String getUrl() {
        if (getArguments() != null) {
            return getArguments().getString(URL_KEY);
        }
        return "";
    }

    private boolean needClearHistory = false;

    protected ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {


        }
    };
    protected WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //  super.onProgressChanged(view, newProgress);
            //Log.i(TAG,"onProgressChanged:"+newProgress+"  view:"+view);
        }

//        @Override
//        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//            return super.onJsAlert(view, url, message, result);
//        }
    };
    protected WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return shouldOverrideUrlLoading(view, request.getUrl() + "");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!(url.startsWith("http") || url.startsWith("https"))) {
                return true;
            }
            final PayTask task = new PayTask((Activity) mContext);
            boolean isIntercepted = task.payInterceptorWithUrl(url, true, new H5PayCallback() {
                @Override
                public void onPayResult(final H5PayResultModel result) {
                    // 支付结果返回
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAgentWeb.loadUrl(mUrl);
                            needClearHistory = true;
//                            if (result.getResultCode().equals("9000")) {
//                                mAgentWeb.loadUrl(mUrl);
//                                needClearHistory = true;
//                            } else {
//                                ToastUtils.showTextToast(mContext, "支付失败，请到订单列表重新支付");
//                            }
                        }
                    });
                }
            });
            if (!isIntercepted) {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
            if (needClearHistory) {
                needClearHistory = false;
                view.clearHistory();//清除历史记录
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (url.equals(AppConfig.requestIndex) || url.equals(AppConfig.requestTiao)
                    || url.equals(AppConfig.requestShare)
                    || url.equals(AppConfig.requestShop) || url.equals(AppConfig.requestMy)
                    || url.contains("login")) {
                EventBus.getDefault().post(new CommonEnity<>("showMenu"));
            } else {
                EventBus.getDefault().post(new CommonEnity<>("hideMenu"));
            }
            nowUrl = url;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            nowUrl = url;
            if (url.equals(AppConfig.requestIndex) || url.equals(AppConfig.requestTiao)
                    || url.equals(AppConfig.requestShare)
                    || url.equals(AppConfig.requestShop) || url.equals(AppConfig.requestMy)
                    || url.contains("login")) {
                EventBus.getDefault().post(new CommonEnity<>("showMenu"));
            } else {
                EventBus.getDefault().post(new CommonEnity<>("hideMenu"));
            }
            super.onPageFinished(view, url);
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10103) {
            UMShareAPI.get(mContext).onActivityResult(requestCode, resultCode, data);
        } else {
            mAgentWeb.uploadFileResult(requestCode, resultCode, data); //1.3.0开始 废弃该api ，没有api代替 ,使用 ActionActivity 绕行该方法 ,降低使用门槛
        }
    }

    protected void initView(View view) {
        pageNavigator(View.GONE);
    }


    private void pageNavigator(int tag) {

    }

    @Override
    public boolean onFragmentKeyDown(int keyCode, KeyEvent event) {
        return mAgentWeb.handleKeyEvent(keyCode, event);
    }

    public class JavaScriptInterface {
        /**
         * 更新图片
         */
        @JavascriptInterface
        public void headimg() {
            isCard = false;
            showPhotoDialog();
        }

        /**
         * show底部
         */
        @JavascriptInterface
        public void showBottomTabBar() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new CommonEnity<>("showMenu"));
                }
            });
        }

        /**
         * 上传身份证照片
         */
        @JavascriptInterface
        public void uploadCard(int i) {
            if (i == 0) {
                isZM = true;
            } else {
                isZM = false;
            }
            isCard = true;
            showPhotoDialog();
        }

        @JavascriptInterface
        public void UMShareAction(String msg) {
            shareModel = com.alibaba.fastjson.JSONObject.parseObject(msg, ShareModel.class);
            showShareDialog(1);
        }

        /**
         * 分享帖子
         */
        @JavascriptInterface
        public void UMShareTIEZIAction(String msg) {
            shareModel = com.alibaba.fastjson.JSONObject.parseObject(msg, ShareModel.class);
            showShareDialog(2);
        }

        /**
         * 微信支付
         */
        @JavascriptInterface
        public void wxpay(String msg) {
            if (msg != null) {
                ServerData data = com.alibaba.fastjson.JSONObject.parseObject(msg, ServerData.class);
                if (data.getData() != null) {
                    payWX(data.getData());
                }
            }
            Log.e("wxpay -- fragment", msg);
        }

        /**
         * 点击进入搜索界面
         */
        @JavascriptInterface
        public void enterSearchVC(String msg) {
            if (msg != null) {
                ClassModel model = com.alibaba.fastjson.JSONObject.parseObject(msg, ClassModel.class);
                startActivity(new Intent(mContext, ClassifyActivity.class).putExtra("classModel", model));
            }
        }

        /**
         * 跳往商品筛选的方法--传入分类Id  cate   shopId
         */
        @JavascriptInterface
        public void enterGoodsFliterVCAddCateIdandShopId(String id, String shopId) {
            if (!StringUtils.isEmpty(id)) {
                startActivity(new Intent(mContext, GoodsListActivity.class).putExtra("id", id));
            } else if (!StringUtils.isEmpty(shopId)) {
                startActivity(new Intent(mContext, GoodsListActivity.class).putExtra("id", shopId).putExtra("shop", true));
            }
        }

        /**
         * 关闭当前页面
         */
        @JavascriptInterface
        public void nativeBackBtnAction() {
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!mAgentWeb.back()) {
                        ((Activity) mContext).finish();
                    }
                }
            });
        }

        /**
         * 关闭当前页面
         */
        @JavascriptInterface
        public void gotoMainVCAction() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new CommonEnity<>("switch"));
                }
            });
//            EventBus.getDefault().post(new CommonEnity<>("switch"));
        }

        /**
         * 跳到商品详情页
         */
        @JavascriptInterface
        public void gotoGoodsDetailAction(String id) {
            startActivity(new Intent(mContext, GoodsDetailActivity.class).putExtra("id", id));
        }

        /**
         * 获取openid 推送使用
         */
        @JavascriptInterface
        public void getAppOpenid(final String openid) {
            JPushInterface.setAlias(mContext, openid, new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    String s1 = s;
                }
            });
            JPushInterface.resumePush(mContext);
        }

        /**
         * 设置cookie
         */
        @JavascriptInterface
        public void saveWebCookie(String cookie) {
            PreferenceUtils.saveStringPreferences(mContext, AppContext.getInstance().COOKIE, cookie);
            ((Activity) mContext).finish();
        }

        /**
         * 客服
         */
        @JavascriptInterface
        public void openCustomer(String json) {
            if (json != null) {
                MeiQiaModel model = JSON.parseObject(json, MeiQiaModel.class);
                if (model != null)
                    conversationWrapper(model);
            }
        }

        /**
         * 清除缓存
         */
        @JavascriptInterface
        public void clearAPPCache() {
            DataCleanManager.clearAllCache(mContext);
            ToastUtils.showTextToast(mContext, "清除成功!");
        }

        /**
         * 退出登陆
         */
        @JavascriptInterface
        public void NativeLogOut() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new CommonEnity<>("logout"));
                    PreferenceUtils.saveStringPreferences(mContext, AppContext.getInstance().COOKIE, "");
                }
            });
        }

//        /**
//         * 登录
//         */
//        @JavascriptInterface
//        public void NativeGotoLoginVC() {
//            startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestLogin));
//        }

        /**
         * 通知前往活动界面
         */
        @JavascriptInterface
        public void toGoToActivity(String json) {
            if (!TextUtils.isEmpty(json)) {
                JsToGoToActivityInfo info = JSON.parseObject(json, JsToGoToActivityInfo.class);
                if (info != null) {
                    int flag = TextUtils.isEmpty(info.getFlag()) ? 1 : Integer.parseInt(info.getFlag());
                    mContext.startActivity(new Intent(mContext, ActivityActivity.class)
                            .putExtra("shareUrl", "")//TODO 分享的url暂时没有
                            .putExtra("flag", flag).putExtra("id", info.getId()).putExtra("title", info.getTitle())
                            .putExtra("desc", "")
                            .putExtra("thumb", ""));
                }
            }
        }
    }

    private void payWX(String msg) {
        WXPay wxPay = JSON.parseObject(msg, WXPay.class);
        if (wxPay != null) {
            WeChatPayService weChatPayService = new WeChatPayService(mContext, wxPay);
            weChatPayService.pay();
        }
    }


    final String[] stringItems = {"相册", "相机"};
    private ExpandableListView elv;
    private View decorView;
    private ActionSheetDialog photoDialog;
    private String myPath;
    private File imgFile;

    private void showPhotoDialog() {
        if (photoDialog == null) {
            photoDialog = new ActionSheetDialog(mContext, stringItems, elv);
        }
        photoDialog.isTitleShow(false);
        myPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + +System.currentTimeMillis()
                + ".jpg";
        photoDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    photoDialog.dismiss();
                    selectPhoto();
                } else {
                    photoDialog.dismiss();
                    takePhoto();
                }
            }
        });
        photoDialog.show();
    }

    private void selectPhoto() {
        Uri imageUri = Uri.fromFile(new File(myPath));
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ToastUtils.showTextToast(mContext, "请打开相册权限");
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            return;
        }
        if (isCard) {
            getTakePhoto().onPickFromGallery();
        } else {
            getTakePhoto().onPickFromGalleryWithCrop(imageUri, getCropOptions());
        }
    }

    private void takePhoto() {
        Uri imageUri = Uri.fromFile(new File(myPath));
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ToastUtils.showTextToast(mContext, "请打开相机权限");
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{android.Manifest.permission.CAMERA}, 100);
            return;
        }
        if (isCard) {
            getTakePhoto().onPickFromCapture(imageUri);
        } else {
            getTakePhoto().onPickFromCaptureWithCrop(imageUri, getCropOptions());
        }
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (isCard) {
            uploadCardFile(result.getImage().getOriginalPath());
        } else {
            uploadFile(result.getImage().getOriginalPath());
        }
    }

    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(600).setAspectY(600);
        builder.setWithOwnCrop(true);
        return builder.create();
    }

    private void uploadFile(String path) {
        imgFile = new File(path);
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(AppConfig.main)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FileUploadService fileUploadService = retrofit.create(FileUploadService.class);

        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);

        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("upload_file", imgFile.getAbsolutePath(), requestFile);

        // 添加描述
        String descriptionString = "hello, 这是文件描述";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);
        Call<ResponseBody> upload = fileUploadService.upload(description, body);
        upload.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result = null;
                if (imgFile != null && imgFile.exists() && !isCard) {
                    imgFile.delete();
                }
                try {
                    result = response.body().source().readUtf8().toString();
                    ServerData serverData = JSON.parseObject(result, ServerData.class);
                    if (!TextUtils.isEmpty(result)) {
                        if (serverData.getCode() == 0) {
                            ToastUtils.showTextToast(mContext, serverData.getMessge());
                        } else {
                            JSONObject dataObject = new JSONObject(serverData.getData());
                            String address = dataObject.getString("address");
                            if (address != null && !StringUtils.isEmpty(address)) {
                                mAgentWeb.loadUrl("javascript:headimgUrl('" + address + "')");
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("result", result);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (imgFile != null && imgFile.exists() && !isCard) {
                    imgFile.delete();
                }
            }
        });
    }

    private boolean isDel;

    private void uploadCardFile(String path) {
        long fileSize = 0;
        try {
            fileSize = getFileSize(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fileSize != 0 && fileSize > 1024 * 300) {
            isDel = true;
            ImageUtils.doCompressImage(path, myPath);
        } else {
            isDel = false;
            myPath = path;
        }
        imgFile = new File(myPath);
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(AppConfig.main)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FileUploadService fileUploadService = retrofit.create(FileUploadService.class);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("upload_file", imgFile.getAbsolutePath(), requestFile);
        String descriptionString = "1";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);
        Call<ResponseBody> upload = fileUploadService.uploadCard(description, body);
        upload.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (imgFile != null && imgFile.exists() && isDel) {
                    imgFile.delete();
                }
                String result = null;
                try {
                    result = response.body().source().readUtf8().toString();
                    if (!TextUtils.isEmpty(result)) {
                        ServerData serverData = JSON.parseObject(result, ServerData.class);
                        if (serverData.getCode() == 0) {
                            ToastUtils.showTextToast(mContext, serverData.getMessge());
                        } else {
                            JSONObject dataObject = new JSONObject(serverData.getData());
                            String address = dataObject.getString("address");
                            if (address != null && !StringUtils.isEmpty(address)) {
                                if (isZM) {
                                    mAgentWeb.loadUrl("javascript:ZMcardUrl('" + address + "')");
                                } else {
                                    mAgentWeb.loadUrl("javascript:FMcardUrl('" + address + "')");
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (imgFile != null && imgFile.exists() && !isCard) {
                    imgFile.delete();
                }
            }
        });
    }


    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    private long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;

    private void conversationWrapper(final MeiQiaModel model) {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    HashMap<String, String> clientInfo = new HashMap<>();
                    clientInfo.put("name", model.getNickname());
                    clientInfo.put("avatar", AppConfig.main + model.getAvatar());
                    Intent intent = new MQIntentBuilder(mContext)
                            .setClientInfo(clientInfo)
                            .setPreSendTextMessage("商品名称:" + model.getTitle() + "\n商品链接:" + model.getLink())
                            .setPreSendImageMessage(new File(model.getThumb()))
                            .build();
                    startActivity(intent);
                    conversation(model.getNickname(), model.getAvatar());
                }
            });
        }
    }

    private void conversation(String name, String avatar) {

    }

    private UMWeb umWeb;


    private ShareModel shareModel;
    private ShareDialog shareDialog;

    private void showShareDialog(int type) {
        final UMImage shareImage;
        if (shareModel.getThumbs().startsWith("http")) {
            shareImage = new UMImage(mContext, shareModel.getThumbs());//网络图片
        } else {
            if (type == 1) {
                shareImage = new UMImage(mContext, "http://www.chanwu7.com/attachment/" + shareModel.getThumbs());//网络图片
            } else {
                shareImage = new UMImage(mContext, "http://www.chanwu7.com/" + shareModel.getThumbs());//网络图片
            }
        }
        umWeb = new UMWeb(shareModel.getGoodsurl());
        umWeb.setTitle(shareModel.getTitle());
        umWeb.setDescription(shareModel.getName());
        umWeb.setThumb(shareImage);
        if (shareDialog == null) {
            shareDialog = new ShareDialog(mContext);
            shareDialog.setClick(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new ShareAction(getActivity())
                            .setPlatform(SHARE_MEDIA.QQ)//传入平台
                            .withMedia(umWeb)
                            .setCallback(shareListener)//回调监听器
                            .share();
                    shareDialog.dismiss();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new ShareAction(getActivity())
                            .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                            .withMedia(umWeb)
                            .setCallback(shareListener)//回调监听器
                            .share();
                    shareDialog.dismiss();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new ShareAction(getActivity())
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                            .withMedia(umWeb)
                            .setCallback(shareListener)//回调监听器
                            .share();
                    shareDialog.dismiss();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    boolean checkSinaVersion = isWeiboInstalled();
                    if (!checkSinaVersion) {
                        ToastUtils.showTextToast(getActivity(), "请安装微博客户端");
                        return;
                    }
                    new ShareAction(getActivity())
                            .setPlatform(SHARE_MEDIA.SINA)//传入平台
                            .withMedia(umWeb)
                            .setCallback(shareListener)//回调监听器
                            .share();
                    shareDialog.dismiss();
                }
            });
        }
        shareDialog.show();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            platform.toString();
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showTextToast(mContext, "分享成功!");
            mAgentWeb.loadUrl("javascript:AppshareSuccess(1)");
            mAgentWeb.loadUrl(nowUrl);
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showTextToast(mContext, t.getMessage().toString());
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showTextToast(mContext, "分享已取消!");
        }
    };

    public boolean isWeiboInstalled() {
        PackageManager pm;
        if ((pm = mContext.getApplicationContext().getPackageManager()) == null) {
            return false;
        }
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo info : packages) {
            String name = info.packageName.toLowerCase(Locale.ENGLISH);
            if ("com.sina.weibo".equals(name)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        mAgentWeb.destroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
