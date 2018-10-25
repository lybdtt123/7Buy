package com.haoniu.zzx.app_ts.model;

import java.util.List;

/**
 * Created by zzx on 2017/9/27 0027.
 */

public class BigModel {

    private HotBean hot;
    private String selectPic;
    private List<BannerBean> banner;
    private List<IconBean> icon;
    private List<CountryBean> home;

    public HotBean getHot() {
        return hot;
    }

    public void setHot(HotBean hot) {
        this.hot = hot;
    }

    public String getSelectPic() {
        return selectPic;
    }

    public void setSelectPic(String selectPic) {
        this.selectPic = selectPic;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<IconBean> getIcon() {
        return icon;
    }

    public void setIcon(List<IconBean> icon) {
        this.icon = icon;
    }

    public List<CountryBean> getHome() {
        return home;
    }

    public void setHome(List<CountryBean> home) {
        this.home = home;
    }

    public static class HotBean {
        /**
         * img : http://103.22.189.2/attachment/images/home/getHotbrand.png
         * goods : [{"id":"4709","merchname":"Lirikos 俪瑞思","logo":"http://images.tiaosui.com/2ec19d0bd44f5e74134251ef07187575.jpg"},{"id":"4707","merchname":"HANYUL 韩律","logo":"http://images.tiaosui.com/1703c347068de8506b9be9b9d92d7f6b.jpg"},{"id":"4703","merchname":"Merries 花王妙而舒","logo":"http://images.tiaosui.com/72d66bca96424879d3348db75e3bbefb.jpg"},{"id":"4689","merchname":"a2","logo":"http://images.tiaosui.com/961531061545bd67026598c4661834bc.jpg"},{"id":"4681","merchname":"HiPP 喜宝","logo":"http://images.tiaosui.com/52652911b07746e6651aa26eff31e92d.jpg"},{"id":"3825","merchname":"IOPE（艾诺碧）","logo":"http://images.tiaosui.com/dc305f89fa7fabf42c8cd1ab8ed1f923.png"},{"id":"654","merchname":"Laneige（兰芝）","logo":"http://www.chanwu7.com/attachment/images/3/2017/07/NNaDHoMUuM1h1LzNg39Zq3z0GM21NB.png"},{"id":"601","merchname":"Mamonde（梦妆）","logo":"http://images.tiaosui.com/b720c91ddd8c20c637da66712ccbd198.png"}]
         */

        private String img;
        private List<GoodsBean> goods;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * id : 4709
             * merchname : Lirikos 俪瑞思
             * logo : http://images.tiaosui.com/2ec19d0bd44f5e74134251ef07187575.jpg
             */

            private String id;
            private String merchname;
            private String logo;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMerchname() {
                return merchname;
            }

            public void setMerchname(String merchname) {
                this.merchname = merchname;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }
        }
    }

    public static class BannerBean {
        /**
         * link :
         * thumb : http://images.tiaosui.com/eab44ace5c0e23cc9c69e675ec34ea50.jpg
         */

        private String link;
        private String thumb;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }

    public static class IconBean {
        /**
         * pic : http://103.22.189.2/attachment/images/home/quanqiugou.png
         * name : 全球购
         * link : http://103.22.189.2/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=shop.category
         */

        private String pic;
        private String name;
        private String link;

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
