package com.haoniu.zzx.app_ts.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.model.BigModel;
import com.haoniu.zzx.app_ts.model.HomeNewDataInfo;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2018/07/07/上午 10:04
 */

public class VFixIconNewAdapter extends BaseQuickAdapter<HomeNewDataInfo.IconBean,BaseViewHolder> {
    public VFixIconNewAdapter(List<HomeNewDataInfo.IconBean> data) {
        super(R.layout.adapter_icon, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomeNewDataInfo.IconBean model) {


        baseViewHolder.setText(R.id.tvTitle, model.getName());
        ImageView ivImg = baseViewHolder.getView(R.id.ivImg);
        if (!StringUtils.isEmpty(model.getPic())) {
            Picasso.with(mContext).load(model.getPic())
                    .placeholder(R.mipmap.img_square)
                    .error(R.mipmap.img_square)
                    .into(ivImg);
        } else {
            ivImg.setImageResource(R.mipmap.img_square);
        }

        LinearLayout llContent = baseViewHolder.getView(R.id.llContent);
        ViewGroup.LayoutParams layoutParams = llContent.getLayoutParams();
        layoutParams.height = (int) (DensityUtils.getWidthInPx(mContext) / 4);
        llContent.setLayoutParams(layoutParams);
    }

}
