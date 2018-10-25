package com.haoniu.zzx.app_ts.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.H5PayCallback;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.ShareDialog;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.AppContext;
import com.haoniu.zzx.app_ts.http.FileUploadService;
import com.haoniu.zzx.app_ts.http.ServerData;
import com.haoniu.zzx.app_ts.model.ClassModel;
import com.haoniu.zzx.app_ts.model.MeiQiaModel;
import com.haoniu.zzx.app_ts.model.ShareModel;
import com.haoniu.zzx.app_ts.myinterface.CommonEnity;
import com.haoniu.zzx.app_ts.utils.DataCleanManager;
import com.haoniu.zzx.app_ts.utils.PreferenceUtils;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.haoniu.zzx.app_ts.utils.ToastUtils;
import com.haoniu.zzx.app_ts.wxapi.WXPay;
import com.haoniu.zzx.app_ts.wxapi.WeChatPayService;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;
import com.just.library.AgentWeb;
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

/**
 * 网页
 */
public class WebviewActivity extends TakePhotoActivity {
    protected AgentWeb mAgentWeb;
    private RelativeLayout mRelativeLayout;
    private String mUrl;
    private Context mContext;

    private boolean isCard;
    private boolean isZM;//判断是都是正面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mContext = this;
        mUrl = getIntent().getStringExtra("url");
        initView();
        EventBus.getDefault().register(this);
    }

    /**
     * EventBus接收消息
     *
     * @param center 消息接收
     */
    @Subscribe
    public void onEventMainThread(CommonEnity center) {
        if (center.getType().equals("loginSuc")) {
            finish();
        } else if (center.getType().equals("wxpay")) {
//            mUrl = AppConfig.orderUrl;
//            mAgentWeb.loadUrl(mUrl);
            finish();
            needClearHistory = true;
        }
    }

    private void setCookies(String cookie) {
//        AgentWebConfig.syncCookieToWebView(url, "57da___ewei_shopv2_member_session_3=" + cookie);
        CookieSyncManager.createInstance(mContext);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        StringBuilder sbCookie = new StringBuilder();
        sbCookie.append("57da___ewei_shopv2_member_session_3=" + cookie);
        sbCookie.append(";" + "path=/");
        String cookieValue = sbCookie.toString();
        cookieManager.setCookie(mUrl
                , cookieValue);//cookies是在HttpClient中获得的cookie
//        cookieManager.setCookie(url
//                , "57da___ewei_shopv2_member_session_3=" + cookie);//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
    }

    private void initView() {
        if (PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null) != null
                && !StringUtils.isEmpty(PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null))) {
            setCookies(PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null));
        }
        mRelativeLayout = (RelativeLayout) this.findViewById(R.id.container);
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(mRelativeLayout, new LinearLayout.LayoutParams(-1, -1))//
                .closeProgressBar()//
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setSecutityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()//
                .ready()
                .go(mUrl);
        mAgentWeb.getJsInterfaceHolder().addJavaObject("app", new JavaScriptInterface());
        if (mUrl.equals(AppConfig.requestLogin)) {
            View view = View.inflate(mContext, R.layout.view_back, null);
            mRelativeLayout.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    private boolean needClearHistory = false;
    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, String url) {
            if (!(url.startsWith("http") || url.startsWith("https"))) {
                return true;
            }
            /**
             * 推荐采用的新的二合一接口(payInterceptorWithUrl),只需调用一次
             */
            final PayTask task = new PayTask(WebviewActivity.this);
            boolean isIntercepted = task.payInterceptorWithUrl(url, true, new H5PayCallback() {
                @Override
                public void onPayResult(final H5PayResultModel result) {
                    // 支付结果返回
                    WebviewActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mUrl = AppConfig.orderUrl;
                            mAgentWeb.loadUrl(mUrl);
                            needClearHistory = true;
//                            if (result.getResultCode().equals("9000")) {
//                                mUrl = AppConfig.orderUrl;
//                                mAgentWeb.loadUrl(mUrl);
//                                needClearHistory = true;
//                            } else {
////                                finish();
//                            }
                        }
                    });
                }
            });

            /**
             * 判断是否成功拦截
             * 若成功拦截，则无需继续加载该URL；否则继续加载
             */
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
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> valueCallback) {
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }

        // For Android  >= 3.0
        public void openFileChooser(ValueCallback valueCallback, String acceptType) {
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }

        //For Android  >= 4.1
        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }

        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(android.webkit.WebView webView, ValueCallback<Uri[]> filePathCallback, android.webkit.WebChromeClient.FileChooserParams fileChooserParams) {
            uploadMessageAboveL = filePathCallback;
            openImageChooserActivity();
            return true;
        }
    };
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private final static int FILE_CHOOSER_RESULT_CODE = 10000;

    private void openImageChooserActivity() {
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ToastUtils.showTextToast(mContext, "请打开相册权限");
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            return;
        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == uploadMessage && null == uploadMessageAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        } else if (requestCode == 10103) {
            UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
            Log.e("wxpay -- webviewy", msg);
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
        public void gotoMainVCAction() {
            startActivity(new Intent(mContext, HomePageActivity.class));
            finish();
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
         * 关闭当前页面
         */
        @JavascriptInterface
        public void nativeBackBtnAction() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!mAgentWeb.back()) {
                        finish();
                    }
                }
            });
        }

        /**
         * 获取openid 推送使用
         */
        @JavascriptInterface
        public void getAppOpenid(final String openid) {
            JPushInterface.setAlias(getApplicationContext(), openid, new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    String s1 = s;
                }
            });
            JPushInterface.resumePush(getApplicationContext());
        }

        /**
         * 设置cookie
         */
        @JavascriptInterface
        public void saveWebCookie(final String cookie) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    PreferenceUtils.saveStringPreferences(mContext, AppContext.getInstance().COOKIE, cookie);
                    EventBus.getDefault().post(new CommonEnity<>("loginSuc"));
//                    startActivity(new Intent(mContext, HomePageActivity.class));
                    finish();
                }
            });
        }

        /**
         * 跳到商品详情页
         */
        @JavascriptInterface
        public void gotoGoodsDetailAction(String id) {
            startActivity(new Intent(mContext, GoodsDetailActivity.class).putExtra("id", id));
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
         * 登录
         */
        @JavascriptInterface
        public void NativeGotoLoginVC() {
            String token = PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, "");
            if (TextUtils.isEmpty(token)){
                startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestLogin));
            }

        }

        /**
         * 退出登陆
         */
        @JavascriptInterface
        public void NativeLogOut() {
            EventBus.getDefault().post(new CommonEnity<>("logout"));
            PreferenceUtils.saveStringPreferences(mContext, AppContext.getInstance().COOKIE, "");
            finish();
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
                .baseUrl("http://www.chanwu7.com")
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
        if (fileSize != 0 && fileSize > 1024 * 200) {
            isDel = true;
            ImageUtils.doCompressImage(path, myPath);
        } else {
            isDel = false;
            myPath = path;
        }
        imgFile = new File(myPath);
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl("http://www.chanwu7.com")
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    HashMap<String, String> clientInfo = new HashMap<>();
                    clientInfo.put("name", model.getNickname());
                    clientInfo.put("avatar", AppConfig.main + model.getAvatar());
                    Intent intent = new MQIntentBuilder(WebviewActivity.this)
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
                    new ShareAction(WebviewActivity.this)
                            .setPlatform(SHARE_MEDIA.QQ)//传入平台
                            .withMedia(umWeb)
                            .setCallback(shareListener)//回调监听器
                            .share();
                    shareDialog.dismiss();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new ShareAction(WebviewActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                            .withMedia(umWeb)
                            .setCallback(shareListener)//回调监听器
                            .share();
                    shareDialog.dismiss();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new ShareAction(WebviewActivity.this)
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
                        ToastUtils.showTextToast(WebviewActivity.this, "请安装微博客户端");
                        return;
                    }
                    new ShareAction(WebviewActivity.this)
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
//            webView.reload();
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
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.clearWebCache();
        EventBus.getDefault().unregister(this);
    }
}
