package com.haoniu.zzx.app_ts.model;

import java.io.Serializable;

/**
 * Created by zzx on 2017/12/14 0014.
 */

public class RecommendModel implements Serializable{

    /**
     * id : 74408
     * title : 面霜ARGITAL(雅琪朵)面霜，50毫升
     * marketprice : 353.57
     * inlandprice : 883.91
     * thumb : http://images.tiaosui.com/images/7a/03/7a03a4909917516531747aeeec1f43f9.jpg
     */

    private String id;
    private String title;
    private String marketprice;
    private String inlandprice;
    private String thumb;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMarketprice() {
        return marketprice;
    }

    public void setMarketprice(String marketprice) {
        this.marketprice = marketprice;
    }

    public String getInlandprice() {
        return inlandprice;
    }

    public void setInlandprice(String inlandprice) {
        this.inlandprice = inlandprice;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
