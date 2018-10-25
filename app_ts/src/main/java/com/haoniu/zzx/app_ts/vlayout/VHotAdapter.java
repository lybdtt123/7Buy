package com.haoniu.zzx.app_ts.vlayout;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.activity.GoodsListActivity;
import com.haoniu.zzx.app_ts.adapter.AdvantageAdapter;
import com.haoniu.zzx.app_ts.adapter.VBrandHotAdapter;
import com.haoniu.zzx.app_ts.model.AdvantageModel;
import com.haoniu.zzx.app_ts.model.BigModel;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2018/1/31 0031.
 */

public class VHotAdapter extends DelegateAdapter.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<BigModel.HotBean.GoodsBean> beans;

    public VHotAdapter(Context mContext, List<BigModel.HotBean.GoodsBean> iconBeans) {
        this.mContext = mContext;
        this.beans = iconBeans;
    }

    @Override
    public GridLayoutHelper onCreateLayoutHelper() {
        return new GridLayoutHelper(4);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_brand, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        view.setLayoutParams(layoutParams);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, final int position) {
//        RecyclerView mRecyclerView = holder.getView(R.id.mRecyclerView);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
//        mRecyclerView.setLayoutManager(gridLayoutManager);
//        VBrandHotAdapter brandHotAdapter = new VBrandHotAdapter(beans);
//        brandHotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                mContext.startActivity(new Intent(mContext, GoodsListActivity.class)
//                        .putExtra("id", beans.get(i).getId()).putExtra("shop", true));
//            }
//        });
//        mRecyclerView.setAdapter(brandHotAdapter);
//        brandHotAdapter.notifyDataSetChanged();

        ImageView ivImg = baseViewHolder.getView(R.id.ivImg);
        if (!StringUtils.isEmpty(beans.get(position).getLogo())) {
            Glide.with(mContext).load(beans.get(position).getLogo())
                    .error(R.mipmap.img_square)
                    .into(ivImg);
        } else {
            ivImg.setImageResource(R.mipmap.img_square);
        }

        LinearLayout llContent = baseViewHolder.getView(R.id.llContent);
        ViewGroup.LayoutParams layoutParams = llContent.getLayoutParams();
        layoutParams.height = (int) (DensityUtils.getWidthInPx(mContext) / 4);
        llContent.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

}
