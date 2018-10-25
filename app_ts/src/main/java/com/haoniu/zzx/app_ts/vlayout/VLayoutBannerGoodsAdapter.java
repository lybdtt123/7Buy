package com.haoniu.zzx.app_ts.vlayout;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.activity.ActivityActivity;
import com.haoniu.zzx.app_ts.adapter.GoodsViewpageAdapter;
import com.haoniu.zzx.app_ts.adapter.VBannerGoodsAdapter;
import com.haoniu.zzx.app_ts.model.CountryBean;
import com.haoniu.zzx.app_ts.model.NormalModel;

import java.util.ArrayList;
import java.util.List;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2018/1/31 0031.
 */

public class VLayoutBannerGoodsAdapter extends DelegateAdapter.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<CountryBean> beans;

    public VLayoutBannerGoodsAdapter(Context mContext, List<CountryBean> iconBeans) {
        this.mContext = mContext;
        this.beans = iconBeans;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_banner_goods, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        view.setLayoutParams(layoutParams);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, final int position) {
//        RecyclerView mRecyclerView = holder.getView(R.id.mRecyclerView);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        VBannerGoodsAdapter vBannerGoodsAdapter = new VBannerGoodsAdapter(beans);
//        mRecyclerView.setAdapter(vBannerGoodsAdapter);
//        vBannerGoodsAdapter.notifyDataSetChanged();
        ImageView ivImg = baseViewHolder.getView(R.id.ivImg);
        ViewGroup.LayoutParams layoutParams = ivImg.getLayoutParams();
        layoutParams.width = (int) DensityUtils.getWidthInPx(mContext);
        layoutParams.height = (int) (layoutParams.width * 2.8 / 6.4);
        ivImg.setLayoutParams(layoutParams);
        if (beans.get(position).getImg() != null) {
            Glide.with(mContext)
                    .load(beans.get(position).getImg())
                    .into(ivImg);
        }
        ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ActivityActivity.class)
                        .putExtra("shareUrl", beans.get(position).getEachUrl())
                        .putExtra("flag", 2).putExtra("id", beans.get(position).getCountryid()));
//                mContext.startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", models.getEachUrl()));
            }
        });
        ConvenientBanner vp = baseViewHolder.getView(R.id.vp_recommend);

        vp.setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.drawable.shape_item_index_red});
        vp.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //设置如果只有一组数据时不能滑动
        vp.setManualPageable(beans.get(position).getGoods().size() == 1 ? false : true);
        vp.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new GoodsViewpageAdapter();
            }
        }, handleRecommendGoods(beans.get(position).getGoods()));
    }

    /**
     * 处理推荐商品数据(每三个分为一组)
     *
     * @return
     */
    public List<List<NormalModel>> handleRecommendGoods(List<NormalModel> models) {
        List<List<NormalModel>> handleData = new ArrayList<>();
        int length = models.size() / 3;
        if (models.size() % 3 != 0) {
            length = models.size() / 3 + 1;
        }
        for (int i = 0; i < length; i++) {
            List<NormalModel> recommendGoods = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                if (i * 3 + j < models.size())
                    recommendGoods.add(models.get(i * 3 + j));
            }
            handleData.add(recommendGoods);
        }
        return handleData;
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

}
