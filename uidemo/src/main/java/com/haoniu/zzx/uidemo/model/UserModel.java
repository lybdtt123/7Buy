package com.haoniu.zzx.uidemo.model;

import android.view.View;

import com.haoniu.zzx.uidemo.utils.ToastUtils;

/**
 * Created by zzx on 2017/10/30 0030.
 */

public class UserModel {
    private String trueName, nickName, email;
    private int level;
    private boolean vip;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void clickName(View view) {
        ToastUtils.showTextToast(view.getContext(), trueName);
    }
    public void clickName(View view,String msg) {
        ToastUtils.showTextToast(view.getContext(), msg);
    }

    public boolean longClickName(View view) {
        ToastUtils.showTextToast(view.getContext(), nickName);
        return true;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }
}
