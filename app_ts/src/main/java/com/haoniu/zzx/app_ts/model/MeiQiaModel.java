package com.haoniu.zzx.app_ts.model;

import java.io.Serializable;

/**
 * Created by zzx on 2017/8/31 0031.
 */

public class MeiQiaModel implements Serializable{

    /**
     * nickname : 186xxxx0310
     * mobile : 18655050310
     * avatar : /getData/Uploads/headerimg/1503564213865857.png
     * title : Greylin Women's Dezza Grommet Dress
     * thumb : http://www.chanwu7.com/attachment/images/3/d6/24/d624d6eb8ea710cd57c63fff408b81d5.jpg
     * link : http://www.chanwu7.com/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=goods.detail&id=5717
     * marketprice : ￥316.06~1048.45
     */

    private String nickname;
    private String mobile;
    private String avatar;
    private String title;
    private String thumb;
    private String link;
    private String marketprice;
    private int isFavorite;

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMarketprice() {
        return marketprice;
    }

    public void setMarketprice(String marketprice) {
        this.marketprice = marketprice;
    }
}
