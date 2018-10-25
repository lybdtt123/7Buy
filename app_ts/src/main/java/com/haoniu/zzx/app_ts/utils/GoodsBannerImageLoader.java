package com.haoniu.zzx.app_ts.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.haoniu.zzx.app_ts.R;
import com.youth.banner.loader.ImageLoader;

public class GoodsBannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        if (!StringUtils.isEmpty((String) path)) {
            Glide.with(context)
                    .load(QiNiuGlideUtils.boundary640((String) path))
                    .error(R.mipmap.img_banner_defult)
                    .into(imageView);
        }
    }
}