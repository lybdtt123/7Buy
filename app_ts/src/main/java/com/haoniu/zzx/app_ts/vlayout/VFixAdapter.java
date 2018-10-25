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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.activity.DianShangHuiActivity;
import com.haoniu.zzx.app_ts.activity.GlobalBuyActivity;
import com.haoniu.zzx.app_ts.activity.WebviewActivity;
import com.haoniu.zzx.app_ts.adapter.VFixIconAdapter;
import com.haoniu.zzx.app_ts.model.BigModel;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2018/1/31 0031.
 */

public class VFixAdapter extends DelegateAdapter.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<BigModel.IconBean> iconBeans;

    public VFixAdapter(Context mContext, List<BigModel.IconBean> iconBeans) {
        this.mContext = mContext;
        this.iconBeans = iconBeans;
    }

    @Override
    public GridLayoutHelper onCreateLayoutHelper() {
        return new GridLayoutHelper(4);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_icon, parent, false);
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
//        VFixIconAdapter vFixIconAdapter = new VFixIconAdapter(iconBeans);
//        mRecyclerView.setAdapter(vFixIconAdapter);
//        vFixIconAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                switch (i) {
//                    case 0: //全球购
//                        mContext.startActivity(new Intent(mContext, GlobalBuyActivity.class));
//                        break;
//                    case 2: //电商汇
//                        mContext.startActivity(new Intent(mContext, DianShangHuiActivity.class));
//                        break;
//                    default:
//                        mContext.startActivity(new Intent(mContext, WebviewActivity.class)
//                                .putExtra("url", iconBeans.get(position).getLink()));
//                        break;
//                }
//            }
//        });
//        vFixIconAdapter.notifyDataSetChanged();

        baseViewHolder.setText(R.id.tvTitle, iconBeans.get(position).getName());
        ImageView ivImg = baseViewHolder.getView(R.id.ivImg);
        if (!StringUtils.isEmpty(iconBeans.get(position).getPic())) {
            Picasso.with(mContext).load(iconBeans.get(position).getPic())
                    .fit()
                    .placeholder(R.mipmap.img_square)
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
        return iconBeans.size();
    }

}
