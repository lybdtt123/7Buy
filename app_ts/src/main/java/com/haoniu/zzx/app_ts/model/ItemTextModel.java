package com.haoniu.zzx.app_ts.model;

/**
 * Created by zzx on 2017/10/23 0023.
 */

public class ItemTextModel {

    private boolean isCheck;
    /**
     * id : 4174
     * name : CC霜
     * level : 4
     * eachUrl : http://www.chanwu7.com/getData/index.php?m=shop&c=homeport&a=getshopcategood&fcate=4174
     */

    private String id;
    private String name;
    private String level;
    private String eachUrl;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEachUrl() {
        return eachUrl;
    }

    public void setEachUrl(String eachUrl) {
        this.eachUrl = eachUrl;
    }
}
