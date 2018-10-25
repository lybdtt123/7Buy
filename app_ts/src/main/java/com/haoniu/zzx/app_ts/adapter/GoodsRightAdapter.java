package com.haoniu.zzx.app_ts.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.model.CotegoryListModel;

import java.util.List;

/**
 * Created by zzx on 2017/9/28 0028.
 */

public class GoodsRightAdapter extends BaseQuickAdapter<CotegoryListModel.ListBean,BaseViewHolder> {
    public GoodsRightAdapter(List<CotegoryListModel.ListBean> data) {
        super(R.layout.adapter_classify_text, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CotegoryListModel.ListBean categoryModel) {
        baseViewHolder.setText(R.id.tvItemText, categoryModel.getName());
        baseViewHolder.setTextColor(R.id.tvItemText, categoryModel.isCheck() ?
                mContext.getResources().getColor(R.color.colorRed) : mContext.getResources().getColor(R.color.colorGrayText68));
    }

}
