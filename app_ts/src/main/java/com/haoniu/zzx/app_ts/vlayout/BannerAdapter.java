package com.haoniu.zzx.app_ts.vlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2018/1/31 0031.
 */

public class BannerAdapter extends DelegateAdapter.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<String> bannerUrls;

    public BannerAdapter(Context mContext, List<String> bannerUrls) {
        this.mContext = mContext;
        this.bannerUrls = bannerUrls;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_banner, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) DensityUtils.getWidthInPx(mContext);
        layoutParams.height = layoutParams.width * 35 / 64;
        view.setLayoutParams(layoutParams);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Banner mBanner = holder.getView(R.id.mBanner);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerAnimation(Transformer.Default);
        mBanner.isAutoPlay(true);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setDelayTime(3000);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setImages(bannerUrls);
        mBanner.start();
    }

//    @Override
//    public void onBindViewHolder(BannerHolder holder, int position) {
//        holder.mBanner.setImageLoader(new GlideImageLoader());
//        holder.mBanner.setBannerAnimation(Transformer.Default);
//        holder.mBanner.isAutoPlay(true);
//        holder.mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
//        holder.mBanner.setDelayTime(3000);
//        holder.mBanner.setIndicatorGravity(BannerConfig.CENTER);
//        holder.mBanner.setImages(bannerUrls);
//        holder.mBanner.start();
//    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class BannerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mBanner)
        Banner mBanner;

        BannerHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
