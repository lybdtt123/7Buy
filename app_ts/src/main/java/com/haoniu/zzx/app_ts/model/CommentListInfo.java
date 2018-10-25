package com.haoniu.zzx.app_ts.model;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by zzx on 2018/04/19/上午 9:19
 */

public class CommentListInfo {
    private String id;
    private String nickname;
    private String avatar;
    private String content;
    private List<String> images;
    private String createtime;
    private String deleted;
    private String checked;
    private String ordersn;
    private Long star;
    private String backlist;

    public String getBacklist() {
        return backlist;
    }

    public void setBacklist(String backlist) {
        this.backlist = backlist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public Long getStar() {
        return star;
    }

    public void setStar(Long star) {
        this.star = star;
    }
}
