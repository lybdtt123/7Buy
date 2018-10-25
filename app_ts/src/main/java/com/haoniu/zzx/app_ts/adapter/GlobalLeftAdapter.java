package com.haoniu.zzx.app_ts.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.model.CategoryModel;

import java.util.List;

/**
 * Created by zzx on 2017/9/28 0028.
 */

public class GlobalLeftAdapter extends BaseQuickAdapter<CategoryModel,BaseViewHolder> {
    public GlobalLeftAdapter(List<CategoryModel> data) {
        super(R.layout.adapter_global_left, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CategoryModel categoryModel) {
        baseViewHolder.setText(R.id.tvGlobalTitle, categoryModel.getName());
        baseViewHolder.setTextColor(R.id.tvGlobalTitle, categoryModel.isCheck() ?
                mContext.getResources().getColor(R.color.colorWhite) : mContext.getResources().getColor(R.color.colorGrayText48));
        baseViewHolder.setBackgroundRes(R.id.tvGlobalTitle, categoryModel.isCheck() ? R.drawable.shape_blue_30 : R.drawable.shape_white);
    }

}
