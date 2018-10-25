package com.haoniu.zzx.app_ts.vlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.model.MainClassifyModel;
import com.haoniu.zzx.app_ts.utils.GlideImageLoader;
import com.haoniu.zzx.app_ts.utils.L;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2018/1/31 0031.
 */

public class VTopClassifyAdapter extends DelegateAdapter.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<MainClassifyModel> classifyModels;
    private List<TextView> tabTextList;

    public VTopClassifyAdapter(Context mContext, List<MainClassifyModel> classifyModels) {
        this.mContext = mContext;
        this.classifyModels = classifyModels;
        tabTextList = new ArrayList<>();
    }

    @Override
    public StickyLayoutHelper onCreateLayoutHelper() {
        return new StickyLayoutHelper();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_fix_head, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = DensityUtils.dip2px(mContext, 50f);
        view.setLayoutParams(layoutParams);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
//        View vTabCursor = holder.getView(R.id.v_tab_cursor);
//        LinearLayout llCursor = holder.getView(R.id.llCursor);
//        LinearLayout llFixHead = holder.getView(R.id.llFixHead);
//        LinearLayout llFixHeadTop = holder.getView(R.id.llFixHeadTop);
//        llCursor.setWeightSum(classifyModels.size());
//        int width = (int) (DensityUtils.getWidthInPx(mContext) / classifyModels.size());
//        int heigth = DensityUtils.dip2px(mContext, 49);
//        for (int i = 0; i < classifyModels.size(); i++) {
//            TextView textView = new TextView(mContext);
//            textView.setWidth(width);
//            textView.setHeight(heigth);
//            textView.setGravity(Gravity.CENTER);
//            try {
//                textView.setTextColor(mContext.getResources().getColor(R.color.colorGrayText88));
//            } catch (Exception e) {
//
//            }
//            textView.setText(classifyModels.get(i).getBannername() == null ? "" : classifyModels.get(i).getBannername());
//            tabTextList.add(textView);
//            llFixHeadTop.addView(textView);
//            final int finalI = i;
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    if (finalI == selIndex) {
////                        return;
////                    } else {
////                        normalModels.clear();
////                        page = 1;
////                        selIndex = finalI;
////                        requestGoods(finalI);
////                    }
//                }
//            });
//        }
//        try {
//            tabTextList.get(0).setTextColor(mContext.getResources().getColor(R.color.colorRed));
//        } catch (Exception e) {
//            L.d("Exception1:" + e.toString());
//        }
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

}
