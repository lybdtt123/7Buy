package com.haoniu.zzx.app_ts.http;

/**
 * Created by zzx on 2017/6/20 0020.
 */

public class AppConfig {
    //    public static String main = "http://103.22.189.2";
    public static String main = "http://www.chanwu7.com";

    /**
     * 记录fragment
     */
    public static int fragmentTag = 5;

    public static String mainUrl = main + "/getData/index.php?m=shop&c=index&a=";
    /**
     * 搜索
     */
    public static String searchUrl = main + "/getData/index.php?m=shop&c=brand&a=morebrands";
    /**
     * 订单
     */
    public static String orderUrl = main + "/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=order";
    /**
     * 上传图片
     */
    public static String uploadFile = main + "/getData/index.php?m=shop&c=index&a=uploadCard";
    /**
     * 搜索结束点击
     */
    public static String searchResultUrl = main + "/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=goods&shopid=";

    public static String uploadFileUrl = "m=shop&c=index&a=uploadFile";

    public static String requestIndex = main + "/getData/index.php?m=shop&c=index&a=";
    /**
     * 新闻--谁在挑
     */
    public static String requestTiao = main + "/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=order.selectorder";
    /**
     * 购物车
     */
    public static String requestShop = main + "/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=member.cart&status=1";
    /**
     * 分类--每日抽奖
     */
    public static String requestShare = main + "/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=sale.coupon.award";
    /**
     * 我的
     */
    public static String requestMy = main + "/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=member";
    /**
     * 首页数据
     */
    public static String requestHomeMsg = main + "/getData/index.php?m=shop&c=homeport&a=index";
    /**
     * 首页下方商品数据
     */
    public static String requestHomeGoods = main + "/getData/index.php?m=shop&c=homeport&a=startshop";
    /**
     * 登录页面
     */
    public static String requestLogin = main + "/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=account.login&type=1";
    /**
     * 加入购物车
     */
    public static String requestAddGoods = main + "/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=member.addcart.add";
    /**
     * 全球搜索  一级菜单
     */
    public static String requestCategory = main + "/getData/index.php?m=shop&c=homeport&a=category";
    /**
     * 全球搜索  二级菜单
     */
    public static String requestCategoryList = main + "/getData/index.php?m=shop&c=homeport&a=categorylist";
    /**
     * 搜索
     */
    public static String requestSearchGeneral = main + "/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=goods&merchid=0";
    /**
     * 搜索 详情
     */
    public static String requestSearchDetail = main + "/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=goods&cate=";
    /**
     * 商品详情
     */
    public static String requestGoodDetail = main + "/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=goods.detail&id=";
    /**
     *
     */
    public static String requestBrand = main + "/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=goods&shopid=";
    /**
     * 主页分类
     */
    public static String requestMainClassify = main + "/getData/index.php?m=shop&c=homeport&a=lmcategory";
    /**
     * 商品列表  商品分类
     */
    public static String requestCateList = main + "/getData/index.php?m=shop&c=homeport&a=getshopcate&cate=";
    /**
     * 商品列表
     */
    public static String requestGoodsList = main + "/getData/index.php?m=shop&c=homeport&a=getshopcategood";
    /**
     * 电商汇 国家列表
     */
    public static String requestContrys = main + "/getData/index.php?m=shop&c=homeport&a=getallNav";
    /**
     * 电商汇 国家下面的数据
     */
    public static String requestNavBrands = main + "/getData/index.php?m=shop&c=homeport&a=getnavBrand&groupid=9&countryid=";
    /**
     * 电商汇 Banner
     */
    public static String requestBanner = main + "/getData/index.php?m=shop&c=homeport&a=getnavBanner";
    /**
     * 首页活动
     */
    public static String requestAdvantage = main + "/getData/index.php?m=shop&c=homeport&a=activitycate";
    /**
     * 意向
     */
    public static String requestIntention = main + "/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=shop.shopguide.wishlist&shopid=";
    /**
     * 用户是否领过优惠券
     */
    public static String requestDiscount = main + "/getData/index.php?m=shop&c=homeport&a=ifReceive";
    /**
     * 活动列表
     */
    public static String requestActivities = main + "/getData/index.php?m=shop&c=homeport&a=activitygoods";
    /**
     * 国家 -- >  活动列表
     */
    public static String requestColumns = main + "/getData/index.php?m=shop&c=homeport&a=columngoods";
    /**
     * 国家 -- >  详情
     */
    public static String requestColumndetail = main + "/getData/index.php?m=shop&c=homeport&a=columndetail";
    /**
     * 商品 -- >  详情  cookie
     */
    public static String requestGoodsDetail = main + "/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=goods.detail&output=json";
    /**
     * 推荐商品
     */
    public static String requestGoodsRecommand = main + "/getData/index.php?m=shop&c=homeport&a=goodelse";
    /**
     * 商品规格   id
     */
    public static String requestGoodsSpecifications = main + "/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=goods.picker&output=json";
    /**
     * 关注   id  isfavorite    cookie
     */
    public static String requestGoodsFocus = main + "/getData/index.php?m=shop&c=homeport&a=iffollow";
    /**
     * 加入购物车   详情页
     */
    public static String requestAddToCar = main + "/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=goods.detail.addTocart&output=json";
    /**
     * 立即购买  id=105311 optionid total=1
     */
    public static String requestToBuy = main + "/app/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=order.create&";

//    /**
//     * 加入购物车
//     */
//    public static String requestAddGoods = main + "/index.php?i=3&c=entry&m=ewei_shopv2&do=mobile&r=member.cart.add";


    /**
     * 评论列表
     */
    public static String comment_list = main + "/getData/index.php?m=shop&c=homeport&a=comment_list";

    /**
     * 分享成功或回调
     */
    public static String SHARE_SUCCESS = main + "/getData/index.php?m=shop&c=homeport&a=sharecallback";

    /**
     * 热门品牌---换一批
     */
    public static String HOT_BRAND_CHANGE_BATCH= main + "/getData/index.php?m=shop&c=homeport&a=getHot";


    /**
     * 首页测试数据
     */
    public static String HOME_TEST_DATA= main + "/getData/index.php?m=shoptest&c=homeport&a=index";
}
