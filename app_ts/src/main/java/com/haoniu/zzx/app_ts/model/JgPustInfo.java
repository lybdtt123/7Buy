package com.haoniu.zzx.app_ts.model;

/**
 * Created by zzx on 2018/07/07/下午 5:46
 */

public class JgPustInfo {


    /**
     * activityids : 51
     * sendtype : 2
     * goodsids : 0
     * goodsname : false
     * activityname : 99元全球扫货
     */

    private int activityids;
    private int sendtype;
    private int goodsids;
    private String goodsname;
    private String activityname;

    public void setActivityids(int activityids) {
        this.activityids = activityids;
    }

    public void setSendtype(int sendtype) {
        this.sendtype = sendtype;
    }

    public void setGoodsids(int goodsids) {
        this.goodsids = goodsids;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public void setActivityname(String activityname) {
        this.activityname = activityname;
    }

    public int getActivityids() {
        return activityids;
    }

    public int getSendtype() {
        return sendtype;
    }

    public int getGoodsids() {
        return goodsids;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public String getActivityname() {
        return activityname;
    }
}
