package com.haoniu.zzx.app_ts.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.activity.ActivityActivity;
import com.haoniu.zzx.app_ts.model.HomeNewInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zlw on 2018/07/02/上午 11:55
 * item 第一个布局的第一个rc
 */

public class HomeNewH9Adapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<HomeNewInfo.ActivityglobalmodelBean.CountryactivityBean> mListImg;
    private List<HomeNewInfo.ActivityglobalmodelBean.ActivitylistBean> mListImg1;
    private int type;

    public HomeNewH9Adapter(Context mContext, List<HomeNewInfo.ActivityglobalmodelBean.CountryactivityBean> mListImg, int type) {
        this.mContext = mContext;
        this.mListImg = mListImg;
        this.type = type;
    }

    public HomeNewH9Adapter(Context mContext, List<HomeNewInfo.ActivityglobalmodelBean.ActivitylistBean> mListImg, int type, int yy) {
        this.mContext = mContext;
        this.mListImg1 = mListImg;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (type == 0) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_new_one, null);
            view.setLayoutParams(lp);
            return new myViewHolder(view);
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_new_two, null);
        view.setLayoutParams(lp);
        return new myOneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof myViewHolder) {
            myViewHolder vh = (myViewHolder) holder;
            final HomeNewInfo.ActivityglobalmodelBean.CountryactivityBean info = mListImg.get(position);

            Glide.with(mContext)
                    .load(TextUtils.isEmpty(info.getThumb()) ? R.mipmap.failure_image : info.getThumb())
                    .error(R.mipmap.failure_image)
                    .into(vh.mImgContent1);
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, ActivityActivity.class)
                            .putExtra("shareUrl", info.getLink())//TODO 暂无分享连接
                            .putExtra("flag", 2).putExtra("id", info.getId()).putExtra("title", info.getBannername())
                            .putExtra("desc", info.getDesc())
                            .putExtra("thumb", info.getThumb()));
                }
            });
        } else {
            myOneViewHolder vh = (myOneViewHolder) holder;
            final HomeNewInfo.ActivityglobalmodelBean.ActivitylistBean info = mListImg1.get(position);
            Glide.with(mContext)
                    .load(TextUtils.isEmpty(info.getThumb()) ? R.mipmap.failure_image : info.getThumb())
                    .error(R.mipmap.failure_image)
                    .into(vh.mImgContent1);
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, ActivityActivity.class)
                            .putExtra("shareUrl", info.getLink())//TODO 暂无分享连接
                            .putExtra("flag", 1).putExtra("id", info.getId()).putExtra("title", info.getBannername())
                            .putExtra("desc", info.getDesc())
                            .putExtra("thumb", info.getThumb()));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return type == 0 ? mListImg.size() : mListImg1.size();
    }

    static class myViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_content_1)
        ImageView mImgContent1;

        myViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public onMyOneClickListener onMyOneClickListener;

    public interface onMyOneClickListener {
        void onOneClick(int position);
    }

    public void setOnMyOneClickListener(onMyOneClickListener listener) {
        this.onMyOneClickListener = listener;
    }


    static class myOneViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_content_1)
        ImageView mImgContent1;

        myOneViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
