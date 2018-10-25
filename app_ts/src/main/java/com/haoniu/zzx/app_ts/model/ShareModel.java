package com.haoniu.zzx.app_ts.model;

/**
 * Created by zzx on 2017/7/24 0024.
 */

public class ShareModel {

    /**
     * img : http://zs.hfrjkf.cn/attachment/logo.png
     * title : 众生商城
     * content : 众生商城是一款网购类APP，为满足用户的生活消费及线上购物需求的软件。商城中包含各类商品供用户选择。物美价廉，满足您所有的购物需求。
     * link : http://zs.hfrjkf.cn/openApp.php
     */

    private String title;
    private String thumbs;
    private String name;
    private String goodsurl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbs() {
        return thumbs;
    }

    public void setThumbs(String thumbs) {
        this.thumbs = thumbs;
    }


    public String getGoodsurl() {
        return goodsurl;
    }

    public void setGoodsurl(String goodsurl) {
        this.goodsurl = goodsurl;
    }
}
