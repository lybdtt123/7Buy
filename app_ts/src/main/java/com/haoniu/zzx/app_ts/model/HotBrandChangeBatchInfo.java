package com.haoniu.zzx.app_ts.model;

import java.util.List;

/**
 * Created by zzx on 2018/07/09/上午 9:35
 */

public class HotBrandChangeBatchInfo {


    /**
     * img : http://tiaosui.com/attachment/images/home/getHotbrand.png
     * goods : [{"id":"5536","merchname":"Selsun","logo":"http://images.tiaosui.com/30da49df75dca6bddd9f31af0399d509.jpg","bgimage":""},{"id":"5315","merchname":"JMsolution","logo":"http://images.tiaosui.com/506e7f2f2b72893207bdf10b4ec2698a.jpg","bgimage":""},{"id":"4721","merchname":"Whoo 后","logo":"http://images.tiaosui.com/ff1b370cea8fd9e2c59639ceaa195410.jpg","bgimage":""},{"id":"3777","merchname":"Innisfree（悦诗风吟）","logo":"http://www.chanwu7.com/attachment/images/3/2017/07/Qa7222Ztt2fN2PMyMXMIJYyVD6OZ76.png","bgimage":""},{"id":"3258","merchname":"Givenchy（纪梵希）","logo":"http://www.chanwu7.com/attachment/images/3/2017/07/qa0axd548f5UJRjuXFrKjH4wZ0WEOu.png","bgimage":""},{"id":"2835","merchname":"Estee Lauder（雅诗兰黛）","logo":"http://www.chanwu7.com/attachment/images/3/2017/07/WJVeJJkrt0RTyr99TEYJzvT9Z0r0jB.png","bgimage":""},{"id":"875","merchname":"adidas 阿迪达斯","logo":"http://images.tiaosui.com/7cdcc462bb88a40b1d5b3ad6e85e678f.jpg","bgimage":""},{"id":"650","merchname":"Lancome（兰蔻）","logo":"http://www.chanwu7.com/attachment/images/3/2017/07/MqSMmELNZolML9qo11rNN1p1wq6noM.png","bgimage":""},{"id":"189","merchname":"AHC","logo":"http://images.tiaosui.com/ad6364d188a6abd343623c755d58badb.png","bgimage":""}]
     */

    private String img;
    private List<GoodsBean> goods;

    public void setImg(String img) {
        this.img = img;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public String getImg() {
        return img;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public static class GoodsBean {
        /**
         * id : 5536
         * merchname : Selsun
         * logo : http://images.tiaosui.com/30da49df75dca6bddd9f31af0399d509.jpg
         * bgimage :
         */

        private String id;
        private String merchname;
        private String logo;
        private String bgimage;

        public void setId(String id) {
            this.id = id;
        }

        public void setMerchname(String merchname) {
            this.merchname = merchname;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public void setBgimage(String bgimage) {
            this.bgimage = bgimage;
        }

        public String getId() {
            return id;
        }

        public String getMerchname() {
            return merchname;
        }

        public String getLogo() {
            return logo;
        }

        public String getBgimage() {
            return bgimage;
        }
    }
}
