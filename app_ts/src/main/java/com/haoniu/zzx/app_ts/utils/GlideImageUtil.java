package com.haoniu.zzx.app_ts.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.haoniu.zzx.app_ts.R;

/**
 * Created by zzx on 2018/07/02/下午 2:56
 */

public class GlideImageUtil {

    public static void normalImg(Context context, String url, View v) {
        Glide.with(context)
                .load(TextUtils.isEmpty(url) ? R.mipmap.failure_image : url)
                .error(R.mipmap.failure_image)
                .into((ImageView) v);
    }
}
