package com.haoniu.zzx.app_ts.model;

import java.io.Serializable;

/**
 * Created by zzx on 2017/8/16 0016.
 */

public class ClassModel implements Serializable {

    /**
     * cid : 30
     * groupid : 7
     * catename : 食品
     */

    private int cid;
    private int groupid;
    private String catename;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }
}
