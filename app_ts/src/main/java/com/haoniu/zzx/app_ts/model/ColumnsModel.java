package com.haoniu.zzx.app_ts.model;

/**
 * Created by zzx on 2017/12/11 0011.
 */

public class ColumnsModel {

    /**
     * id : 23
     * uniacid : 3
     * navname : 澳洲
     * bgimage :
     * icon : http://images.tiaosui.com/2b2edf8c5e6a22bf843f0d0322b2dc55.jpg
     * url : Australia
     * displayorder : 0
     * status : 1
     */

    private String id;
    private String uniacid;
    private String navname;
    private String bgimage;
    private String icon;
    private String url;
    private String displayorder;
    private String status;
    private String shareUrl;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniacid() {
        return uniacid;
    }

    public void setUniacid(String uniacid) {
        this.uniacid = uniacid;
    }

    public String getNavname() {
        return navname;
    }

    public void setNavname(String navname) {
        this.navname = navname;
    }

    public String getBgimage() {
        return bgimage;
    }

    public void setBgimage(String bgimage) {
        this.bgimage = bgimage;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(String displayorder) {
        this.displayorder = displayorder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
