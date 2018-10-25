package com.haoniu.zzx.app_ts.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.model.BigModel;
import com.haoniu.zzx.app_ts.model.DianShangNavModel;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2017/9/28 0028.
 */

public class DianShangNavAdapter extends BaseQuickAdapter<DianShangNavModel,BaseViewHolder> {
    public DianShangNavAdapter(List<DianShangNavModel> data) {
        super(R.layout.adapter_cotrynav, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, DianShangNavModel model) {

        ImageView ivImg = baseViewHolder.getView(R.id.ivImg);
        if (!StringUtils.isEmpty(model.getLogo())) {
            Picasso.with(mContext).load(model.getLogo())
                    .error(R.mipmap.img_square)
                    .into(ivImg);
        } else {
            ivImg.setImageResource(R.mipmap.img_square);
        }

        LinearLayout llContent = baseViewHolder.getView(R.id.llContent);
        ViewGroup.LayoutParams layoutParams = llContent.getLayoutParams();
        layoutParams.width = (int) (DensityUtils.getWidthInPx(mContext) / 3) - 1;
        layoutParams.height = layoutParams.width * 3 / 4;
        llContent.setLayoutParams(layoutParams);
    }

}
