package com.haoniu.zzx.app_ts.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.model.CommentInfo;
import com.haoniu.zzx.app_ts.model.CommentListInfo;
import com.haoniu.zzx.app_ts.utils.GlideCircleTransform;
import com.haoniu.zzx.app_ts.utils.QiNiuGlideUtils;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.haoniu.zzx.app_ts.view.MyRecyclerView;
import com.haoniu.zzx.app_ts.view.ZQImageViewRoundOval;
import com.idlestar.ratingstar.RatingStarView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zouliwen on 2018/04/18/上午 11:57
 */

public class CommentListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<CommentListInfo> mListData;
    private LayoutInflater mInflater;
    private int type;

    public onMyClickListener onMyClickListener;

    public interface onMyClickListener {
        void onMyClick(int position, List<String> mImg);

        void omMyDetailClick(int position);
    }

    public void setOnMyClickListener(onMyClickListener listener) {
        this.onMyClickListener = listener;
    }

    public CommentListAdapter(Context mContext, List<CommentListInfo> mListData, int type) {
        this.mContext = mContext;
        this.mListData = mListData;
        this.type = type;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_comment_list, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        myViewHolder vh = (myViewHolder) holder;
        Glide.with(mContext)
                .load(TextUtils.isEmpty(mListData.get(position).getAvatar()) ? "" : mListData.get(position).getAvatar())
                .transform(new GlideCircleTransform(mContext))
                .error(R.mipmap.img_main55b)
                .into(vh.mImgHeard);
        vh.mTvNikeName.setText(TextUtils.isEmpty(mListData.get(position).getNickname()) ? "" : mListData.get(position).getNickname());
//        vh.mTvLv.setText(TextUtils.isEmpty(mListData.get(position).getmLv()) ? "" : mListData.get(position).getmLv());
        vh.mTvCommtent.setText(TextUtils.isEmpty(mListData.get(position).getContent()) ? "" : mListData.get(position).getContent());
        vh.mTvCommtentTime.setText(TextUtils.isEmpty(mListData.get(position).getCreatetime()) ? "" : StringUtils.formatTime(mListData.get(position).getCreatetime()));
        vh.mRsView.setEnabled(false);
        vh.mRsView.setRating(mListData.get(position).getStar());
        vh.mTvShopReturn.setText(TextUtils.isEmpty(mListData.get(position).getBacklist()) ? "" : "商家回复：" + mListData.get(position).getBacklist());
        vh.mTvShopReturn.setVisibility(TextUtils.isEmpty(mListData.get(position).getBacklist()) ? View.GONE : View.VISIBLE);
        if (mListData.size() >= 2) {
            if (type == 1) {
                vh.mTvMore.setVisibility(View.GONE);
            } else {
                if (position == 0) {
                    vh.mTvMore.setVisibility(View.GONE);
                } else {
                    vh.mTvMore.setVisibility(View.VISIBLE);
                }

            }
        } else {
            vh.mTvMore.setVisibility(View.GONE);
        }
        vh.mTvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMyClickListener != null) {
                    onMyClickListener.omMyDetailClick(position);
                }
            }
        });


        LinearLayoutManager lm = new GridLayoutManager(mContext, 3);
        CommentImgAdapter mAdapter = new CommentImgAdapter(mContext, mListData.get(position).getImages(), 0);
        vh.mMyRcPic.setLayoutManager(lm);
        vh.mMyRcPic.setAdapter(mAdapter);
        mAdapter.setOnMyImgItemClickListener(new CommentImgAdapter.onMyImgItemClickListener() {
            @Override
            public void onMyItemImg(int position, List<String> mImg) {
                if (onMyClickListener != null) {
                    onMyClickListener.onMyClick(position, mImg);
                }
            }

            @Override
            public void onMyDetele(int position) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }


    static class myViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_heard)
        ImageView mImgHeard;
        @BindView(R.id.tv_nike_name)
        TextView mTvNikeName;
        @BindView(R.id.tv_lv)
        TextView mTvLv;
        @BindView(R.id.tv_commtent_time)
        TextView mTvCommtentTime;
        @BindView(R.id.rs_view)
        RatingStarView mRsView;
        @BindView(R.id.tv_commtent)
        TextView mTvCommtent;
        @BindView(R.id.my_rc_pic)
        MyRecyclerView mMyRcPic;
        @BindView(R.id.tv_more)
        TextView mTvMore;
        @BindView(R.id.tv_shop_return)
        TextView mTvShopReturn;

        myViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
