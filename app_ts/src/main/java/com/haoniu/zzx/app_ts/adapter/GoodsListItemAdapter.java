package com.haoniu.zzx.app_ts.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.activity.GoodsDetailActivity;
import com.haoniu.zzx.app_ts.activity.WebviewActivity;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.AppContext;
import com.haoniu.zzx.app_ts.http.HttpUtils;
import com.haoniu.zzx.app_ts.http.JsonCallback;
import com.haoniu.zzx.app_ts.model.GoodsListModel;
import com.haoniu.zzx.app_ts.model.NormalModel;
import com.haoniu.zzx.app_ts.myinterface.CommonEnity;
import com.haoniu.zzx.app_ts.utils.GlideImageUtil;
import com.haoniu.zzx.app_ts.utils.PreferenceUtils;
import com.haoniu.zzx.app_ts.utils.QiNiuGlideUtils;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.haoniu.zzx.app_ts.utils.ToastUtils;
import com.jph.takephoto.model.TException;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2017/9/27 0027.
 */
public class GoodsListItemAdapter extends BaseQuickAdapter<GoodsListModel, BaseViewHolder> {
    public GoodsListItemAdapter(List<GoodsListModel> data) {
        super(R.layout.adapter_item_goods_list, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final GoodsListModel normalModel) {
        if (normalModel.getTitle() != null) {
            String msg = normalModel.getTitle();
            if (msg.contains("&#039;")) {
                msg = msg.replaceAll("&#039;", "'");
            }
            baseViewHolder.setText(R.id.tvGoodName, msg);
        }
        baseViewHolder.setText(R.id.tvGoodPrice, "￥" + normalModel.getMinprice());
        if (TextUtils.isEmpty(normalModel.getSaleno())) {
            baseViewHolder.setText(R.id.tv_tag1, "双十一促销");
        } else {
            Double price;
            try {
                price = Double.parseDouble(normalModel.getSaleno());
            } catch (Exception e) {
                price = 0.00;
            }
            if (0.00 == price){
                baseViewHolder.setText(R.id.tv_tag1, "双十一促销 ");
            } else{
                baseViewHolder.setText(R.id.tv_tag1, "双十一促销 | 下单立减 " + normalModel.getSaleno() + " 元");
            }
        }

        if (TextUtils.isEmpty(normalModel.getLayer())) {
            baseViewHolder.setGone(R.id.img_tag, false);
        } else {
            baseViewHolder.setVisible(R.id.img_tag, true);
            baseViewHolder.setText(R.id.img_tag, normalModel.getLayer());
        }
        if (!TextUtils.isEmpty(normalModel.getIssaleout()) && "1".equals(normalModel.getIssaleout())) {
            baseViewHolder.setVisible(R.id.img_no_goods, true);
        } else {
            baseViewHolder.setGone(R.id.img_no_goods, false);
        }
        ImageView ivGoodImg = baseViewHolder.getView(R.id.ivGoodImg);
        ViewGroup.LayoutParams layoutParams = ivGoodImg.getLayoutParams();
        layoutParams.width = (int) ((DensityUtils.getWidthInPx(mContext) - DensityUtils.dip2px(mContext, 15)) / 2);
        layoutParams.height = layoutParams.width;
        ivGoodImg.setLayoutParams(layoutParams);
        if (!StringUtils.isEmpty(normalModel.getThumb())) {
            Glide.with(mContext)
                    .load(QiNiuGlideUtils.boundary480(normalModel.getThumb()))
                    .into(ivGoodImg);
        } else {
            ivGoodImg.setImageResource(R.mipmap.img_square);
        }

        baseViewHolder.getView(R.id.ivShopCar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //购物车按钮
                if (AppContext.getInstance().isLogin(mContext)) {
                    String cookie = PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null);
                    requestAddToCar(normalModel, cookie);
                }
            }
        });
        baseViewHolder.getView(R.id.llContent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class)
                        .putExtra("id", normalModel.getId()));
//                mContext.startActivity(new Intent(mContext, WebviewActivity.class)
//                        .putExtra("url", AppConfig.requestGoodDetail + normalModel.getId()));
            }
        });
    }

    private void requestAddToCar(GoodsListModel model, String cookie) {
        Map<String, Object> map = new HashMap<>();
        map.put("cookie", cookie);
        map.put("total", 1);
        map.put("id", model.getId());
        HttpUtils.requestPosts(mContext, AppConfig.requestAddGoods, map, new JsonCallback<String>(mContext, "加入购物车...") {
            @Override
            public void onSuccess(Response<String> response) {
                ToastUtils.showTextToast(mContext, "加入成功!");
                EventBus.getDefault().post(new CommonEnity<>("addGoodsSuc"));
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                if (response.getException().getMessage() != null && response.getException().getMessage().equals("未登录")) {
                    PreferenceUtils.saveStringPreferences(mContext, AppContext.getInstance().COOKIE, "");
                    mContext.startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestLogin));
                }
            }
        });
    }
}
