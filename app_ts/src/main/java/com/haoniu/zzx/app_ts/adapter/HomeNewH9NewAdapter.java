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
import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2018/07/07/上午 10:29
 */

public class HomeNewH9NewAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<HomeNewInfo.ActivitycategorymodelBean.CountryactivityBean> mListImg;
    private List<HomeNewInfo.ActivitycategorymodelBean.ActivitylistBean> mListImg2;
    private int type;
    private int yy;

    public HomeNewH9NewAdapter(Context mContext, List<HomeNewInfo.ActivitycategorymodelBean.CountryactivityBean> mListImg, int type) {
        this.mContext = mContext;
        this.mListImg = mListImg;
        this.type = type;
    }

    public HomeNewH9NewAdapter(Context mContext, List<HomeNewInfo.ActivitycategorymodelBean.ActivitylistBean> mListImg, int type, int yy) {
        this.mContext = mContext;
        this.mListImg2 = mListImg;
        this.type = type;
        this.yy = yy;
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
            final HomeNewInfo.ActivitycategorymodelBean.CountryactivityBean info = mListImg.get(position);
            float with = DensityUtils.getWidthInPx(mContext);
            float picwith = ((with - DensityUtils.dip2px(mContext, 35)) / 13) * 4;
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vh.mImgContent1.getLayoutParams();
            params.width = (int) picwith;
            params.height = (int) picwith;
            vh.mImgContent1.setLayoutParams(params);
            Glide.with(mContext)
                    .load(TextUtils.isEmpty(info.getThumb()) ? R.mipmap.failure_image : info.getThumb())
                    .error(R.mipmap.failure_image)
                    .into(vh.mImgContent1);
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, ActivityActivity.class)
                            .putExtra("shareUrl", info.getLink())//TODO 分享的url暂时没有
                            .putExtra("flag", 1).putExtra("id", info.getId()).putExtra("title", info.getBannername())
                            .putExtra("desc", info.getDesc())
                            .putExtra("thumb", info.getThumb()));
                }
            });
        } else {
            myOneViewHolder vh = (myOneViewHolder) holder;
            final HomeNewInfo.ActivitycategorymodelBean.ActivitylistBean info = mListImg2.get(position);

            if (yy == 2) {
                float with = DensityUtils.getWidthInPx(mContext);
                float picwith = (with - DensityUtils.dip2px(mContext, 15)) / 2;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vh.mImgContent1.getLayoutParams();
                params.width = (int) picwith;
                params.height = (int) (picwith * 0.5);
                vh.mImgContent1.setLayoutParams(params);
            } else if (yy == 3) {
                float with = DensityUtils.getWidthInPx(mContext);
                float picwith = (with - DensityUtils.dip2px(mContext, 20)) / 3;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vh.mImgContent1.getLayoutParams();
                params.width = (int) picwith;
                params.height = (int) (picwith * 0.9);
                vh.mImgContent1.setLayoutParams(params);
            }else if (yy == 4){
                float with = DensityUtils.getWidthInPx(mContext);
                float picwith = (with - DensityUtils.dip2px(mContext, 25)) / 4;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vh.mImgContent1.getLayoutParams();
                params.width = (int) picwith;
                params.height = (int) (picwith * 0.8);
                vh.mImgContent1.setLayoutParams(params);
            }

            Glide.with(mContext)
                    .load(TextUtils.isEmpty(info.getThumb()) ? R.mipmap.failure_image : info.getThumb())
                    .error(R.mipmap.failure_image)
                    .into(vh.mImgContent1);
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, ActivityActivity.class)
                            .putExtra("shareUrl", info.getLink())//TODO 分享的url暂时没有
                            .putExtra("flag", 1).putExtra("id", info.getId()).putExtra("title", info.getBannername())
                            .putExtra("desc", info.getDesc())
                            .putExtra("thumb", info.getThumb()));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return type == 0 ? mListImg.size() : mListImg2.size();
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
