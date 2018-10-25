package com.haoniu.zzx.app_ts.model;

import com.haoniu.zzx.app_ts.utils.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzx on 2017/12/15 0015.
 */

public class SpecificationsModel implements Serializable {

    /**
     * diyformhtml :
     */
    //  商品名称
    private GoodsBean goods;

    private boolean seckillinfo;
    private String diyformhtml;
    //  规格列表
    private List<SpecsBean> specs;
    //  各个规则对应的价格
    private List<OptionsBean> options;


    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public boolean isSeckillinfo() {
        return seckillinfo;
    }

    public void setSeckillinfo(boolean seckillinfo) {
        this.seckillinfo = seckillinfo;
    }

    public String getDiyformhtml() {
        return diyformhtml;
    }

    public void setDiyformhtml(String diyformhtml) {
        this.diyformhtml = diyformhtml;
    }

    public List<SpecsBean> getSpecs() {
        return specs;
    }

    public void setSpecs(List<SpecsBean> specs) {
        this.specs = specs;
    }

    public List<OptionsBean> getOptions() {
        return options;
    }

    public void setOptions(List<OptionsBean> options) {
        this.options = options;
    }

    public static class GoodsBean implements Serializable {
        /**
         * id : 102572
         * thumb : http://images.tiaosui.com/images/ad/12/ad12a17824f8c92ca8a99eb5f66aa4b8.jpg
         * title : adidas 阿迪达斯 女士时尚运动鞋
         * marketprice : 215.03
         * total : 4200
         * maxbuy : 0
         * minbuy : 0
         * unit :
         * hasoption : 1
         * showtotal : 0
         * diyformid : 0
         * diyformtype : 0
         * diyfields : null
         * isdiscount : 0
         * isdiscount_time : 1511608062
         * isdiscount_discounts : {"type":1,"default":{"option1178036":"","option1178037":"","option1178038":"","option1178039":"","option1178040":"","option1178041":"","option1178042":"","option1178043":"","option1178044":"","option1178045":"","option1178046":"","option1178047":"","option1178048":"","option1178049":"","option1178050":"","option1178051":"","option1178052":"","option1178053":"","option1178054":"","option1178055":"","option1178056":"","option1178057":"","option1178058":"","option1178059":"","option1178060":"","option1178061":"","option1178062":"","option1178063":"","option1178064":"","option1178065":"","option1178066":"","option1178067":"","option1178068":"","option1178069":"","option1178070":"","option1178071":"","option1178072":"","option1178073":"","option1178074":"","option1178075":"","option1178076":"","option1178077":""}}
         * needfollow : 0
         * followtip :
         * followurl :
         * type : 1
         * isverify : 1
         * maxprice : 783.89
         * minprice : 197.92
         * merchsale : 0
         * canAddCart : true
         */

        private String id;
        private String thumb;
        private String title;
        private String marketprice;
        private String total;
        private String maxbuy;
        private String minbuy;
        private String unit;
        private String hasoption;
        private String showtotal;
        private String diyformid;
        private String diyformtype;
        private Object diyfields;
        private String isdiscount;
        private String isdiscount_time;
        private String isdiscount_discounts;
        private String needfollow;
        private String followtip;
        private String followurl;
        private String type;
        private String isverify;
        private String maxprice;
        private String minprice;
        private String merchsale;
        private boolean canAddCart;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMarketprice() {
            if (!StringUtils.isEmpty(marketprice) && marketprice.contains(",")) {
                marketprice = marketprice.replaceAll(",", "");
            }
            return marketprice;
        }

        public void setMarketprice(String marketprice) {
            this.marketprice = marketprice;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getMaxbuy() {
            return maxbuy;
        }

        public void setMaxbuy(String maxbuy) {
            this.maxbuy = maxbuy;
        }

        public String getMinbuy() {
            return minbuy;
        }

        public void setMinbuy(String minbuy) {
            this.minbuy = minbuy;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getHasoption() {
            return hasoption;
        }

        public void setHasoption(String hasoption) {
            this.hasoption = hasoption;
        }

        public String getShowtotal() {
            return showtotal;
        }

        public void setShowtotal(String showtotal) {
            this.showtotal = showtotal;
        }

        public String getDiyformid() {
            return diyformid;
        }

        public void setDiyformid(String diyformid) {
            this.diyformid = diyformid;
        }

        public String getDiyformtype() {
            return diyformtype;
        }

        public void setDiyformtype(String diyformtype) {
            this.diyformtype = diyformtype;
        }

        public Object getDiyfields() {
            return diyfields;
        }

        public void setDiyfields(Object diyfields) {
            this.diyfields = diyfields;
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

        public String getIsdiscount_discounts() {
            return isdiscount_discounts;
        }

        public void setIsdiscount_discounts(String isdiscount_discounts) {
            this.isdiscount_discounts = isdiscount_discounts;
        }

        public String getNeedfollow() {
            return needfollow;
        }

        public void setNeedfollow(String needfollow) {
            this.needfollow = needfollow;
        }

        public String getFollowtip() {
            return followtip;
        }

        public void setFollowtip(String followtip) {
            this.followtip = followtip;
        }

        public String getFollowurl() {
            return followurl;
        }

        public void setFollowurl(String followurl) {
            this.followurl = followurl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIsverify() {
            return isverify;
        }

        public void setIsverify(String isverify) {
            this.isverify = isverify;
        }

        public String getMaxprice() {
            if (!StringUtils.isEmpty(maxprice) && maxprice.contains(",")) {
                maxprice = maxprice.replaceAll(",", "");
            }
            return maxprice;
        }

        public void setMaxprice(String maxprice) {
            this.maxprice = maxprice;
        }

        public String getMinprice() {
            if (!StringUtils.isEmpty(minprice) && minprice.contains(",")) {
                minprice = minprice.replaceAll(",", "");
            }
            return minprice;
        }

        public void setMinprice(String minprice) {
            this.minprice = minprice;
        }

        public String getMerchsale() {
            return merchsale;
        }

        public void setMerchsale(String merchsale) {
            this.merchsale = merchsale;
        }

        public boolean isCanAddCart() {
            return canAddCart;
        }

        public void setCanAddCart(boolean canAddCart) {
            this.canAddCart = canAddCart;
        }
    }

    public static class SpecsBean implements Serializable {
        /**
         * id : 118255
         * uniacid : 3
         * goodsid : 102572
         * title : 规格1
         * description :
         * displaytype : 0
         * content : a:13:{i:0;s:6:"658468";i:1;s:6:"658469";i:2;s:6:"658470";i:3;s:6:"658471";i:4;s:6:"658472";i:5;s:6:"658473";i:6;s:6:"658474";i:7;s:6:"658475";i:8;s:6:"658476";i:9;s:6:"658477";i:10;s:6:"658478";i:11;s:6:"658479";i:12;s:6:"658480";}
         * displayorder : 0
         * propId :
         * items : [{"id":"658468","uniacid":"3","specid":"118255","title":"38.5码","thumb":"","show":"1","displayorder":"0","valueId":"","virtual":"0"},{"id":"658469","uniacid":"3","specid":"118255","title":"36.5码","thumb":"","show":"1","displayorder":"0","valueId":"","virtual":"0"},{"id":"658470","uniacid":"3","specid":"118255","title":"41码","thumb":"","show":"1","displayorder":"0","valueId":"","virtual":"0"},{"id":"658471","uniacid":"3","specid":"118255","title":"42码","thumb":"","show":"1","displayorder":"0","valueId":"","virtual":"0"},{"id":"658472","uniacid":"3","specid":"118255","title":"35码","thumb":"","show":"1","displayorder":"0","valueId":"","virtual":"0"},{"id":"658473","uniacid":"3","specid":"118255","title":"40码","thumb":"","show":"1","displayorder":"0","valueId":"","virtual":"0"},{"id":"658474","uniacid":"3","specid":"118255","title":"39码US","thumb":"","show":"1","displayorder":"0","valueId":"","virtual":"0"},{"id":"658475","uniacid":"3","specid":"118255","title":"35.5码","thumb":"","show":"1","displayorder":"0","valueId":"","virtual":"0"},{"id":"658476","uniacid":"3","specid":"118255","title":"37码","thumb":"","show":"1","displayorder":"0","valueId":"","virtual":"0"},{"id":"658477","uniacid":"3","specid":"118255","title":"38码","thumb":"","show":"1","displayorder":"0","valueId":"","virtual":"0"},{"id":"658478","uniacid":"3","specid":"118255","title":"36码","thumb":"","show":"1","displayorder":"0","valueId":"","virtual":"0"},{"id":"658479","uniacid":"3","specid":"118255","title":"39.5码","thumb":"","show":"1","displayorder":"0","valueId":"","virtual":"0"},{"id":"658480","uniacid":"3","specid":"118255","title":"41.5码","thumb":"","show":"1","displayorder":"0","valueId":"","virtual":"0"}]
         */

        private String id;
        private String uniacid;
        private String goodsid;
        private String title;
        private String description;
        private String displaytype;
        private String content;
        private String displayorder;
        private String propId;
        private List<ItemsBean> items;

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

        public String getGoodsid() {
            return goodsid;
        }

        public void setGoodsid(String goodsid) {
            this.goodsid = goodsid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDisplaytype() {
            return displaytype;
        }

        public void setDisplaytype(String displaytype) {
            this.displaytype = displaytype;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDisplayorder() {
            return displayorder;
        }

        public void setDisplayorder(String displayorder) {
            this.displayorder = displayorder;
        }

        public String getPropId() {
            return propId;
        }

        public void setPropId(String propId) {
            this.propId = propId;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean implements Serializable {
            /**
             * id : 658468
             * uniacid : 3
             * specid : 118255
             * title : 38.5码
             * thumb :
             * show : 1
             * displayorder : 0
             * valueId :
             * virtual : 0
             */

            private String id;
            private String uniacid;
            private String specid;
            private String title;
            private String thumb;
            private String show;
            private String displayorder;
            private String valueId;
            private String virtual;

            private boolean isSelect;//是否选中

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
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

            public String getSpecid() {
                return specid;
            }

            public void setSpecid(String specid) {
                this.specid = specid;
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

            public String getShow() {
                return show;
            }

            public void setShow(String show) {
                this.show = show;
            }

            public String getDisplayorder() {
                return displayorder;
            }

            public void setDisplayorder(String displayorder) {
                this.displayorder = displayorder;
            }

            public String getValueId() {
                return valueId;
            }

            public void setValueId(String valueId) {
                this.valueId = valueId;
            }

            public String getVirtual() {
                return virtual;
            }

            public void setVirtual(String virtual) {
                this.virtual = virtual;
            }
        }
    }

    public static class OptionsBean implements Serializable {
        /**
         * id : 1178036
         * uniacid : 3
         * goodsid : 102572
         * title : 38.5码+Silvmt/Silvmt/Ftwwht
         * thumb :
         * productprice : 0.00
         * Chinaproductprice : 0.00
         * marketprice : 765.32
         * Chinamarketprice : 98.03
         * costprice : 0.00
         * stock : 100
         * weight : 1200.00
         * displayorder : 0
         * specs : 658468_658481
         * skuId :
         * goodssn : B07177G7W8
         * productsn :
         * virtual : 0
         * exchange_stock : 0
         * exchange_postage : 0.00
         */

        private String id;
        private String uniacid;
        private String goodsid;
        private String title;
        private String thumb;
        private String productprice;
        private String Chinaproductprice;
        private String marketprice;
        private String Chinamarketprice;
        private String costprice;
        private String stock;
        private String weight;
        private String displayorder;
        private String specs;
        private String skuId;
        private String goodssn;
        private String productsn;
        private String virtual;
        private String exchange_stock;
        private String exchange_postage;

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

        public String getGoodsid() {
            return goodsid;
        }

        public void setGoodsid(String goodsid) {
            this.goodsid = goodsid;
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

        public String getProductprice() {
            return productprice;
        }

        public void setProductprice(String productprice) {
            this.productprice = productprice;
        }

        public String getChinaproductprice() {
            return Chinaproductprice;
        }

        public void setChinaproductprice(String Chinaproductprice) {
            this.Chinaproductprice = Chinaproductprice;
        }

        public String getMarketprice() {
            if (!StringUtils.isEmpty(marketprice) && marketprice.contains(",")) {
                marketprice = marketprice.replaceAll(",", "");
            }
            return marketprice;
        }

        public void setMarketprice(String marketprice) {
            this.marketprice = marketprice;
        }

        public String getChinamarketprice() {
            return Chinamarketprice;
        }

        public void setChinamarketprice(String Chinamarketprice) {
            this.Chinamarketprice = Chinamarketprice;
        }

        public String getCostprice() {
            return costprice;
        }

        public void setCostprice(String costprice) {
            this.costprice = costprice;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getDisplayorder() {
            return displayorder;
        }

        public void setDisplayorder(String displayorder) {
            this.displayorder = displayorder;
        }

        public String getSpecs() {
            return specs;
        }

        public void setSpecs(String specs) {
            this.specs = specs;
        }

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public String getGoodssn() {
            return goodssn;
        }

        public void setGoodssn(String goodssn) {
            this.goodssn = goodssn;
        }

        public String getProductsn() {
            return productsn;
        }

        public void setProductsn(String productsn) {
            this.productsn = productsn;
        }

        public String getVirtual() {
            return virtual;
        }

        public void setVirtual(String virtual) {
            this.virtual = virtual;
        }

        public String getExchange_stock() {
            return exchange_stock;
        }

        public void setExchange_stock(String exchange_stock) {
            this.exchange_stock = exchange_stock;
        }

        public String getExchange_postage() {
            return exchange_postage;
        }

        public void setExchange_postage(String exchange_postage) {
            this.exchange_postage = exchange_postage;
        }
    }
}
