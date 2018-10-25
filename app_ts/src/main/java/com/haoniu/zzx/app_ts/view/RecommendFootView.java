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

public class RecommendFootView extends FrameLayout {

    public RecommendFootView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_recommend_foot, this);
    }
}
