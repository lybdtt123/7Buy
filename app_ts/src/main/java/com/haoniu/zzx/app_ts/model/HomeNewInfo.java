package com.haoniu.zzx.app_ts.model;

import java.util.List;

/**
 * Created by zzx on 2018/07/03/上午 9:59
 */

public class HomeNewInfo {

    private List<OneModeBeam> OneModeBeam;
    private List<ActivitycategorymodelBean> activitycategorymodel;
    private ActivityglobalmodelBean activityglobalmodel;

    public ActivityglobalmodelBean getActivityglobalmodel() {
        return activityglobalmodel;
    }

    public void setActivityglobalmodel(ActivityglobalmodelBean activityglobalmodel) {
        this.activityglobalmodel = activityglobalmodel;
    }

    public static class ActivityglobalmodelBean {
        /**
         * activitylist : [{"bannername":"99元全球扫货","categoryid":"0","countryid":"0","desc":"99元全球扫货","displayorder":"0","enabled":"1","id":"51","link":"","position":"1","shopid":"0","thumb":"http://images.tiaosui.com/427197164153fc076d66f6f14f3c8dce.jpg","uniacid":"3"},{"bannername":"魅力彩妆系列","categoryid":"0","countryid":"0","desc":"魅力彩妆系列","displayorder":"0","enabled":"1","id":"55","link":"","position":"3","shopid":"0","thumb":"http://images.tiaosui.com/7005f97879b8a8cb0e776dc6367c0fca.jpg","uniacid":"3"},{"bannername":"大牌护肤系列","categoryid":"0","countryid":"0","desc":"大牌护肤系列","displayorder":"0","enabled":"1","id":"56","link":"","position":"2","shopid":"0","thumb":"http://images.tiaosui.com/5a1c8343eedca7bbfa18073e306d58bb.jpg","uniacid":"3"},{"bannername":"每日必抢","categoryid":"0","countryid":"0","desc":"每日必抢","displayorder":"0","enabled":"1","id":"53","link":"","position":"1","shopid":"0","thumb":"http://images.tiaosui.com/72d09e1450154bf470a6c03a15b47d9e.jpg","uniacid":"3"},{"bannername":"夏季驱蚊","categoryid":"0","countryid":"0","desc":"夏季驱蚊","displayorder":"0","enabled":"1","id":"54","link":"","position":"6","shopid":"0","thumb":"http://images.tiaosui.com/c40cb53ba7c930ee5deacecd9d31db3a.jpg","uniacid":"3"},{"bannername":"国际轻奢系列","categoryid":"0","countryid":"0","desc":"国际轻奢系列","displayorder":"0","enabled":"1","id":"52","link":"","position":"4","shopid":"0","thumb":"http://images.tiaosui.com/f0238655fb3f29366b32a2a445a893e5.jpg","uniacid":"3"},{"bannername":"轻奢鞋靴","categoryid":"6","categoryname":"轻奢鞋靴","countryid":"0","desc":"轻奢鞋靴","displayorder":"0","enabled":"1","id":"188","link":"","position":"0","shopid":"0","thumb":"http://images.tiaosui.com/ff15581c6d0882ae87a213027a2ab84a.jpg","uniacid":"3"},{"bannername":"轻奢鞋靴","categoryid":"7","categoryname":"轻奢鞋靴","countryid":"0","desc":"轻奢鞋靴","displayorder":"0","enabled":"1","id":"199","link":"","position":"0","shopid":"0","thumb":"http://images.tiaosui.com/ff15581c6d0882ae87a213027a2ab84a.jpg","uniacid":"3"},{"bannername":"轻奢鞋靴","categoryid":"4","categoryname":"轻奢鞋靴","countryid":"0","desc":"轻奢鞋靴","displayorder":"0","enabled":"1","id":"157","link":"","position":"0","shopid":"0","thumb":"http://images.tiaosui.com/ff15581c6d0882ae87a213027a2ab84a.jpg","uniacid":"3"}]
         * countryactivity : [{"bannername":"轻奢鞋靴","categoryid":"1","categoryname":"轻奢鞋靴","countryid":"48","desc":"轻奢鞋靴","displayorder":"0","enabled":"1","id":"59","link":"","position":"0","shopid":"0","thumb":"http://images.tiaosui.com/ff15581c6d0882ae87a213027a2ab84a.jpg","uniacid":"3"},{"bannername":"轻奢鞋靴","categoryid":"7","categoryname":"轻奢鞋靴","countryid":"48","desc":"轻奢鞋靴","displayorder":"0","enabled":"1","id":"113","link":"","position":"0","shopid":"0","thumb":"http://images.tiaosui.com/ff15581c6d0882ae87a213027a2ab84a.jpg","uniacid":"3"},{"bannername":"轻奢鞋靴","categoryid":"8","categoryname":"轻奢鞋靴","countryid":"48","desc":"轻奢鞋靴","displayorder":"0","enabled":"1","id":"124","link":"","position":"0","shopid":"0","thumb":"http://images.tiaosui.com/ff15581c6d0882ae87a213027a2ab84a.jpg","uniacid":"3"},{"bannername":"轻奢鞋靴","categoryid":"4","categoryname":"轻奢鞋靴","countryid":"48","desc":"轻奢鞋靴","displayorder":"0","enabled":"1","id":"82","link":"","position":"0","shopid":"0","thumb":"http://images.tiaosui.com/ff15581c6d0882ae87a213027a2ab84a.jpg","uniacid":"3"},{"bannername":"轻奢鞋靴","categoryid":"3","categoryname":"轻奢鞋靴","countryid":"48","desc":"轻奢鞋靴","displayorder":"0","enabled":"1","id":"91","link":"","position":"0","shopid":"0","thumb":"http://images.tiaosui.com/ff15581c6d0882ae87a213027a2ab84a.jpg","uniacid":"3"},{"bannername":"轻奢鞋靴","categoryid":"5","categoryname":"轻奢鞋靴","countryid":"48","desc":"轻奢鞋靴","displayorder":"0","enabled":"1","id":"68","link":"","position":"0","shopid":"0","thumb":"http://images.tiaosui.com/ff15581c6d0882ae87a213027a2ab84a.jpg","uniacid":"3"},{"bannername":"轻奢鞋靴","categoryid":"2","categoryname":"轻奢鞋靴","countryid":"48","desc":"轻奢鞋靴","displayorder":"0","enabled":"1","id":"99","link":"","position":"0","shopid":"0","thumb":"http://images.tiaosui.com/ff15581c6d0882ae87a213027a2ab84a.jpg","uniacid":"3"},{"bannername":"轻奢鞋靴","categoryid":"4","categoryname":"轻奢鞋靴","countryid":"48","desc":"轻奢鞋靴","displayorder":"0","enabled":"1","id":"77","link":"","position":"0","shopid":"0","thumb":"http://images.tiaosui.com/ff15581c6d0882ae87a213027a2ab84a.jpg","uniacid":"3"},{"bannername":"轻奢鞋靴","categoryid":"6","categoryname":"轻奢鞋靴","countryid":"48","desc":"轻奢鞋靴","displayorder":"0","enabled":"1","id":"108","link":"","position":"0","shopid":"0","thumb":"http://images.tiaosui.com/ff15581c6d0882ae87a213027a2ab84a.jpg","uniacid":"3"}]
         */

        private String categoryname;
        private List<ActivityglobalmodelBean.ActivitylistBean> activitylist;
        private List<ActivityglobalmodelBean.CountryactivityBean> countryactivity;

        public String getCategoryname() {
            return categoryname;
        }

        public void setCategoryname(String categoryname) {
            this.categoryname = categoryname;
        }

        public void setActivitylist(List<ActivityglobalmodelBean.ActivitylistBean> activitylist) {
            this.activitylist = activitylist;
        }

        public void setCountryactivity(List<ActivityglobalmodelBean.CountryactivityBean> countryactivity) {
            this.countryactivity = countryactivity;
        }

        public List<ActivityglobalmodelBean.ActivitylistBean> getActivitylist() {
            return activitylist;
        }

        public List<ActivityglobalmodelBean.CountryactivityBean> getCountryactivity() {
            return countryactivity;
        }

        public static class ActivitylistBean {
            /**
             * bannername : 99元全球扫货
             * categoryid : 0
             * countryid : 0
             * desc : 99元全球扫货
             * displayorder : 0
             * enabled : 1
             * id : 51
             * link :
             * position : 1
             * shopid : 0
             * thumb : http://images.tiaosui.com/427197164153fc076d66f6f14f3c8dce.jpg
             * uniacid : 3
             */

            private String bannername;
            private String categoryid;
            private String countryid;
            private String desc;
            private String displayorder;
            private String enabled;
            private String id;
            private String link;
            private String position;
            private String shopid;
            private String thumb;
            private String uniacid;

            public void setBannername(String bannername) {
                this.bannername = bannername;
            }

            public void setCategoryid(String categoryid) {
                this.categoryid = categoryid;
            }

            public void setCountryid(String countryid) {
                this.countryid = countryid;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public void setDisplayorder(String displayorder) {
                this.displayorder = displayorder;
            }

            public void setEnabled(String enabled) {
                this.enabled = enabled;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public void setUniacid(String uniacid) {
                this.uniacid = uniacid;
            }

            public String getBannername() {
                return bannername;
            }

            public String getCategoryid() {
                return categoryid;
            }

            public String getCountryid() {
                return countryid;
            }

            public String getDesc() {
                return desc;
            }

            public String getDisplayorder() {
                return displayorder;
            }

            public String getEnabled() {
                return enabled;
            }

            public String getId() {
                return id;
            }

            public String getLink() {
                return link;
            }

            public String getPosition() {
                return position;
            }

            public String getShopid() {
                return shopid;
            }

            public String getThumb() {
                return thumb;
            }

            public String getUniacid() {
                return uniacid;
            }
        }

        public static class CountryactivityBean {
            /**
             * bannername : 轻奢鞋靴
             * categoryid : 1
             * categoryname : 轻奢鞋靴
             * countryid : 48
             * desc : 轻奢鞋靴
             * displayorder : 0
             * enabled : 1
             * id : 59
             * link :
             * position : 0
             * shopid : 0
             * thumb : http://images.tiaosui.com/ff15581c6d0882ae87a213027a2ab84a.jpg
             * uniacid : 3
             */

            private String bannername;
            private String categoryid;
            private String categoryname;
            private String countryid;
            private String desc;
            private String displayorder;
            private String enabled;
            private String id;
            private String link;
            private String position;
            private String shopid;
            private String thumb;
            private String uniacid;

            public void setBannername(String bannername) {
                this.bannername = bannername;
            }

            public void setCategoryid(String categoryid) {
                this.categoryid = categoryid;
            }

            public void setCategoryname(String categoryname) {
                this.categoryname = categoryname;
            }

            public void setCountryid(String countryid) {
                this.countryid = countryid;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public void setDisplayorder(String displayorder) {
                this.displayorder = displayorder;
            }

            public void setEnabled(String enabled) {
                this.enabled = enabled;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public void setUniacid(String uniacid) {
                this.uniacid = uniacid;
            }

            public String getBannername() {
                return bannername;
            }

            public String getCategoryid() {
                return categoryid;
            }

            public String getCategoryname() {
                return categoryname;
            }

            public String getCountryid() {
                return countryid;
            }

            public String getDesc() {
                return desc;
            }

            public String getDisplayorder() {
                return displayorder;
            }

            public String getEnabled() {
                return enabled;
            }

            public String getId() {
                return id;
            }

            public String getLink() {
                return link;
            }

            public String getPosition() {
                return position;
            }

            public String getShopid() {
                return shopid;
            }

            public String getThumb() {
                return thumb;
            }

            public String getUniacid() {
                return uniacid;
            }
        }
    }

    public class OneModeBeam{
        private String id;
        private String bannername;
        private String thumb;
        private String eachUrl;
        private String desc;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBannername() {
            return bannername;
        }

        public void setBannername(String bannername) {
            this.bannername = bannername;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getEachUrl() {
            return eachUrl;
        }

        public void setEachUrl(String eachUrl) {
            this.eachUrl = eachUrl;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public List<HomeNewInfo.OneModeBeam> getOneModeBeam() {
        return OneModeBeam;
    }

    public List<ActivitycategorymodelBean> getActivitycategorymodel() {
        return activitycategorymodel;
    }

    public void setActivitycategorymodel(List<ActivitycategorymodelBean> activitycategorymodel) {
        this.activitycategorymodel = activitycategorymodel;
    }

    public void setOneModeBeam(List<HomeNewInfo.OneModeBeam> oneModeBeam) {
        OneModeBeam = oneModeBeam;
    }

    public static class ActivitycategorymodelBean {
        /**
         * activitylist : [{"accategoryname":"asd","acid":"1","bannername":"test","categoryid":"1","categoryname":"asd","countryid":"48","desc":"sass","displayorder":"0","enabled":"1","id":"57","link":"","position":"0","shopid":"0","thumb":"http://images.tiaosui.com/ff15581c6d0882ae87a213027a2ab84a.jpg","uniacid":"3"}]
         * categoryid : 1
         * categoryname : asd
         * countryactivity : [{"accategoryname":"asd","acid":"1","bannername":"test","categoryid":"1","categoryname":"asd","countryid":"48","desc":"sass","displayorder":"0","enabled":"1","id":"57","link":"","position":"0","shopid":"0","thumb":"http://images.tiaosui.com/ff15581c6d0882ae87a213027a2ab84a.jpg","uniacid":"3"}]
         */

        private String categoryid;
        private String categoryname;
        private List<ActivitycategorymodelBean.ActivitylistBean> activitylist;
        private List<ActivitycategorymodelBean.CountryactivityBean> countryactivity;

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }

        public void setCategoryname(String categoryname) {
            this.categoryname = categoryname;
        }

        public void setActivitylist(List<ActivitycategorymodelBean.ActivitylistBean> activitylist) {
            this.activitylist = activitylist;
        }

        public void setCountryactivity(List<ActivitycategorymodelBean.CountryactivityBean> countryactivity) {
            this.countryactivity = countryactivity;
        }

        public String getCategoryid() {
            return categoryid;
        }

        public String getCategoryname() {
            return categoryname;
        }

        public List<ActivitycategorymodelBean.ActivitylistBean> getActivitylist() {
            return activitylist;
        }

        public List<ActivitycategorymodelBean.CountryactivityBean> getCountryactivity() {
            return countryactivity;
        }

        public static class ActivitylistBean {
            /**
             * accategoryname : asd
             * acid : 1
             * bannername : test
             * categoryid : 1
             * categoryname : asd
             * countryid : 48
             * desc : sass
             * displayorder : 0
             * enabled : 1
             * id : 57
             * link :
             * position : 0
             * shopid : 0
             * thumb : http://images.tiaosui.com/ff15581c6d0882ae87a213027a2ab84a.jpg
             * uniacid : 3
             */

            private String accategoryname;
            private String acid;
            private String bannername;
            private String categoryid;
            private String categoryname;
            private String countryid;
            private String desc;
            private String displayorder;
            private String enabled;
            private String id;
            private String link;
            private String position;
            private String shopid;
            private String thumb;
            private String uniacid;

            public void setAccategoryname(String accategoryname) {
                this.accategoryname = accategoryname;
            }

            public void setAcid(String acid) {
                this.acid = acid;
            }

            public void setBannername(String bannername) {
                this.bannername = bannername;
            }

            public void setCategoryid(String categoryid) {
                this.categoryid = categoryid;
            }

            public void setCategoryname(String categoryname) {
                this.categoryname = categoryname;
            }

            public void setCountryid(String countryid) {
                this.countryid = countryid;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public void setDisplayorder(String displayorder) {
                this.displayorder = displayorder;
            }

            public void setEnabled(String enabled) {
                this.enabled = enabled;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public void setUniacid(String uniacid) {
                this.uniacid = uniacid;
            }

            public String getAccategoryname() {
                return accategoryname;
            }

            public String getAcid() {
                return acid;
            }

            public String getBannername() {
                return bannername;
            }

            public String getCategoryid() {
                return categoryid;
            }

            public String getCategoryname() {
                return categoryname;
            }

            public String getCountryid() {
                return countryid;
            }

            public String getDesc() {
                return desc;
            }

            public String getDisplayorder() {
                return displayorder;
            }

            public String getEnabled() {
                return enabled;
            }

            public String getId() {
                return id;
            }

            public String getLink() {
                return link;
            }

            public String getPosition() {
                return position;
            }

            public String getShopid() {
                return shopid;
            }

            public String getThumb() {
                return thumb;
            }

            public String getUniacid() {
                return uniacid;
            }
        }

        public static class CountryactivityBean {
            /**
             * accategoryname : asd
             * acid : 1
             * bannername : test
             * categoryid : 1
             * categoryname : asd
             * countryid : 48
             * desc : sass
             * displayorder : 0
             * enabled : 1
             * id : 57
             * link :
             * position : 0
             * shopid : 0
             * thumb : http://images.tiaosui.com/ff15581c6d0882ae87a213027a2ab84a.jpg
             * uniacid : 3
             */

            private String accategoryname;
            private String acid;
            private String bannername;
            private String categoryid;
            private String categoryname;
            private String countryid;
            private String desc;
            private String displayorder;
            private String enabled;
            private String id;
            private String link;
            private String position;
            private String shopid;
            private String thumb;
            private String uniacid;

            public void setAccategoryname(String accategoryname) {
                this.accategoryname = accategoryname;
            }

            public void setAcid(String acid) {
                this.acid = acid;
            }

            public void setBannername(String bannername) {
                this.bannername = bannername;
            }

            public void setCategoryid(String categoryid) {
                this.categoryid = categoryid;
            }

            public void setCategoryname(String categoryname) {
                this.categoryname = categoryname;
            }

            public void setCountryid(String countryid) {
                this.countryid = countryid;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public void setDisplayorder(String displayorder) {
                this.displayorder = displayorder;
            }

            public void setEnabled(String enabled) {
                this.enabled = enabled;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public void setUniacid(String uniacid) {
                this.uniacid = uniacid;
            }

            public String getAccategoryname() {
                return accategoryname;
            }

            public String getAcid() {
                return acid;
            }

            public String getBannername() {
                return bannername;
            }

            public String getCategoryid() {
                return categoryid;
            }

            public String getCategoryname() {
                return categoryname;
            }

            public String getCountryid() {
                return countryid;
            }

            public String getDesc() {
                return desc;
            }

            public String getDisplayorder() {
                return displayorder;
            }

            public String getEnabled() {
                return enabled;
            }

            public String getId() {
                return id;
            }

            public String getLink() {
                return link;
            }

            public String getPosition() {
                return position;
            }

            public String getShopid() {
                return shopid;
            }

            public String getThumb() {
                return thumb;
            }

            public String getUniacid() {
                return uniacid;
            }
        }
    }
}
