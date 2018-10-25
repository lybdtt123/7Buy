package com.haoniu.zzx.app_ts.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.activity.GlobalBuyActivity;
import com.haoniu.zzx.app_ts.activity.WebviewActivity;
import com.haoniu.zzx.app_ts.adapter.VBannerGoodsAdapter;
import com.haoniu.zzx.app_ts.adapter.VBrandHotAdapter;
import com.haoniu.zzx.app_ts.adapter.VFixIconAdapter;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.model.BigModel;
import com.haoniu.zzx.app_ts.utils.GlideImageLoader;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zzx on 2017/9/22 0022.
 */

public class HomepageHeadView extends FrameLayout {


    @BindView(R.id.mBanner)
    Banner mBanner;
    @BindView(R.id.ivImgBg)
    ImageView ivImgBg;
    @BindView(R.id.ivImgGoodsBg)
    ImageView ivImgGoodsBg;
    @BindView(R.id.recycleViewHot)
    RecyclerView recycleViewHot;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.recycleViewIcon)
    RecyclerView recycleViewIcon;

    private Context mContext;
    private List<String> bannerUrls;
    private BigModel bigModel;
    // 固定
    private VFixIconAdapter vFixIconAdapter;
    // 热门品牌
    private VBrandHotAdapter vBrandHotAdapter;
    // 不同国家
    private VBannerGoodsAdapter vBannerGoodsAdapter;

    public HomepageHeadView(@NonNull Context context, BigModel bigModel) {
        super(context);
        mContext = context;
        this.bigModel = bigModel;
        LayoutInflater.from(context).inflate(R.layout.view_head, this);
        ButterKnife.bind(this);
        initBanner();
    }

    private void initBanner() {

        bannerUrls = new ArrayList<>();
        for (int i = 0; i < bigModel.getBanner().size(); i++) {
            bannerUrls.add(bigModel.getBanner().get(i).getThumb());
        }
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerAnimation(Transformer.Default);
        mBanner.isAutoPlay(true);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setDelayTime(3000);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setImages(bannerUrls);
        mBanner.start();
        //  四个固定标题
        vFixIconAdapter = new VFixIconAdapter(bigModel.getIcon());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        recycleViewIcon.setLayoutManager(gridLayoutManager);
        vFixIconAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                switch (i) {
                    case 0: //全球购
                        mContext.startActivity(new Intent(mContext, GlobalBuyActivity.class));
                        break;
                    default:
                        mContext.startActivity(new Intent(mContext, WebviewActivity.class)
                                .putExtra("url", bigModel.getIcon().get(i).getLink()));
                        break;
                }
            }
        });
        recycleViewIcon.setAdapter(vFixIconAdapter);
//        vFixIconAdapter.notifyDataSetChanged();
        //  热门品牌
        if (!StringUtils.isEmpty(bigModel.getHot().getImg())) {
            Picasso.with(mContext).load(bigModel.getHot().getImg())
                    .fit()
                    .placeholder(R.mipmap.img_banner_normal)
                    .into(ivImgBg);
        } else {
            ivImgBg.setImageResource(R.mipmap.img_banner_normal);
        }
        if (!StringUtils.isEmpty(bigModel.getSelectPic())) {
            Picasso.with(mContext).load(bigModel.getSelectPic())
                    .fit()
                    .error(R.mipmap.img_square)
                    .placeholder(R.mipmap.img_square)
                    .into(ivImgGoodsBg);
        } else {
            ivImgGoodsBg.setImageResource(R.mipmap.img_square);
        }
        vBrandHotAdapter = new VBrandHotAdapter(bigModel.getHot().getGoods());
        vBrandHotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                mContext.startActivity(new Intent(mContext, WebviewActivity.class)
                        .putExtra("url", AppConfig.requestBrand + bigModel.getHot().getGoods().get(i).getId()));
            }
        });
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(mContext, 4);
        recycleViewHot.setLayoutManager(gridLayoutManager1);
        recycleViewHot.setAdapter(vBrandHotAdapter);
//        vBrandHotAdapter.notifyDataSetChanged();

        recycleView.setLayoutManager(new LinearLayoutManager(mContext));
        vBannerGoodsAdapter = new VBannerGoodsAdapter(bigModel.getHome());
        recycleView.setAdapter(vBannerGoodsAdapter);
//        vBannerGoodsAdapter.notifyDataSetChanged();

    }

}
