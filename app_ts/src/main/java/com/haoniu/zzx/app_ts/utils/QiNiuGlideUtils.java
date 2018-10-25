package com.haoniu.zzx.app_ts.utils;

/**
 * Created by zzx on 2017/12/21 0021.
 */

public class QiNiuGlideUtils {
    public static String boundary200(String url) {
        return url + "?imageView2/2/w/200/h/200/q/75|imageslim";
    }

    public static String boundary320(String url) {
        return url + "?imageView2/2/w/320/h/320/q/75|imageslim";
    }
    public static String boundary480(String url) {
        return url + "?imageView2/2/w/480/h/480/q/75|imageslim";
    }

    public static String boundary640(String url) {
        return url + "?imageView2/2/w/640/h/640/q/75|imageslim";
    }
}
