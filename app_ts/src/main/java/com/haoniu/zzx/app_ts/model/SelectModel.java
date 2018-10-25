package com.haoniu.zzx.app_ts.model;

/**
 * Created by zzx on 2017/10/24 0024.
 */

public class SelectModel {
    private String msg;
    private boolean check = false;

    public SelectModel(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
