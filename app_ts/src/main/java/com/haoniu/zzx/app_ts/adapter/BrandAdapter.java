package com.haoniu.zzx.app_ts.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.activity.GoodsListActivity;
import com.haoniu.zzx.app_ts.model.BrandModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by zhangxutong .
 * Date: 16/08/28
 */

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder> {
    private Context mContext;
    private List<BrandModel> mDatas;
    private LayoutInflater mInflater;

    public BrandAdapter(Context mContext, List<BrandModel> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_clssify, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final BrandModel brandModel = mDatas.get(position);
        if (brandModel.getLogo() != null) {
            ImageLoader.getInstance().displayImage("http://www.chanwu7.com/attachment/" + brandModel.getLogo(), holder.ivBrand);
        }
        if (brandModel.getMerchname() != null) {
            String msg = brandModel.getMerchname();
            if (msg.contains("&#039;")) {
                msg = msg.replaceAll("&#039;", "'");
            }
            holder.tvBrand.setText(msg);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, GoodsListActivity.class)
                        .putExtra("id",brandModel.getId()).putExtra("shop",true));
//                mContext.startActivity(new Intent(mContext, MainActivity.class).putExtra("url", AppConfig.searchResultUrl + brandModel.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBrand;
        ImageView ivBrand;

        public ViewHolder(View itemView) {
            super(itemView);
            ivBrand = (ImageView) itemView.findViewById(R.id.ivBrand);
            tvBrand = (TextView) itemView.findViewById(R.id.tvBrand);
        }
    }
}
