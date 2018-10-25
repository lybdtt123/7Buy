package com.haoniu.zzx.uidemo.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.haoniu.zzx.uidemo.R;

/**
 * Created by zzx on 2017/10/30 0030.
 */

public class MyDataBingdingUtil {
//    @BindingAdapter("loadToImage")
//    public static void loadToImage(ImageView imageView, String url) {
//        if (url != null) {
//            Glide.with(imageView.getContext())
//                    .load(url)
//                    .into(imageView);
//        } else {
//            imageView.setImageResource(R.mipmap.img_qr);
//        }
//    }

    @BindingAdapter("loadToImage")
    public void loadToImage(ImageView imageView, String url) {
        if (url != null) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.mipmap.img_qr);
        }
    }
}
