package com.haoniu.zzx.uidemo.model;

/**
 * Created by zzx on 2017/10/19 0019.
 */

public class MainClickModel {
    private String msg;
    private Class<?> aClass;

    public MainClickModel(String msg, Class<?> aClass) {
        this.msg = msg;
        this.aClass = aClass;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Class<?> getaClass() {
        return aClass;
    }

    public void setaClass(Class<?> aClass) {
        this.aClass = aClass;
    }
}
