package com.haoniu.zzx.app_ts.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.decoration.SpaceItemDecoration;
import com.haoniu.zzx.app_ts.layoutmanager.FlowLayoutManager;
import com.haoniu.zzx.app_ts.model.SpecificationsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzx on 2017/9/27 0027.
 * 内层
 */

public class Spec1Adapter extends BaseQuickAdapter<SpecificationsModel.SpecsBean, BaseViewHolder> {
    public Spec1Adapter(List<SpecificationsModel.SpecsBean> data) {
        super(R.layout.adapter_spec1, data);
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
        if (normalModel.getTitle() != null) {
            baseViewHolder.setText(R.id.tvAdapterSpecName, normalModel.getTitle());
        }
        final RecyclerView mRecyclerView = baseViewHolder.getView(R.id.mRecyclerView);
        final FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        final Spec2Adapter spec2Adapter = new Spec2Adapter(normalModel.getItems());
        final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mRecyclerView.getLayoutParams();
        lp.width = mContext.getResources().getDisplayMetrics().widthPixels;
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(mContext, 5));
        mRecyclerView.setLayoutManager(flowLayoutManager);
        mRecyclerView.setAdapter(spec2Adapter);
        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRecyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Log.d(TAG, "flowLayoutManager.getTotalHeight():" + flowLayoutManager.getTotalHeight());
                lp.height = flowLayoutManager.getTotalHeight() + mRecyclerView.getPaddingBottom() + mRecyclerView.getPaddingTop();
                mRecyclerView.setLayoutParams(lp);
                mHeight = mHeight + lp.height;
                Log.d(TAG, "lp.height:" + lp.height);
                if (baseViewHolder.getAdapterPosition() + 1 == selList.size() && recyclerViewHeight != null) {
                    recyclerViewHeight.viewTotalHeight(mHeight);
                }
            }
        });
        final int adapterPosition = baseViewHolder.getAdapterPosition();
        spec2Adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (selList.get(adapterPosition) == -1) {
                    selList.set(adapterPosition, i);
                    normalModel.getItems().get(selList.get(adapterPosition)).setSelect(true);
                    spec2Adapter.notifyItemChanged(i);
                } else {
                    normalModel.getItems().get(selList.get(adapterPosition)).setSelect(false);
                    spec2Adapter.notifyItemChanged(selList.get(adapterPosition));
                    selList.set(adapterPosition, i);
                    normalModel.getItems().get(selList.get(adapterPosition)).setSelect(true);
                    spec2Adapter.notifyItemChanged(selList.get(adapterPosition));
                }
                if (specItemClick != null) {
                    specItemClick.onItemClick(adapterPosition, i);
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
