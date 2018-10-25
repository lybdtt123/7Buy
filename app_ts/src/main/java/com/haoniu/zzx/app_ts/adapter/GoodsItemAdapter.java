package com.haoniu.zzx.app_ts.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.activity.GoodsDetailActivity;
import com.haoniu.zzx.app_ts.activity.WebviewActivity;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.AppContext;
import com.haoniu.zzx.app_ts.http.HttpUtils;
import com.haoniu.zzx.app_ts.http.JsonCallback;
import com.haoniu.zzx.app_ts.model.NormalModel;
import com.haoniu.zzx.app_ts.myinterface.CommonEnity;
import com.haoniu.zzx.app_ts.utils.PreferenceUtils;
import com.haoniu.zzx.app_ts.utils.QiNiuGlideUtils;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.haoniu.zzx.app_ts.utils.ToastUtils;
import com.lzy.okgo.model.Response;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2017/9/21 0021.
 */

public class GoodsItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<NormalModel> models;

    public GoodsItemAdapter(Context mContext, List<NormalModel> models) {
        this.mContext = mContext;
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_item_good, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final NormalModel model = models.get(position);
        if (model.getTitle() != null) {
            String msg = model.getTitle();
            if (msg.contains("&#039;")) {
                msg = msg.replaceAll("&#039;", "'");
            }
            holder.tvGoodName.setText(msg);
        }
        holder.tvGoodPrice.setText("￥" + model.getMinprice());
        if (!StringUtils.isEmpty(models.get(position).getThumb())) {
            if (models.get(position).getThumb() != null) {
                Glide.with(mContext)
                        .load(QiNiuGlideUtils.boundary320(models.get(position).getThumb()))
                        .error(R.mipmap.img_square)
                        .into(holder.ivGoodImg);
            }
//            Picasso.with(mContext).load(models.get(position).getThumb())
//                    .placeholder(R.mipmap.img_square)
//                    .error(R.mipmap.img_square)
//                    .into(holder.ivGoodImg);
        } else {
            holder.ivGoodImg.setBackgroundResource(R.mipmap.img_square);
        }
        holder.ivShopCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cookie = PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null);
                if (cookie == null || StringUtils.isEmpty(cookie)) {
                    mContext.startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestLogin));
                    return;
                }
                requestAddToCar(models.get(position), cookie);
            }
        });

        holder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, WebviewActivity.class)
//                        .putExtra("url", AppConfig.requestGoodDetail + model.getId()));
                mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class)
                        .putExtra("id", model.getId()));
            }
        });
        return convertView;
    }

    class ViewHolder {
        private ImageView ivGoodImg, ivShopCar;
        private TextView tvGoodName, tvGoodPrice;
        private LinearLayout llContent;

        public ViewHolder(View convertView) {
            ivShopCar = (ImageView) convertView.findViewById(R.id.ivShopCar);
            ivGoodImg = (ImageView) convertView.findViewById(R.id.ivGoodImg);
            tvGoodName = (TextView) convertView.findViewById(R.id.tvGoodName);
            tvGoodPrice = (TextView) convertView.findViewById(R.id.tvGoodPrice);
            llContent = (LinearLayout) convertView.findViewById(R.id.llContent);
            ViewGroup.LayoutParams layoutParams = ivGoodImg.getLayoutParams();
            layoutParams.width = (int) ((DensityUtils.getWidthInPx(mContext) - DensityUtils.dip2px(mContext, 15)) / 3);
            layoutParams.height = layoutParams.width;
            ivGoodImg.setLayoutParams(layoutParams);
        }
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

}
