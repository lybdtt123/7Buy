package com.haoniu.zzx.app_ts.model;

/**
 * Created by zzx on 2017/9/28 0028.
 */

public class CategoryModel {

    /**
     * id : 1182
     * name : 美妆护肤
     */

    private String id;
    private String name;
    private boolean isCheck;

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

}
