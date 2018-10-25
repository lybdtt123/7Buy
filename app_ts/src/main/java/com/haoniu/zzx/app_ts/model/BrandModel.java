package com.haoniu.zzx.app_ts.model;

import android.support.annotation.NonNull;

import com.haoniu.zzx.app_ts.IndexBar.bean.BaseIndexPinyinBean;

/**
 * Created by zzx on 2017/8/14 0014.
 */

public class BrandModel extends BaseIndexPinyinBean implements Comparable<BrandModel>{

    /**
     * id :
     * logo :
     * merchname : *|%A
     */

    private String id;
    private String logo;
    private String merchname;

    @Override
    public String getTarget() {
        return merchname;
    }

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
        return merchname == null ? "" : merchname;
    }

    public void setMerchname(String merchname) {
        this.merchname = merchname;
    }

    @Override
    public int compareTo(@NonNull BrandModel brandModel) {
        return 0;
    }

}
