package com.haoniu.zzx.app_ts.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.utils.QiNiuGlideUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2017/9/22 0022.
 */

public class ActivityHeadView extends FrameLayout {

    @BindView(R.id.ivHeadBg)
    ImageView ivHeadBg;
    @BindView(R.id.tvActivityMsg)
    TextView tvActivityMsg;
    private Context mContext;
    private String thumb;
    private String mTitle;
    private int flag;

    public ActivityHeadView(@NonNull Context context, int flag, String thumb, String title) {
        super(context);
        mContext = context;
        this.thumb = thumb;
        this.mTitle = title;
        this.flag = flag;
        LayoutInflater.from(context).inflate(R.layout.layout_activity_head, this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ViewGroup.LayoutParams layoutParams = ivHeadBg.getLayoutParams();
        layoutParams.width = (int) DensityUtils.getWidthInPx(mContext);
        layoutParams.height = layoutParams.width;
        ivHeadBg.setLayoutParams(layoutParams);
        Glide.with(mContext)
                .load(QiNiuGlideUtils.boundary480(thumb))
                .into(ivHeadBg);
        tvActivityMsg.setText(mTitle + "系列");
        if (flag == 1) {
            tvActivityMsg.setVisibility(View.VISIBLE);
            ivHeadBg.setVisibility(View.GONE);
        } else {
            tvActivityMsg.setVisibility(View.GONE);
            ivHeadBg.setVisibility(View.VISIBLE);
        }
    }
}
