package com.haoniu.zzx.app_ts.model;

import java.io.Serializable;

/**
 * Created by zzx on 2017/11/3 0003.
 */

public class DianShangNavModel implements Serializable{
    /**
     * id : 4470
     * logo : http://images.tiaosui.com/3d4bba15e0a78a1d273bd952a4d5c55c.jpg
     * merchname : 美国amazon亚马逊
     * address : https://www.amazon.com/
     */

    private String id;
    private String logo;
    private String merchname;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getMerchname() {
        return merchname;
    }

    public void setMerchname(String merchname) {
        this.merchname = merchname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * id : 4470
     * logo : http://images.tiaosui.com/3d4bba15e0a78a1d273bd952a4d5c55c.jpg
     * eachUrl : http://www.chanwu7.com/getData/index.php?m=shop&c=brand&a=seaMail&shopid=4470
     */

}
