package com.haoniu.zzx.app_ts.vlayout;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.activity.ActivityActivity;
import com.haoniu.zzx.app_ts.activity.GoodsDetailActivity;
import com.haoniu.zzx.app_ts.activity.WebviewActivity;
import com.haoniu.zzx.app_ts.adapter.GoodsBottomItemAdapter;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.AppContext;
import com.haoniu.zzx.app_ts.http.HttpUtils;
import com.haoniu.zzx.app_ts.http.JsonCallback;
import com.haoniu.zzx.app_ts.model.AdvantageModel;
import com.haoniu.zzx.app_ts.model.NormalModel;
import com.haoniu.zzx.app_ts.myinterface.CommonEnity;
import com.haoniu.zzx.app_ts.utils.PreferenceUtils;
import com.haoniu.zzx.app_ts.utils.QiNiuGlideUtils;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.haoniu.zzx.app_ts.utils.ToastUtils;
import com.haoniu.zzx.app_ts.view.RoundAngleImageView;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2018/1/31 0031.
 */

public class VGoodsAdapter extends DelegateAdapter.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<NormalModel> models;

    public VGoodsAdapter(Context mContext, List<NormalModel> iconBeans) {
        this.mContext = mContext;
        this.models = iconBeans;
    }

    @Override
    public LinearLayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_bottom_good, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        view.setLayoutParams(layoutParams);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, final int position) {
        RecyclerView mRecyclerView = baseViewHolder.getView(R.id.mRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        GoodsBottomItemAdapter advantageAdapter = new GoodsBottomItemAdapter(models);
        mRecyclerView.setAdapter(advantageAdapter);
        advantageAdapter.notifyDataSetChanged();

//        NormalModel normalModel = models.get(position);
//        if (normalModel.getTitle() != null) {
//            String msg = normalModel.getTitle();
//            if (msg.contains("&#039;")) {
//                msg = msg.replaceAll("&#039;", "'");
//            }
//            baseViewHolder.setText(R.id.tvGoodName, msg);
//        }
//        baseViewHolder.setText(R.id.tvGoodPrice, "￥" + normalModel.getMinprice());
//        baseViewHolder.setText(R.id.tvGoodCountry, normalModel.getCountry());
//        ImageView ivGoodImg = baseViewHolder.getView(R.id.ivGoodImg);
//        ViewGroup.LayoutParams layoutParams = ivGoodImg.getLayoutParams();
//        layoutParams.width = (int) ((DensityUtils.getWidthInPx(mContext) - DensityUtils.dip2px(mContext, 15)) / 2);
//        layoutParams.height = layoutParams.width;
//        ivGoodImg.setLayoutParams(layoutParams);
//        if (!StringUtils.isEmpty(normalModel.getThumb())) {
//            Glide.with(mContext)
//                    .load(QiNiuGlideUtils.boundary200(normalModel.getThumb()))
////                    .load(QiNiuGlideUtils.boundary480(normalModel.getThumb()))
//                    .error(R.mipmap.img_square)
//                    .into(ivGoodImg);
//        } else {
//            ivGoodImg.setBackgroundResource(R.mipmap.img_square);
//        }
//
//        baseViewHolder.getView(R.id.ivShopCar).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //购物车按钮
//                if (AppContext.getInstance().isLogin(mContext)) {
//                    String cookie = PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null);
//                    requestAddToCar(models.get(position), cookie);
//                }
//            }
//        });
//        baseViewHolder.getView(R.id.llContent).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class)
//                        .putExtra("id", models.get(position).getId()));
////                mContext.startActivity(new Intent(mContext, WebviewActivity.class)
////                        .putExtra("url", AppConfig.requestGoodDetail + normalModel.getId()));
//            }
//        });
    }

    private void requestAddToCar(NormalModel model, String cookie) {
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

    @Override
    public int getItemCount() {
        return 1;
    }

//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if (manager instanceof GridLayoutManager) {   // 布局是GridLayoutManager所管理
//            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
//            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    // 如果是Header、Footer的对象则占据spanCount的位置，否则就只占用1个位置
//                    return isHeader(position) ? gridLayoutManager.getSpanCount() : 1;
//                }
//            });
//        }
//    }

    /**
     * 判断是否是Header的位置
     * 如果是Header的则返回true否则返回false
     *
     * @param position
     * @return
     */
    public boolean isHeader(int position) {
        return position == 0 || position == 1;
    }
}
