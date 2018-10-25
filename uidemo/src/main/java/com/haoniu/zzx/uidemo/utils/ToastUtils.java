package com.haoniu.zzx.uidemo.utils;

import android.content.Context;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/12/3.
 */
public class ToastUtils {
    private static Toast toast = null;

    public static void showTextToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void showTextToast(Context context, int msg) {
        if (toast == null) {
            toast = Toast.makeText(context, context.getResources().getString(msg), Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void showToast(Context context, String text) {
//        View toastRoot = LayoutInflater.from(context).inflate(R.layout.qupai_common_toast_default_layout, null, false);
//        TextView message = (TextView) toastRoot.findViewById(R.id.toast_info);
//        message.setText(text);
//        if (toast != null) {
//            toast.cancel();
//            toast = null;
//        }
//
//
//        toast = new Toast(context);
//        toast.setView(toastRoot);
//        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.show();
//        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context context, int resID) {
        showToast(context, context.getString(resID));
    }
    public static void main(String[] args) {
        String str = "总额度:<strongclass=\"amount\">11500<spanclass=\"fen\">.00</span></strong>";
        Pattern p = Pattern.compile(AlipayHuabeiZongRegex);
        Matcher m = p.matcher(str);
        while (m.find()) {
            System.out.println("------" + m.group(1));
        }
    }

    //<strongclass="amount">(.*?)<span
    public final static String AlipayHuabeiZongRegex = "总额度:<strongclass=\"amount\">(.*?)<span";
}
