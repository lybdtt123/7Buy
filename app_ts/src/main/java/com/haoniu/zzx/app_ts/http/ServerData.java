package com.haoniu.zzx.app_ts.http;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/26.
 */
public class ServerData implements Serializable {


    /**
     * code : 1
     * message : 上传成功
     * data : {"address":"./Uploads/headerimg/","pic":"149397916985403.png"}
     */

    private int code;
    private String messge;
    private String data;

    @Override
    public String toString() {
        return "ServerData{" +
                "code=" + code +
                ", message=" + messge +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int status) {
        this.code = status;
    }

    public String getMessge() {
        return messge;
    }

    public void setMessge(String messge) {
        this.messge = messge;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
