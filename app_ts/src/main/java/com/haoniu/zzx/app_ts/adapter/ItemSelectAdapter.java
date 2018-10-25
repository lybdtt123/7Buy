package com.haoniu.zzx.app_ts.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.model.CategoryModel;
import com.haoniu.zzx.app_ts.model.SelectModel;

import java.util.List;

/**
 * Created by zzx on 2017/9/28 0028.
 */

public class ItemSelectAdapter extends BaseQuickAdapter<SelectModel,BaseViewHolder> {
    public ItemSelectAdapter(List<SelectModel> data) {
        super(R.layout.adapter_select_text, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, SelectModel categoryModel) {
        baseViewHolder.setText(R.id.tvItemText, categoryModel.getMsg());
        baseViewHolder.setTextColor(R.id.tvItemText, categoryModel.isCheck() ?
                mContext.getResources().getColor(R.color.colorAccent) : mContext.getResources().getColor(R.color.colorWhite));
        baseViewHolder.setBackgroundRes(R.id.tvItemText, categoryModel.isCheck() ?
                R.drawable.shape_blue_white_5 : R.drawable.shape_blue_5);
    }

}
