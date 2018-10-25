package com.haoniu.zzx.app_ts.model;

import java.util.List;

/**
 * Created by zzx on 2017/9/28 0028.
 */

public class CotegoryListModel {

    /**
     * id : 2688
     * name : 魅力彩妆
     * advimg :
     * list : [{"id":"4049","name":"脸部","thumb":"http://www.chanwu7.com/attachment/images/3/2017/08/F9gTDQBTm6uEN9OjM5ub6bE4J9o8ED.jpg"},{"id":"2711","name":"身体","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/efaP754ABAQ2428fEvZavRfqap5R22.jpg"},{"id":"2690","name":"眉笔","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/cf3MLnqlNfA33PLHAEmn9p9ojQ3m37.jpg"},{"id":"2691","name":"眼线笔","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/bQXzkI0x0AxTe0Ds10cZKdzxIdh4cC.jpg"},{"id":"2692","name":"眼影","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/VRH3hdI5HTD801Gf7Tt88FCd0c8phx.jpg"},{"id":"2693","name":"眼霜","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/fuuU9kZN6rnKny666P56k595VUy6yM.jpg"},{"id":"2694","name":"闪光眼影","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/RCJ02eOACbSos1252aIQARsSbE2MR8.jpg"},{"id":"2696","name":"眼线笔&眼影盘","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/U1bE1poqmSvMVmEYWw1USskQ1ogpZs.jpg"},{"id":"2697","name":"睫毛膏","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/l17E51W1wa80olOp308Ow101s78N33.jpg"},{"id":"2698","name":"唇彩","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/u0QqoRWUrz5OZ3Y0U9119Ao68898R9.jpg"},{"id":"2699","name":"唇线笔","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/JXYU133IxkQ668k0uq8rrya86U1mZg.jpg"},{"id":"2702","name":"口红","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/JKlgK16V0n5NFFWFLXKK19PMM1X9hl.jpg"},{"id":"2703","name":"唇膏","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/AqoP22nNe829rbSEcEy8scsPccVpoB.jpg"},{"id":"2700","name":"丰唇蜜","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/CL177s6Ls7J7ZKx8ZiWu4798xu9S78.jpg"},{"id":"2701","name":"染唇液","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/QIE6gBnKKUeg6EQbbQuG3xuBHxgU6Z.jpg"},{"id":"2689","name":"遮瑕膏","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/uA7SH0SdJsadsCsN7RZCM3azaDDn7S.jpg"},{"id":"2704","name":"美甲","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/xewGo4pQp7miEi7hOhhpj57R4JHqxr.jpg"},{"id":"2719","name":"卸妆水","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/MJfTtGgKB4BOB8J0OfO4OvqNGJK04x.jpg"},{"id":"4256","name":"粉底","thumb":"http://images.tiaosui.com/daf24e9a44405a9419712a0e011f932d.jpg"},{"id":"2723","name":"化妆品套装","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/R7e3eOjlAheHh3rwpHLAIHwa4jPehI.jpg"},{"id":"2718","name":"化妆调色板","thumb":"http://www.chanwu7.com/attachment/images/3/2017/07/LZCk9dYypv9vH9KH3E9vwd58e498C8.jpg"}]
     */

    private String id;
    private String name;
    private String advimg;
    private List<ListBean> list;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdvimg() {
        return advimg;
    }

    public void setAdvimg(String advimg) {
        this.advimg = advimg;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }



    public static class ListBean {
        /**
         * id : 4049
         * name : 脸部
         * thumb : http://www.chanwu7.com/attachment/images/3/2017/08/F9gTDQBTm6uEN9OjM5ub6bE4J9o8ED.jpg
         */

        private String id;
        private String name;
        private String thumb;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }
}
