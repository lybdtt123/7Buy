package com.haoniu.zzx.app_ts.model;

import java.util.List;

/**
 * Created by zzx on 2017/9/27 0027.
 */

public class CountryBean {
    /**
     * img : http://103.22.189.2/attachment/images/home/NewYork.png
     * goods : [{"id":"5068","title":"单肩包 Guess（盖尔斯） GUESS 盖尔斯 Darin迷你城市包","thumb":"http://images.tiaosui.com/images/79/ea/79eaae3c6b5fe6f809d723422dec8b99.jpg","total":"300","minprice":"533.94","country":"美国"},{"id":"1519","title":"打火机 Zippo  Pole Dancer Pocket Lighter","thumb":"http://images.tiaosui.com/images/04/8d/048dd6e55ad4c53755e6d1b06fb17d1a.jpg","total":"200","minprice":"133.20","country":"美国"},{"id":"36395","title":"拖鞋&木屐（木底鞋） Naturalizer（娜然） Naturalizer Women&#039;s Wink Slide Sandal","thumb":"http://images.tiaosui.com/images/f6/21/f6211d183b91dda4c4d9bcb13b5cbc86.jpg","total":"700","minprice":"541.65","country":"美国"},{"id":"26397","title":"帽子 NIKE（耐克） NIKE Unisex Classic99 Washed Cap","thumb":"http://images.tiaosui.com/images/a7/2d/a72d04d231ca8af7b0d9e3b58d1fa67e.jpg","total":"400","minprice":"241.15","country":"美国"},{"id":"23990","title":"口红 Estee Lauder（雅诗兰黛） Estee Lauder 雅诗兰黛 纯色羡慕造型口红, 0.12 Ounce","thumb":"http://images.tiaosui.com/images/5a/45/5a45dd9c4d88884226ab605704b5b9d6.jpg","total":"400","minprice":"183.36","country":"美国"},{"id":"18657","title":"太阳镜 Prada Linea Rossa 普拉达  Men&#039;s 0PS 56RS","thumb":"http://images.tiaosui.com/images/e6/26/e626135677922ae8341152273a0dd8b6.jpg","total":"500","minprice":"1165.75","country":"美国"},{"id":"11206","title":"斜挎包 Coach（蔻驰） Coach Outline Signature Celeste Hobo Shoulder Crossbody Bag Purse Handbag","thumb":"http://images.tiaosui.com/images/72/c4/72c4304b355887bef16564b6effb92d7.jpg","total":"700","minprice":"949.93","country":"美国"},{"id":"10282","title":"手表 Daniel Wellington（丹尼尔·惠灵顿） Daniel Wellington - Dapper Bristol Rosegold 38mm Unisex Leather watch","thumb":"http://images.tiaosui.com/images/e0/21/e02133624bdaa0f6e04dcf590e2e9971.jpg","total":"100","minprice":"780.42","country":"美国"},{"id":"53994","title":"跑鞋 adidas NEO adidas Men&#039;s Superstar","thumb":"http://images.tiaosui.com/images/3c/11/3c115b23995f5b1944eedab8b8f822fe.jpg","total":"2000","minprice":"841.75","country":"美国"}]
     */

    private String img;
    private String countryid;
    private String eachUrl;

    public String getCountryid() {
        return countryid;
    }

    public void setCountryid(String countryid) {
        this.countryid = countryid;
    }

    public String getEachUrl() {
        return eachUrl;
    }

    public void setEachUrl(String eachUrl) {
        this.eachUrl = eachUrl;
    }

    private List<NormalModel> goods;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<NormalModel> getGoods() {
        return goods;
    }

    public void setGoods(List<NormalModel> goods) {
        this.goods = goods;
    }

}
