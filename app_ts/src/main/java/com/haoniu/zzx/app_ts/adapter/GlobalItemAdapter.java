package com.haoniu.zzx.app_ts.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.activity.GoodsListActivity;
import com.haoniu.zzx.app_ts.activity.WebviewActivity;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.model.CotegoryListModel;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zhangxutong .
 * Date: 16/08/28
 */

public class GlobalItemAdapter extends RecyclerView.Adapter<GlobalItemAdapter.ViewHolder> {
    private Context mContext;
    private List<CotegoryListModel.ListBean> mDatas;
    private LayoutInflater mInflater;

    public GlobalItemAdapter(Context mContext, List<CotegoryListModel.ListBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_global_item, null));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CotegoryListModel.ListBean models = mDatas.get(position);
        if (!StringUtils.isEmpty(models.getThumb())) {
            Glide.with(mContext).load(models.getThumb())
//                    .placeholder(R.mipmap.img_square)
                    .error(R.mipmap.img_square)
                    .fitCenter()
                    .into(holder.ivImg);
        } else {
            holder.ivImg.setBackgroundResource(R.mipmap.img_square);
        }
        if (models.getName() != null) {
            holder.tvGlobalName.setText(models.getName());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, GoodsListActivity.class).putExtra("id",models.getId()));
//                mContext.startActivity(new Intent(mContext, WebviewActivity.class)
//                        .putExtra("url", AppConfig.requestSearchDetail + models.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGlobalName;
        ImageView ivImg;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImg = (ImageView) itemView.findViewById(R.id.ivImg);
            tvGlobalName = (TextView) itemView.findViewById(R.id.tvGlobalName);
        }
    }
}
