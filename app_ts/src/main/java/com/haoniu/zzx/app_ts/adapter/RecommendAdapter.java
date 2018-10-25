package com.haoniu.zzx.app_ts.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.activity.GoodsDetailActivity;
import com.haoniu.zzx.app_ts.model.ActivityInfo;
import com.haoniu.zzx.app_ts.model.RecommendModel;
import com.haoniu.zzx.app_ts.utils.QiNiuGlideUtils;
import com.haoniu.zzx.app_ts.utils.StringUtils;

import java.util.List;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx
 */
public class RecommendAdapter extends BaseQuickAdapter<RecommendModel, BaseViewHolder> {
    public RecommendAdapter(List<RecommendModel> data) {
        super(R.layout.adapter_goodsdetial_goods, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final RecommendModel normalModel) {
        LinearLayout llGoodsContent = baseViewHolder.getView(R.id.llGoodsContent);
        ViewGroup.LayoutParams layoutParams1 = llGoodsContent.getLayoutParams();
        layoutParams1.width = (int) ((DensityUtils.getWidthInPx(mContext) - DensityUtils.dip2px(mContext, 20)) / 3);
        llGoodsContent.setLayoutParams(layoutParams1);
        if (normalModel.getTitle() != null) {
            String msg = normalModel.getTitle();
            if (msg.contains("&#039;")) {
                msg = msg.replaceAll("&#039;", "'");
            }
            baseViewHolder.setText(R.id.tvGoodsName, msg);
        }
        if (normalModel.getMarketprice() != null) {
            baseViewHolder.setText(R.id.tvGoodsMarketPrice, "￥" + normalModel.getMarketprice());
        }
        if (normalModel.getInlandprice() != null) {
            TextView tvGoodsInlandPrice = baseViewHolder.getView(R.id.tvGoodsInlandPrice);
            tvGoodsInlandPrice.getPaint().setAntiAlias(true);//抗锯齿
            tvGoodsInlandPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
            baseViewHolder.setText(R.id.tvGoodsInlandPrice, "￥" + normalModel.getInlandprice());
        }
        ImageView ivGoodImg = baseViewHolder.getView(R.id.ivGoodsImg);
        ViewGroup.LayoutParams layoutParams = ivGoodImg.getLayoutParams();
        layoutParams.width = (int) ((DensityUtils.getWidthInPx(mContext) - DensityUtils.dip2px(mContext, 40)) / 3);
        layoutParams.height = layoutParams.width;
        ivGoodImg.setLayoutParams(layoutParams);
        if (!StringUtils.isEmpty(normalModel.getThumb())) {
            Glide.with(mContext)
                    .load(QiNiuGlideUtils.boundary320(normalModel.getThumb()))
                    .error(R.mipmap.img_square)
                    .into(ivGoodImg);
        }
        baseViewHolder.getView(R.id.llGoodsContent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                intent.putExtra("id", normalModel.getId());
                mContext.startActivity(intent);
            }
        });
    }

}
