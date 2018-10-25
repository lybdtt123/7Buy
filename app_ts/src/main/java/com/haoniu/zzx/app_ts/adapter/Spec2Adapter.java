package com.haoniu.zzx.app_ts.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.model.SpecificationsModel;

import java.util.List;

/**
 * Created by zzx on 2017/9/27 0027.
 * 内层
 */

public class Spec2Adapter extends BaseQuickAdapter<SpecificationsModel.SpecsBean.ItemsBean, BaseViewHolder> {
    public Spec2Adapter(List<SpecificationsModel.SpecsBean.ItemsBean> data) {
        super(R.layout.adapter_spec2, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final SpecificationsModel.SpecsBean.ItemsBean normalModel) {
        TextView tvAdapterSpecName = baseViewHolder.getView(R.id.tvAdapterSpecName);
        if (normalModel.getTitle() != null) {
            tvAdapterSpecName.setText(normalModel.getTitle());
        }
        if (normalModel.isSelect()) {
            tvAdapterSpecName.setBackgroundResource(R.drawable.shape_theme_5);
            tvAdapterSpecName.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
        } else {
            tvAdapterSpecName.setBackgroundResource(R.drawable.shape_gray_5);
            tvAdapterSpecName.setTextColor(mContext.getResources().getColor(R.color.colorGrayText48));
        }
    }
}
