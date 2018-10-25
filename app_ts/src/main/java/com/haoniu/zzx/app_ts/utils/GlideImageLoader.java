package com.haoniu.zzx.app_ts.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.haoniu.zzx.app_ts.R;
import com.squareup.picasso.Picasso;
import com.youth.banner.loader.ImageLoader;

import self.androidbase.utils.DensityUtils;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if (!StringUtils.isEmpty((String) path)) {
            Glide.with(context).load(path.toString())
                    .fitCenter()
                    .error(R.mipmap.img_banner_normal)
                    .into(imageView);
//            Picasso.with(context).load((String) path)
////                    .centerInside()
//                    .error(R.mipmap.img_banner_normal)
//                    .into(imageView);
        } else {
            imageView.setBackgroundResource(R.mipmap.img_banner_normal);
        }
//        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage((String) path, imageView);
//        Uri uri = Uri.parse((String) path);
//        imageView.setImageURI(uri);
    }


}