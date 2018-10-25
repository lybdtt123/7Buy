package com.haoniu.zzx.app_ts.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/3.
 */
public class ToastUtils {
    private static Toast toast = null;

    public static void showTextToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }

    public static void showTextToast(Context context, int msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
