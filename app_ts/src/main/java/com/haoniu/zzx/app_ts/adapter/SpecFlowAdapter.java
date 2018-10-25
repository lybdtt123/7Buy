package com.haoniu.zzx.app_ts.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.flowlayout.FlowLayout;
import com.haoniu.zzx.app_ts.flowlayout.TagAdapter;
import com.haoniu.zzx.app_ts.flowlayout.TagFlowLayout;
import com.haoniu.zzx.app_ts.model.SpecificationsModel;
import com.haoniu.zzx.app_ts.utils.L;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by zzx on 2017/9/27 0027.
 * 内层
 */

public class SpecFlowAdapter extends BaseQuickAdapter<SpecificationsModel.SpecsBean, BaseViewHolder> {
    public SpecFlowAdapter(List<SpecificationsModel.SpecsBean> data) {
        super(R.layout.adapter_spec11, data);
        selList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            selList.add(-1);
        }
    }

    private List<Integer> selList;
    private SpecItemClick specItemClick;

    private int mHeight = 0;

    public void setSpecItemClick(SpecItemClick specItemClick) {
        this.specItemClick = specItemClick;
    }

    public List<Integer> getSelList() {
        return selList;
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final SpecificationsModel.SpecsBean normalModel) {
        final LayoutInflater mInflater = LayoutInflater.from(mContext);
        if (normalModel.getTitle() != null) {
            baseViewHolder.setText(R.id.tvAdapterSpecName, normalModel.getTitle());
        }
        final int adapterPosition = baseViewHolder.getAdapterPosition();
        final TagFlowLayout mTagFlowLayout = baseViewHolder.getView(R.id.mTagFlowLayout);
        mTagFlowLayout.setAdapter(new TagAdapter<SpecificationsModel.SpecsBean.ItemsBean>(normalModel.getItems()) {
            @Override
            public View getView(FlowLayout parent, int position, SpecificationsModel.SpecsBean.ItemsBean itemsBean) {
                TextView tvSpecName = (TextView) mInflater.inflate(R.layout.layout_spec_text,
                        mTagFlowLayout, false);
                tvSpecName.setText(itemsBean.getTitle());
                return tvSpecName;
            }
        });
        mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (selList.get(adapterPosition) == -1) {
                    selList.set(adapterPosition, position);
                    normalModel.getItems().get(selList.get(adapterPosition)).setSelect(true);
                } else {
                    normalModel.getItems().get(selList.get(adapterPosition)).setSelect(false);
                    selList.set(adapterPosition, position);
                    normalModel.getItems().get(selList.get(adapterPosition)).setSelect(true);
                }
                if (specItemClick != null) {
                    specItemClick.onItemClick(adapterPosition, position);
                }
                return false;
            }
        });
        mTagFlowLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                if (recyclerViewHeight != null) {
                    recyclerViewHeight.viewTotalHeight(bottom);
                }
            }
        });
    }

    public interface SpecItemClick {
        void onItemClick(int selBig, int selSmall);
    }

    private RecyclerViewHeight recyclerViewHeight;

    public void setRecyclerViewHeight(RecyclerViewHeight recyclerViewHeight) {
        this.recyclerViewHeight = recyclerViewHeight;
    }

    public interface RecyclerViewHeight {
        void viewTotalHeight(int height);
    }
}
