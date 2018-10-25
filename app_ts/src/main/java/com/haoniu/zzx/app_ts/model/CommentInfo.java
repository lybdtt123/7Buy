package com.haoniu.zzx.app_ts.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzx on 2018/04/18/下午 12:02
 */

public class CommentInfo {

    private String total;
    private List<CommentListInfo> list = new ArrayList<>();

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<CommentListInfo> getList() {
        return list;
    }

    public void setList(List<CommentListInfo> list) {
        this.list = list;
    }
}
