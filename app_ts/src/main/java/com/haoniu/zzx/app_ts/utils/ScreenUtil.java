package com.haoniu.zzx.app_ts.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 作者： liuyuanbo on 2018/10/22 22:38.
 * 时间： 2018/10/22 22:38
 * 邮箱： 972383753@qq.com
 * 用途：
 */

public class ScreenUtil {
    public static int getScreenWidth(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }
    public static int getScreenHeight(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
}
