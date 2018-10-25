package com.haoniu.zzx.app_ts.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.model.CategoryModel;
import com.haoniu.zzx.app_ts.model.ItemTextModel;

import java.util.List;

/**
 * Created by zzx on 2017/9/28 0028.
 */

public class ItemTextAdapter extends BaseQuickAdapter<ItemTextModel,BaseViewHolder> {
    public ItemTextAdapter(List<ItemTextModel> data) {
        super(R.layout.adapter_iten_text, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ItemTextModel model) {
        baseViewHolder.setText(R.id.tvItemText, model.getName() == null ? "" : model.getName());
        if (model.isCheck()) {
            baseViewHolder.setTextColor(R.id.tvItemText, mContext.getResources().getColor(R.color.colorRed));
        } else {
            baseViewHolder.setTextColor(R.id.tvItemText, mContext.getResources().getColor(R.color.colorGrayText28));
        }
    }

}
