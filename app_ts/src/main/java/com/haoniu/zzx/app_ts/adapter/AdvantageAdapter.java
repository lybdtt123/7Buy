package com.haoniu.zzx.app_ts.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.activity.ActivityActivity;
import com.haoniu.zzx.app_ts.model.AdvantageModel;
import com.haoniu.zzx.app_ts.utils.QiNiuGlideUtils;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.haoniu.zzx.app_ts.view.RoundAngleImageView;

import java.util.List;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2017/9/28 0028.
 */

public class AdvantageAdapter extends BaseQuickAdapter<AdvantageModel, BaseViewHolder> {
    public AdvantageAdapter(List<AdvantageModel> data) {
        super(R.layout.adapter_advantage, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final AdvantageModel model) {

        RoundAngleImageView ivImg = baseViewHolder.getView(R.id.ivImg);
        ViewGroup.LayoutParams layoutParams = ivImg.getLayoutParams();
        layoutParams.width = (int) ((DensityUtils.getWidthInPx(mContext) - DensityUtils.dip2px(mContext, 20)) / 2);
        layoutParams.height = layoutParams.width;
        ivImg.setLayoutParams(layoutParams);
        if (!StringUtils.isEmpty(model.getThumb())) {
            Glide.with(mContext)
                    .load(QiNiuGlideUtils.boundary480(model.getThumb()))
                    .error(R.mipmap.img_square)
                    .into(ivImg);
        } else {
            ivImg.setImageResource(R.mipmap.img_square);
        }
        baseViewHolder.setOnClickListener(R.id.llContent, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ActivityActivity.class)
                        .putExtra("shareUrl", model.getEachUrl())
                        .putExtra("flag", 1).putExtra("id", model.getId()).putExtra("title", model.getBannername())
                        .putExtra("desc", model.getDesc())
                        .putExtra("thumb", model.getThumb()));
//                mContext.startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", model.getEachUrl()));
            }
        });
    }

}
