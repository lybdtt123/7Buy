package com.haoniu.zzx.app_ts.model;

/**
 * Created by zzx on 2017/10/24 0024.
 */

public class GoodsListModel {

    /**
     * id : 101846
     * title : Sekkisei（雪肌精） ホワイト BBクリーム モイスト 02 30g
     * thumb : http://images.tiaosui.com/images/5d/56/5d56f6ee91c61a4780cc6036a7dbc81c.jpg
     * marketprice : 162.93
     * productprice : 0.00
     * minprice : 162.93
     * maxprice : 162.93
     * isdiscount : 0
     * isdiscount_time : 1508496571
     * isdiscount_discounts : null
     * sales : 0
     * total : 100
     * description : 内容量：30g<br>カラー：#02 OCHRE
     * bargain : 0
     * type : 1
     * layer
     * saleno 下单立减
     *issaleout 1 已售罄
     *
     */

    private String id;
    private String title;
    private String thumb;
    private String marketprice;
    private String productprice;
    private String minprice;
    private String maxprice;
    private String isdiscount;
    private String isdiscount_time;
    private Object isdiscount_discounts;
    private String sales;
    private String total;
    private String description;
    private String bargain;
    private String type;
    private String layer;
    private String saleno;
    private String issaleout;


    public String getSaleno() {
        return saleno;
    }

    public void setSaleno(String saleno) {
        this.saleno = saleno;
    }

    public String getIssaleout() {
        return issaleout;
    }

    public void setIssaleout(String issaleout) {
        this.issaleout = issaleout;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getMarketprice() {
        return marketprice;
    }

    public void setMarketprice(String marketprice) {
        this.marketprice = marketprice;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getMinprice() {
        return minprice;
    }

    public void setMinprice(String minprice) {
        this.minprice = minprice;
    }

    public String getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(String maxprice) {
        this.maxprice = maxprice;
    }

    public String getIsdiscount() {
        return isdiscount;
    }

    public void setIsdiscount(String isdiscount) {
        this.isdiscount = isdiscount;
    }

    public String getIsdiscount_time() {
        return isdiscount_time;
    }

    public void setIsdiscount_time(String isdiscount_time) {
        this.isdiscount_time = isdiscount_time;
    }

    public Object getIsdiscount_discounts() {
        return isdiscount_discounts;
    }

    public void setIsdiscount_discounts(Object isdiscount_discounts) {
        this.isdiscount_discounts = isdiscount_discounts;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBargain() {
        return bargain;
    }

    public void setBargain(String bargain) {
        this.bargain = bargain;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
