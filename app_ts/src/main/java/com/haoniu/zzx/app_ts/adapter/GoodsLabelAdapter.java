package com.haoniu.zzx.app_ts.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;

import java.util.List;

/**
 * Created by zzx on 2017/9/27 0027.
 */

public class GoodsLabelAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public GoodsLabelAdapter(List<String> data) {
        super(R.layout.adapter_goods_lablename, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final String label) {
        if (label != null) {
            baseViewHolder.setText(R.id.tvLabelName, label);
        }
    }
}
