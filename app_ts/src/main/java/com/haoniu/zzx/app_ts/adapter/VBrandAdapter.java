package com.haoniu.zzx.app_ts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.model.BigModel;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2017/9/22 0022.
 * 品牌适配器
 */
public class VBrandAdapter extends BaseAdapter {
    private Context mContext;
    private List<BigModel.HotBean.GoodsBean> brands;

    public VBrandAdapter(Context mContext, List<BigModel.HotBean.GoodsBean> brands) {
        this.mContext = mContext;
        this.brands = brands;
    }

    @Override
    public int getCount() {
        return brands.size();
    }

    @Override
    public Object getItem(int position) {
        return brands.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BrandHodel brandHodel = null;
        if (brandHodel == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_brand, parent, false);
            brandHodel = new BrandHodel();
            brandHodel.ivImg = (ImageView) convertView.findViewById(R.id.ivImg);
            brandHodel.llContent = (LinearLayout) convertView.findViewById(R.id.llContent);
            convertView.setTag(brandHodel);
        } else {
            brandHodel = (BrandHodel) convertView.getTag();
        }
        if (!StringUtils.isEmpty(brands.get(position).getLogo())) {
            Picasso.with(mContext).load(brands.get(position).getLogo())
                    .fit()
                    .placeholder(R.mipmap.img_square)
                    .error(R.mipmap.img_square)
                    .into(brandHodel.ivImg);
        } else {
            brandHodel.ivImg.setImageResource(R.mipmap.img_square);
        }
        ViewGroup.LayoutParams layoutParams = brandHodel.llContent.getLayoutParams();
        layoutParams.height = (int) (DensityUtils.getWidthInPx(mContext) / 4);
        brandHodel.llContent.setLayoutParams(layoutParams);
        return convertView;
    }

    class BrandHodel {
        ImageView ivImg;
        LinearLayout llContent;
    }
}
