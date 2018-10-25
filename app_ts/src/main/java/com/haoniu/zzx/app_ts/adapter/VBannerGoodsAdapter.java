package com.haoniu.zzx.app_ts.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.activity.ActivityActivity;
import com.haoniu.zzx.app_ts.model.CountryBean;
import com.haoniu.zzx.app_ts.model.NormalModel;

import java.util.ArrayList;
import java.util.List;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2017/9/22 0022.
 */

public class VBannerGoodsAdapter extends BaseQuickAdapter<CountryBean, BaseViewHolder> {

    public VBannerGoodsAdapter(List<CountryBean> data) {
        super(R.layout.adapter_banner_goods, data);
    }
    protected boolean isScrolling = false;

    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, final CountryBean models) {
        ImageView ivImg = baseViewHolder.getView(R.id.ivImg);
        ViewGroup.LayoutParams layoutParams = ivImg.getLayoutParams();
        layoutParams.width = (int) DensityUtils.getWidthInPx(mContext);
        layoutParams.height = (int) (layoutParams.width * 2.8 / 6.4);
        ivImg.setLayoutParams(layoutParams);
        if (models.getImg()!=null&& !isScrolling) {
            Glide.with(mContext)
                    .load(models.getImg())
                    .into(ivImg);
        }
        ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ActivityActivity.class)
                        .putExtra("shareUrl", models.getEachUrl())
                        .putExtra("flag", 2).putExtra("id", models.getCountryid()));
//                mContext.startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", models.getEachUrl()));
            }
        });
        ConvenientBanner vp = baseViewHolder.getView(R.id.vp_recommend);

        vp.setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.drawable.shape_item_index_red});
        vp.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //设置如果只有一组数据时不能滑动
        vp.setManualPageable(models.getGoods().size() == 1 ? false : true);
        vp.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new GoodsViewpageAdapter();
            }
        }, handleRecommendGoods(models.getGoods()));

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
}
