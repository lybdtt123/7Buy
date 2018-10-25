package com.haoniu.zzx.app_ts.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.model.CotegoryListModel;
import com.haoniu.zzx.app_ts.utils.QiNiuGlideUtils;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2017/9/28 0028.
 */

public class GlobalRightAdapter extends BaseQuickAdapter<CotegoryListModel,BaseViewHolder> {
    public GlobalRightAdapter(List<CotegoryListModel> data) {
        super(R.layout.adapter_global_right, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final CotegoryListModel cotegoryListModel) {
        ImageView ivGlobalImg = baseViewHolder.getView(R.id.ivGlobalImg);
        ViewGroup.LayoutParams layoutParams = ivGlobalImg.getLayoutParams();
        layoutParams.width = (int) (DensityUtils.getWidthInPx(mContext) - DensityUtils.dip2px(mContext, 101));
        layoutParams.height = layoutParams.width / 2;
        ivGlobalImg.setLayoutParams(layoutParams);
        if (!StringUtils.isEmpty(cotegoryListModel.getAdvimg())) {
            Glide.with(mContext)
                    .load(cotegoryListModel.getAdvimg())
                    .error(R.mipmap.img_global_ad)
                    .into(ivGlobalImg);
        } else {
            ivGlobalImg.setBackgroundResource(R.mipmap.img_global_ad);
        }
        baseViewHolder.setText(R.id.tvGlobalTitle, cotegoryListModel.getName());
        RecyclerView mRecyclerView = baseViewHolder.getView(R.id.mRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        GlobalItemAdapter globalItemAdapter = new GlobalItemAdapter(mContext, cotegoryListModel.getList());
        mRecyclerView.setAdapter(globalItemAdapter);
        globalItemAdapter.notifyDataSetChanged();
    }
}
