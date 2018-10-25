package com.haoniu.zzx.app_ts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.utils.QiNiuGlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zzx on 2018/04/18/上午 11:33
 */

public class CommentImgAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mListData;
    private LayoutInflater mInflater;
    private int type;//0,普通展示图片；1，发表评论上传图片

    public onMyImgItemClickListener onMyImgItemClickListener;

    public interface onMyImgItemClickListener {
        void onMyItemImg(int position, List<String> mImg);

        void onMyDetele(int position);
    }

    public void setOnMyImgItemClickListener(onMyImgItemClickListener listener) {
        this.onMyImgItemClickListener = listener;
    }

    public CommentImgAdapter(Context mContext, List<String> mImg, int type) {
        this.mContext = mContext;
        this.mListData = mImg;
        this.type = type;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_img, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        myViewHolder vh = (myViewHolder) holder;
        switch (type) {
            case 0:
                vh.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onMyImgItemClickListener != null) {
                            onMyImgItemClickListener.onMyItemImg(position, mListData);
                        }
                    }
                });
                break;
            case 1:
                vh.mImgDelete.setVisibility(View.VISIBLE);
                vh.mImgDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onMyImgItemClickListener != null) {
                            onMyImgItemClickListener.onMyDetele(position);
                        }
                    }
                });

                vh.mImgComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == mListData.size() - 1 && TextUtils.isEmpty(mListData.get(position))) {
                            if (onMyImgItemClickListener != null) {
                                onMyImgItemClickListener.onMyItemImg(position, mListData);
                            }
                        }
                    }
                });


                break;
        }
        Glide.with(mContext)
                .load(TextUtils.isEmpty(mListData.get(position)) ? "" : QiNiuGlideUtils.boundary640(mListData.get(position)))
                .error(R.mipmap.img_square)
                .into(vh.mImgComment);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    static class myViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_comment)
        ImageView mImgComment;
        @BindView(R.id.img_delete)
        ImageView mImgDelete;

        myViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
