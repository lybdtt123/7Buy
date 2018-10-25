package com.haoniu.zzx.app_ts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
public class VFixAdapter extends BaseAdapter {
    private Context mContext;
    private List<BigModel.IconBean> iconBeanList;

    public VFixAdapter(Context mContext, List<BigModel.IconBean> iconBeanList) {
        this.mContext = mContext;
        this.iconBeanList = iconBeanList;
    }

    @Override
    public int getCount() {
        return iconBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return iconBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IconHodel brandHodel = null;
        if (brandHodel == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_icon, parent, false);
            brandHodel = new IconHodel();
            brandHodel.ivImg = (ImageView) convertView.findViewById(R.id.ivImg);
            brandHodel.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            brandHodel.llContent = (LinearLayout) convertView.findViewById(R.id.llContent);
            convertView.setTag(brandHodel);
        } else {
            brandHodel = (IconHodel) convertView.getTag();
        }
        if (!StringUtils.isEmpty(iconBeanList.get(position).getPic())) {
            Picasso.with(mContext).load(iconBeanList.get(position).getPic())
                    .fit()
                    .placeholder(R.mipmap.img_square)
                    .error(R.mipmap.img_square)
                    .into(brandHodel.ivImg);
        } else {
            brandHodel.ivImg.setImageResource(R.mipmap.img_square);
        }
        brandHodel.tvTitle.setText(iconBeanList.get(position).getName());
        ViewGroup.LayoutParams layoutParams = brandHodel.llContent.getLayoutParams();
        layoutParams.height = (int) (DensityUtils.getWidthInPx(mContext) / 4);
        brandHodel.llContent.setLayoutParams(layoutParams);
        return convertView;
    }

    class IconHodel {
        ImageView ivImg;
        LinearLayout llContent;
        TextView tvTitle;
    }
}
