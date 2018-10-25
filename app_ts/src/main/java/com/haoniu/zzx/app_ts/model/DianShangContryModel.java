package com.haoniu.zzx.app_ts.model;

/**
 * Created by zzx on 2017/11/3 0003.
 */

public class DianShangContryModel {

    /**
     * id : 14
     * navname : 美国
     */

    private String id;
    private String navname;
    private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNavname() {
        return navname;
    }

    public void setNavname(String navname) {
        this.navname = navname;
    }
}
