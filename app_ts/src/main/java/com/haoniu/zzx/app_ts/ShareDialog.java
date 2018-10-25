package com.haoniu.zzx.app_ts;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by zzx on 2017/8/18 0018.
 */

public class ShareDialog extends Dialog implements OnClickListener {
    private Context mContext;

    public ShareDialog(@NonNull Context context) {
        super(context, R.style.Theme_Light_FullScreenDialogAct);
        setContentView(R.layout.layout_dialog_share);
        Window window = this.getWindow();
        window.setWindowAnimations(R.style.myDialogAnim);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(lp);
        mContext = context;
        initView();
    }

    private void initView() {
        findViewById(R.id.share_qq).setOnClickListener(this);
        findViewById(R.id.share_wechat).setOnClickListener(this);
        findViewById(R.id.share_wechat_circle).setOnClickListener(this);
        findViewById(R.id.share_weibo).setOnClickListener(this);
        findViewById(R.id.share_cancel).setOnClickListener(this);
    }

    private OnClickListener qq, wechat, wechat_circle, sina;

    public void setClick(OnClickListener qq, OnClickListener wechat, OnClickListener wechat_circle, OnClickListener sina) {
        this.qq = qq;
        this.wechat = wechat;
        this.wechat_circle = wechat_circle;
        this.sina = sina;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //QQ分享
            case R.id.share_qq:
                if (qq != null) {
                    qq.onClick(this, 0);
                }
                break;
            //微信好友分享
            case R.id.share_wechat:
                if (wechat != null) {
                    wechat.onClick(this, 0);
                }
                break;
            //微信朋友圈分享
            case R.id.share_wechat_circle:
                if (wechat_circle != null) {
                    wechat_circle.onClick(this, 0);
                }
                break;
            //微博分享
            case R.id.share_weibo:
                if (sina != null) {
                    sina.onClick(this, 0);
                }
                break;
            //取消分享
            case R.id.share_cancel:
                dismiss();
                break;
        }

    }
}
