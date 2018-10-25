package com.haoniu.zzx.app_ts.vlayout;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.activity.ActivityActivity;
import com.haoniu.zzx.app_ts.model.AdvantageModel;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.haoniu.zzx.app_ts.view.RoundAngleImageView;

import java.util.List;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2018/1/31 0031.
 */

public class VActivityAdapter extends DelegateAdapter.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<AdvantageModel> models;

    public VActivityAdapter(Context mContext, List<AdvantageModel> iconBeans) {
        this.mContext = mContext;
        this.models = iconBeans;
    }

    @Override
    public GridLayoutHelper onCreateLayoutHelper() {
        return new GridLayoutHelper(2);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_advantage, parent, false);
//        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        view.setLayoutParams(layoutParams);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
//        RecyclerView mRecyclerView = holder.getView(R.id.mRecyclerView);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
//        mRecyclerView.setLayoutManager(gridLayoutManager);
//        AdvantageAdapter advantageAdapter = new AdvantageAdapter(iconBeans);
//        mRecyclerView.setAdapter(advantageAdapter);
//        advantageAdapter.notifyDataSetChanged();


        RoundAngleImageView ivImg = holder.getView(R.id.ivImg);
        ViewGroup.LayoutParams layoutParams = ivImg.getLayoutParams();
        layoutParams.width = (int) ((DensityUtils.getWidthInPx(mContext) - DensityUtils.dip2px(mContext, 20)) / 2);
        layoutParams.height = layoutParams.width;
        ivImg.setLayoutParams(layoutParams);
        if (!StringUtils.isEmpty(models.get(position).getThumb())) {
            Glide.with(mContext)
                    .load(models.get(position).getThumb())
                    .error(R.mipmap.img_square)
                    .into(ivImg);
        } else {
            ivImg.setImageResource(R.mipmap.img_square);
        }
        holder.setOnClickListener(R.id.llContent, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ActivityActivity.class)
                        .putExtra("shareUrl", models.get(position).getEachUrl())
                        .putExtra("flag", 1).putExtra("id", models.get(position).getId()).putExtra("title", models.get(position).getBannername())
                        .putExtra("desc", models.get(position).getDesc())
                        .putExtra("thumb", models.get(position).getThumb()));
//                mContext.startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", model.getEachUrl()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

}
