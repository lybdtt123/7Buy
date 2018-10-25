package com.haoniu.zzx.app_ts.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.awen.photo.photopick.bean.PhotoResultBean;
import com.awen.photo.photopick.controller.PhotoPickConfig;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.adapter.CommentImgAdapter;
import com.idlestar.ratingstar.RatingStarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zzx on 2018/04/18/下午 5:37
 */

public class PublishCommentActivity extends BaseActivity implements CommentImgAdapter.onMyImgItemClickListener {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rs_view)
    RatingStarView mRsView;
    @BindView(R.id.tv_good_lv)
    TextView mTvGoodLv;
    @BindView(R.id.et_comment)
    EditText mEtComment;
    @BindView(R.id.rc_img)
    RecyclerView mRcImg;
    @BindView(R.id.tv_sure)
    TextView mTvSure;
    @BindView(R.id.img)
    ImageView mImg;

    private CommentImgAdapter mAdapter;
    private List<String> mListImg = new ArrayList<>();

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_publish_comment);
    }

    @Override
    protected void initView() {
        mTvTitle.setText("发表评价");
        LinearLayoutManager lm = new GridLayoutManager(mContext, 3);
        mAdapter = new CommentImgAdapter(mContext, mListImg, 1);
        mRcImg.setLayoutManager(lm);
        mRcImg.setAdapter(mAdapter);
        mAdapter.setOnMyImgItemClickListener(this);
    }

    @Override
    protected void initLogic() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }


    @OnClick({R.id.ll_back, R.id.tv_sure, R.id.img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_sure:
                break;
            case R.id.img:

                new PhotoPickConfig.Builder(this)
                        .pickMode(PhotoPickConfig.MODE_MULTIP_PICK) //多选，这里有单选和多选
                        .maxPickSize(5)                            //最多可选15张
                        .showCamera(true)                          //是否展示拍照icon,默认展示
                        .clipPhoto(false)                            //是否选完图片进行图片裁剪，默认是false,如果这里设置成true,就算设置了是多选也会被配置成单选
                        .spanCount(4)                               //图库的列数，默认3列，这个数建议不要太大
                        .showGif(true)//default true                //是否展示gif
                        .setOnPhotoResultCallback(new PhotoPickConfig.Builder.OnPhotoResultCallback() {
                            @Override
                            public void onResult(PhotoResultBean photoResultBean) {
                                Log.e("MainActivity", "result = " + photoResultBean.getPhotoLists().size());

                            }
                        }) //设置数据回调，如果不想在Activity通过onActivityResult()获取回传的数据，可实现此接口
                        .build();
                break;
        }
    }


    private List<String> commentImg(List<String> img) {
        if (img.size() < 5) {
            for (String s : img) {
                if (TextUtils.isEmpty(s)) {
                    img.remove(s);
                }
            }
            img.add("");
        }
        return img;
    }


    /**
     * 再次上传
     *
     * @param position
     * @param mImg
     */
    @Override
    public void onMyItemImg(int position, List<String> mImg) {
        new PhotoPickConfig.Builder(this)
                .pickMode(PhotoPickConfig.MODE_MULTIP_PICK) //多选，这里有单选和多选
                .maxPickSize(5 - mImg.size())                            //最多可选15张
                .showCamera(true)                          //是否展示拍照icon,默认展示
                .clipPhoto(false)                            //是否选完图片进行图片裁剪，默认是false,如果这里设置成true,就算设置了是多选也会被配置成单选
                .spanCount(4)                               //图库的列数，默认3列，这个数建议不要太大
                .showGif(true)//default true                //是否展示gif
                .setOnPhotoResultCallback(new PhotoPickConfig.Builder.OnPhotoResultCallback() {
                    @Override
                    public void onResult(PhotoResultBean photoResultBean) {
                        Log.e("MainActivity", "result = " + photoResultBean.getPhotoLists().size());

                    }
                }) //设置数据回调，如果不想在Activity通过onActivityResult()获取回传的数据，可实现此接口
                .build();
    }

    /**
     * 删除图片
     *
     * @param position
     */
    @Override
    public void onMyDetele(int position) {
        mListImg.remove(position);
        mAdapter.notifyDataSetChanged();
        if (mListImg == null && mListImg.size() == 0){
            mRcImg.setVisibility(View.GONE);
            mImg.setVisibility(View.VISIBLE);
        }
    }
}
