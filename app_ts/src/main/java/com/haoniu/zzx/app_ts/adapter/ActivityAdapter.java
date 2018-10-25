package com.haoniu.zzx.app_ts.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.activity.GoodsDetailActivity;
import com.haoniu.zzx.app_ts.model.ActivityInfo;
import com.haoniu.zzx.app_ts.utils.QiNiuGlideUtils;
import com.haoniu.zzx.app_ts.utils.StringUtils;

import java.util.List;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2017/9/27 0027.
 */

public class ActivityAdapter extends BaseQuickAdapter<ActivityInfo, BaseViewHolder> {
    public ActivityAdapter(List<ActivityInfo> data) {
        super(R.layout.adapter_activity_good, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final ActivityInfo normalModel) {
        if (normalModel.getTitle() != null) {
            String msg = normalModel.getTitle();
            if (msg.contains("&#039;")) {
                msg = msg.replaceAll("&#039;", "'");
            }
            baseViewHolder.setText(R.id.tvGoodName, msg);
        }
        baseViewHolder.setText(R.id.tvGoodPrice, "￥" + normalModel.getMinprice());
        baseViewHolder.setText(R.id.tvGoodMerchname, normalModel.getMerchname() == null ? "" : normalModel.getMerchname());
        ImageView ivGoodImg = baseViewHolder.getView(R.id.ivGoodImg);
        ViewGroup.LayoutParams layoutParams = ivGoodImg.getLayoutParams();
        layoutParams.width = (int) ((DensityUtils.getWidthInPx(mContext) - DensityUtils.dip2px(mContext, 15)) / 2);
        layoutParams.height = layoutParams.width;
        ivGoodImg.setLayoutParams(layoutParams);
        if (!StringUtils.isEmpty(normalModel.getThumb()) ) {
            Glide.with(mContext)
                    .load(QiNiuGlideUtils.boundary480(normalModel.getThumb()))
                    .error(R.mipmap.img_square)
                    .into(ivGoodImg);
        } else {
            ivGoodImg.setBackgroundResource(R.mipmap.img_square);
        }
        baseViewHolder.getView(R.id.llContent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class)
                        .putExtra("id", normalModel.getId()));
//                mContext.startActivity(new Intent(mContext, WebviewActivity.class)
//                        .putExtra("url", AppConfig.requestGoodDetail + normalModel.getId()));
            }
        });
    }

}
