package com.haoniu.zzx.app_ts.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.activity.ActivityActivity;
import com.haoniu.zzx.app_ts.model.HomeNewDataInfo;
import com.haoniu.zzx.app_ts.model.HomeNewInfo;
import com.haoniu.zzx.app_ts.utils.GlideImageUtil;
import com.haoniu.zzx.app_ts.view.MyRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import self.androidbase.utils.DensityUtils;

/**
 * Created by zlw on 2018/07/02/上午 10:59
 * 新首页适配器
 */

public class HomeNewAdapter1 extends RecyclerView.Adapter {
    private Context mContext;
    private List<HomeNewInfo> mListData;
    private List<String> mListItemImg;
    private List<String> mListImg;
    private List<HomeNewInfo.OneModeBeam> mListDataNull = new ArrayList<>();
    private final int HAVETITLEZERO = 0;
    private final int HAVETITLEONE = 1;
    private final int HAVETITLETWO = 2;
    private final int HAVETITLETHREE = 3;
    private final int HAVETITLEFOUR = 4;

    private final int HAVETITLEFIVE = 5;
    private final int HAVETITLESIX = 6;
    private final int HAVETITLESEVEN = 7;
    private final int HAVETITLEEIGHT = 8;
    private final int HAVETITLENINE = 9;


    public HomeNewAdapter1(Context mContext, List<HomeNewInfo> mListData) {
        this.mContext = mContext;
        this.mListData = mListData;
        mListImg = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            mListImg.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=849275536,3449568478&fm=27&gp=0.jpg");
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HAVETITLEZERO;
        } else if (position == 1) {
            return HAVETITLEONE;
        } else if (position == 2) {
            return HAVETITLETWO;
        } else if (position == 3) {
            return HAVETITLETHREE;
        } else if (position == 4) {
            return HAVETITLEFOUR;
        } else if (position == 5) {
            return HAVETITLEFIVE;
        } else if (position == 6) {
            return HAVETITLESIX;
        } else if (position == 7) {
            return HAVETITLESEVEN;
        } else if (position == 8) {
            return HAVETITLEEIGHT;
        } else {
            return HAVETITLENINE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HAVETITLEZERO:
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                View view = LayoutInflater.from(mContext).inflate(R.layout.activity_item_home_new, null);
                view.setLayoutParams(lp);
                return new myOneViewHolder(view);
            case HAVETITLEONE:
                LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.item_new_home_two, null);
                view1.setLayoutParams(lp1);
                return new myTwoViewHolder(view1);
            case HAVETITLETWO:
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                View view2 = LayoutInflater.from(mContext).inflate(R.layout.item_new_home_two, null);
                view2.setLayoutParams(lp2);
                return new myTwoViewHolder(view2);
            case HAVETITLETHREE:
                LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                View view3 = LayoutInflater.from(mContext).inflate(R.layout.activity_item_home_new, null);
                view3.setLayoutParams(lp3);
                return new myOneViewHolder(view3);
            case HAVETITLEFOUR:
                LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                View view4 = LayoutInflater.from(mContext).inflate(R.layout.item_new_home_two, null);
                view4.setLayoutParams(lp4);
                return new myTwoViewHolder(view4);
            case HAVETITLEFIVE:
                LinearLayout.LayoutParams lp5 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                View view5 = LayoutInflater.from(mContext).inflate(R.layout.activity_item_home_new, null);
                view5.setLayoutParams(lp5);
                return new myOneViewHolder(view5);
            case HAVETITLESIX:
                LinearLayout.LayoutParams lp6 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                View view6 = LayoutInflater.from(mContext).inflate(R.layout.activity_item_home_new, null);
                view6.setLayoutParams(lp6);
                return new myOneViewHolder(view6);
            case HAVETITLESEVEN:
                LinearLayout.LayoutParams lp7 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                View view7 = LayoutInflater.from(mContext).inflate(R.layout.activity_item_home_new, null);
                view7.setLayoutParams(lp7);
                return new myOneViewHolder(view7);
            case HAVETITLEEIGHT:
                LinearLayout.LayoutParams lp8 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                View view8 = LayoutInflater.from(mContext).inflate(R.layout.activity_item_home_new, null);
                view8.setLayoutParams(lp8);
                return new myOneViewHolder(view8);
            case HAVETITLENINE:
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof myOneViewHolder && position == 0) {
            myOneViewHolder vh = (myOneViewHolder) holder;
            LinearLayoutManager lm = new LinearLayoutManager(mContext);
            lm.setOrientation(LinearLayoutManager.HORIZONTAL);
            vh.mMyRc1.setLayoutManager(lm);
            initRcAdapter(mListData, position, vh.mMyRc1, 0, 0);

            LinearLayoutManager lm1 = new GridLayoutManager(mContext, 2);
            vh.mMyRc2.setLayoutManager(lm1);
            initRcAdapter2(mListData, position, vh.mMyRc2, 2, 1);


            initTitleImg(mListData, position, vh.mImgTitle);
            vh.mLlayoutTag1.setVisibility(View.GONE);
            vh.mLlayoutTag2.setVisibility(View.GONE);
            vh.mLlayoutTag3.setVisibility(View.GONE);

        } else if (holder instanceof myTwoViewHolder && position == 1) {
            myTwoViewHolder vh = (myTwoViewHolder) holder;
            LinearLayoutManager lm = new LinearLayoutManager(mContext);
            lm.setOrientation(LinearLayoutManager.HORIZONTAL);
            vh.mMyRc.setLayoutManager(lm);

            initRcAdapter(mListData, position, vh.mMyRc, 2, 0);

            ImageView[] imgView = {vh.mImgComment1, vh.mImgComment2, vh.mImgComment3, vh.mImgComment4, vh.mImgComment5, vh.mImgComment6, vh.mImgComment7, vh.mImgComment8};
            initImageView(mListData, position, imgView);

            for (int i = 0; i < imgView.length; i++) {
                if (i == 0 || i == 1) {
                    jsPx(imgView[i], 15, 2, 0.75);
                } else if (i > 1 && i < 5) {
                    jsPx(imgView[i], 15, 2, 0.5);
                } else {
                    jsPx(imgView[i], 20, 3, 0.9);
                }
            }


            final List<HomeNewInfo.ActivitycategorymodelBean.ActivitylistBean> info = new ArrayList<>();
            info.addAll(mListData.get(1).getActivitycategorymodel().get(position).getActivitylist());

            initTitleImg(mListData, position, vh.mImgTitle);

            vh.mImgComment1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 0);
                }
            });
            vh.mImgComment2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 1);
                }
            });
            vh.mImgComment3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 2);
                }
            });
            vh.mImgComment4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 3);
                }
            });
            vh.mImgComment5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 4);
                }
            });
            vh.mImgComment6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 5);
                }
            });
            vh.mImgComment7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 6);
                }
            });
            vh.mImgComment8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 7);
                }
            });

        } else if (holder instanceof myTwoViewHolder && position == 2) {
            myTwoViewHolder vh = (myTwoViewHolder) holder;
            LinearLayoutManager lm = new LinearLayoutManager(mContext);
            lm.setOrientation(LinearLayoutManager.HORIZONTAL);
            vh.mMyRc.setLayoutManager(lm);

            initTitleImg(mListData, position, vh.mImgTitle);

            initRcAdapter(mListData, position, vh.mMyRc, 11, 0);

            ImageView[] imgView = {vh.mImgComment1, vh.mImgComment2, vh.mImgComment3, vh.mImgComment4, vh.mImgComment6, vh.mImgComment7, vh.mImgComment8};
            initImageView(mListData, position, imgView);
            vh.mImgComment5.setVisibility(View.GONE);

            for (int i = 0; i < imgView.length; i++) {
                if (i < 4) {
                    jsPx(imgView[i], 15, 2, 0.8);
                } else {
                    jsPx(imgView[i], 20, 3, 0.9);
                }
            }

            final List<HomeNewInfo.ActivitycategorymodelBean.ActivitylistBean> info = new ArrayList<>();
            info.addAll(mListData.get(1).getActivitycategorymodel().get(position).getActivitylist());

            vh.mImgComment1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 0);
                }
            });
            vh.mImgComment2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 1);
                }
            });
            vh.mImgComment3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 2);
                }
            });
            vh.mImgComment4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 3);
                }
            });

            vh.mImgComment6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 4);
                }
            });
            vh.mImgComment7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 5);
                }
            });
            vh.mImgComment8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 6);
                }
            });

        } else if (holder instanceof myOneViewHolder && position == 3) {
            myOneViewHolder vh = (myOneViewHolder) holder;
            LinearLayoutManager lm = new LinearLayoutManager(mContext);
            lm.setOrientation(LinearLayoutManager.HORIZONTAL);
            vh.mMyRc1.setLayoutManager(lm);

            initTitleImg(mListData, position, vh.mImgTitle);

            initRcAdapter(mListData, position, vh.mMyRc1, 19, 0);

            LinearLayoutManager lm1 = new GridLayoutManager(mContext, 3);
            lm1.setOrientation(LinearLayoutManager.VERTICAL);
            vh.mMyRc2.setLayoutManager(lm1);

            initRcAdapter2(mListData, position, vh.mMyRc2, 3, 1);

            vh.mLlayoutTag1.setVisibility(View.GONE);
            vh.mLlayoutTag2.setVisibility(View.GONE);
            vh.mLlayoutTag3.setVisibility(View.GONE);

        } else if (holder instanceof myTwoViewHolder && position == 4) {
            myTwoViewHolder vh = (myTwoViewHolder) holder;
            LinearLayoutManager lm = new LinearLayoutManager(mContext);
            lm.setOrientation(LinearLayoutManager.HORIZONTAL);
            vh.mMyRc.setLayoutManager(lm);

            initTitleImg(mListData, position, vh.mImgTitle);

            initRcAdapter(mListData, position, vh.mMyRc, 21, 0);

            ImageView[] imageViews = {vh.mImgComment1, vh.mImgComment2, vh.mImgComment3, vh.mImgComment4, vh.mImgComment5, vh.mImgComment6, vh.mImgComment7, vh.mImgComment8};
            initImageView(mListData, position, imageViews);

            for (int i = 0; i < imageViews.length; i++) {
                if (i == 0 || i == 1) {
                    jsPx(imageViews[i], 15, 2, 0.75);
                } else if (i > 1 && i < 5) {
                    jsPx(imageViews[i], 15, 2, 0.5);
                } else {
                    jsPx(imageViews[i], 20, 3, 0.9);
                }
            }

            final List<HomeNewInfo.ActivitycategorymodelBean.ActivitylistBean> info = new ArrayList<>();
            info.addAll(mListData.get(1).getActivitycategorymodel().get(position).getActivitylist());

            vh.mImgComment1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 0);
                }
            });
            vh.mImgComment2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 1);
                }
            });
            vh.mImgComment3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 2);
                }
            });
            vh.mImgComment4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 3);
                }
            });
            vh.mImgComment5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 4);
                }
            });
            vh.mImgComment6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 5);
                }
            });
            vh.mImgComment7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 6);
                }
            });
            vh.mImgComment8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 7);
                }
            });


        } else if (holder instanceof myOneViewHolder && position == 5) {
            myOneViewHolder vh = (myOneViewHolder) holder;
            LinearLayoutManager lm = new LinearLayoutManager(mContext);
            lm.setOrientation(LinearLayoutManager.HORIZONTAL);
            vh.mMyRc1.setLayoutManager(lm);

            initTitleImg(mListData, position, vh.mImgTitle);

            initRcAdapter(mListData, position, vh.mMyRc1, 30, 0);

            LinearLayoutManager lm1 = new GridLayoutManager(mContext, 3);
            lm1.setOrientation(LinearLayoutManager.VERTICAL);
            vh.mMyRc2.setLayoutManager(lm1);

            initRcAdapter4(mListData, position, vh.mMyRc2, 3, 1);


            vh.mLlayoutTag1.setVisibility(View.GONE);
            vh.mLlayoutTag2.setVisibility(View.VISIBLE);
            vh.mLlayoutTag3.setVisibility(View.GONE);

            ImageView[] imageViews = {vh.mImgContent3, vh.mImgContent4};
            initImageView7(mListData, position, imageViews);

            for (int i = 0; i < imageViews.length; i++) {
                jsPx(imageViews[i], 15, 2, 0.67);
            }

            final List<HomeNewInfo.ActivitycategorymodelBean.ActivitylistBean> info = new ArrayList<>();
            info.addAll(mListData.get(1).getActivitycategorymodel().get(position).getActivitylist());

            vh.mImgContent3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 6);
                }
            });

            vh.mImgContent4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 7);
                }
            });

        } else if (holder instanceof myOneViewHolder && position == 6) {
            myOneViewHolder vh = (myOneViewHolder) holder;
            LinearLayoutManager lm = new LinearLayoutManager(mContext);
            lm.setOrientation(LinearLayoutManager.HORIZONTAL);
            vh.mMyRc1.setLayoutManager(lm);

            initTitleImg(mListData, position, vh.mImgTitle);

            initRcAdapter(mListData, position, vh.mMyRc1, 34, 0);


            LinearLayoutManager lm1 = new GridLayoutManager(mContext, 3);
            lm1.setOrientation(LinearLayoutManager.VERTICAL);
            vh.mMyRc2.setLayoutManager(lm1);

            initRcAdapter3(mListData, position, vh.mMyRc2, 3, 1);


            vh.mLlayoutTag1.setVisibility(View.VISIBLE);
            vh.mLlayoutTag2.setVisibility(View.GONE);
            vh.mLlayoutTag3.setVisibility(View.GONE);

            ImageView[] imageViews = {vh.mImgContent1, vh.mImgContent2};
            initImageView(mListData, position, imageViews);
            for (int i = 0; i < imageViews.length; i++) {
                jsPx(imageViews[i], 15, 2, 0.67);
            }

            final List<HomeNewInfo.ActivitycategorymodelBean.ActivitylistBean> info = new ArrayList<>();
            info.addAll(mListData.get(1).getActivitycategorymodel().get(position).getActivitylist());

            vh.mImgContent1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 0);
                }
            });

            vh.mImgContent2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 1);
                }
            });


        } else if (holder instanceof myOneViewHolder && position == 7) {
            myOneViewHolder vh = (myOneViewHolder) holder;
            LinearLayoutManager lm = new LinearLayoutManager(mContext);
            lm.setOrientation(LinearLayoutManager.HORIZONTAL);
            vh.mMyRc1.setLayoutManager(lm);

            initTitleImg(mListData, position, vh.mImgTitle);

            initRcAdapter(mListData, position, vh.mMyRc1, 38, 0);


            LinearLayoutManager lm1 = new GridLayoutManager(mContext, 3);
            lm1.setOrientation(LinearLayoutManager.VERTICAL);
            vh.mMyRc2.setLayoutManager(lm1);

            initRcAdapter2(mListData, position, vh.mMyRc2, 3, 1);

            vh.mLlayoutTag1.setVisibility(View.GONE);
            vh.mLlayoutTag2.setVisibility(View.GONE);
            vh.mLlayoutTag3.setVisibility(View.GONE);


        } else if (holder instanceof myOneViewHolder && position == 8) {
            myOneViewHolder vh = (myOneViewHolder) holder;
            LinearLayoutManager lm = new GridLayoutManager(mContext, 4);
            vh.mMyRc1.setLayoutManager(lm);

            initTitleImg(mListData, position, vh.mImgTitle);

            initRcAdapter5(mListData, position, vh.mMyRc1, 4, 1);

            LinearLayoutManager lm1 = new GridLayoutManager(mContext, 3);
            lm1.setOrientation(LinearLayoutManager.VERTICAL);
            vh.mMyRc2.setLayoutManager(lm1);

            initRcAdapter6(mListData, position, vh.mMyRc2, 3, 1);


            vh.mLlayoutTag1.setVisibility(View.GONE);
            vh.mLlayoutTag2.setVisibility(View.VISIBLE);
            vh.mLlayoutTag3.setVisibility(View.VISIBLE);


            ImageView[] imageViews = {vh.mImgComment1, vh.mImgComment2, vh.mImgComment3, vh.mImgComment4, vh.mImgComment5, vh.mImgContent3, vh.mImgContent4};
            initImageView3(mListData, position, imageViews);

            for (int i = 0; i < imageViews.length; i++) {
                if (i == 0 || i == 1) {
                    jsPx(imageViews[i], 15, 2, 0.75);
                } else if (i > 1 && i < 5) {
                    jsPx(imageViews[i], 15, 2, 0.5);
                } else {
                    jsPx(imageViews[i], 15, 2, 0.67);
                }
            }
            final List<HomeNewInfo.ActivitycategorymodelBean.ActivitylistBean> info = new ArrayList<>();
            info.addAll(mListData.get(1).getActivitycategorymodel().get(position).getActivitylist());

            vh.mImgComment1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 8);
                }
            });
            vh.mImgComment2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 9);
                }
            });
            vh.mImgComment3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 10);
                }
            });
            vh.mImgComment4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 11);
                }
            });
            vh.mImgComment5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 12);
                }
            });


            vh.mImgContent3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 19);
                }
            });

            vh.mImgContent4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgClickActivity(info, 20);
                }
            });

        } else {
        }
    }


    @Override
    public int getItemCount() {
        return mListData != null && mListData.size() > 1 ? 9 : 0;
    }

    static class myOneViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_title)
        ImageView mImgTitle;
        @BindView(R.id.my_rc1)
        MyRecyclerView mMyRc1;
        @BindView(R.id.img_content1)
        ImageView mImgContent1;
        @BindView(R.id.img_content2)
        ImageView mImgContent2;
        @BindView(R.id.llayout_tag1)
        LinearLayout mLlayoutTag1;
        @BindView(R.id.img_comment1)
        ImageView mImgComment1;
        @BindView(R.id.img_comment2)
        ImageView mImgComment2;
        @BindView(R.id.img_comment3)
        ImageView mImgComment3;
        @BindView(R.id.img_comment4)
        ImageView mImgComment4;
        @BindView(R.id.img_comment5)
        ImageView mImgComment5;
        @BindView(R.id.llayout_tag3)
        LinearLayout mLlayoutTag3;
        @BindView(R.id.my_rc2)
        MyRecyclerView mMyRc2;
        @BindView(R.id.img_content3)
        ImageView mImgContent3;
        @BindView(R.id.img_content4)
        ImageView mImgContent4;
        @BindView(R.id.llayout_tag2)
        LinearLayout mLlayoutTag2;

        myOneViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class myTwoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_title)
        ImageView mImgTitle;
        @BindView(R.id.my_rc)
        MyRecyclerView mMyRc;
        @BindView(R.id.img_comment1)
        ImageView mImgComment1;
        @BindView(R.id.img_comment2)
        ImageView mImgComment2;
        @BindView(R.id.img_comment3)
        ImageView mImgComment3;
        @BindView(R.id.img_comment4)
        ImageView mImgComment4;
        @BindView(R.id.img_comment5)
        ImageView mImgComment5;
        @BindView(R.id.img_comment6)
        ImageView mImgComment6;
        @BindView(R.id.img_comment7)
        ImageView mImgComment7;
        @BindView(R.id.img_comment8)
        ImageView mImgComment8;

        myTwoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public onMyNewHomeClickListener onMyNewHomeClickListener;

    public interface onMyNewHomeClickListener {
        void onMyClick(int position, int type);

        void onMyClick(int position, int cPosition, int type);
    }

    public void setOnMyNewHomeClickListener(onMyNewHomeClickListener listener) {
        this.onMyNewHomeClickListener = listener;
    }


    /**
     * 整合方法
     *
     * @param listDta
     * @param position
     * @param view
     * @param type
     */
    public void initRcAdapter(List<HomeNewInfo> listDta, final int position, View view, final int clickType, int type) {
        RecyclerView mMyRc = (RecyclerView) view;
        if (listDta.get(1).getActivitycategorymodel() != null && listDta.get(1).getActivitycategorymodel().get(position) != null && listDta.get(1).getActivitycategorymodel().get(position).getCountryactivity() != null) {
            HomeNewH9NewAdapter mAdapter = new HomeNewH9NewAdapter(mContext, listDta.get(1).getActivitycategorymodel().get(position).getCountryactivity(), type);
            mMyRc.setAdapter(mAdapter);
        }
    }

    /**
     * 整合方法
     *
     * @param listDta
     * @param position
     * @param view
     * @param type
     */
    public void initRcAdapter2(List<HomeNewInfo> listDta, final int position, View view, final int clickType, int type) {
        RecyclerView mMyRc2 = (RecyclerView) view;
        if (listDta.get(1) != null && listDta.get(1).getActivitycategorymodel() != null && listDta.get(1).getActivitycategorymodel().get(position) != null && listDta.get(1).getActivitycategorymodel().get(position).getActivitylist() != null) {
            HomeNewH9NewAdapter mAdapter1 = new HomeNewH9NewAdapter(mContext, listDta.get(1).getActivitycategorymodel().get(position).getActivitylist(), type, clickType);
            mMyRc2.setAdapter(mAdapter1);
        }
    }

    /**
     * 整合方法
     *
     * @param listDta
     * @param position
     * @param view
     * @param type
     */
    public void initRcAdapter3(List<HomeNewInfo> listDta, final int position, View view, final int clickType, int type) {
        RecyclerView mMyRc2 = (RecyclerView) view;
        if (listDta.get(1) != null && listDta.get(1).getActivitycategorymodel() != null && listDta.get(1).getActivitycategorymodel().get(position) != null && listDta.get(1).getActivitycategorymodel().get(position).getActivitylist() != null) {

            List<HomeNewInfo.ActivitycategorymodelBean.ActivitylistBean> itemList = new ArrayList<>();
            for (int i = 0; i < listDta.get(1).getActivitycategorymodel().get(position).getActivitylist().size(); i++) {
                if (i > 1) {
                    itemList.add(listDta.get(1).getActivitycategorymodel().get(position).getActivitylist().get(i));
                }
            }
            HomeNewH9NewAdapter mAdapter1 = new HomeNewH9NewAdapter(mContext, itemList, type, clickType);
            mMyRc2.setAdapter(mAdapter1);
        }
    }

    /**
     * 整合方法
     *
     * @param listDta
     * @param position
     * @param view
     * @param type
     */
    public void initRcAdapter4(List<HomeNewInfo> listDta, final int position, View view, final int clickType, int type) {
        RecyclerView mMyRc2 = (RecyclerView) view;
        if (listDta.get(1) != null && listDta.get(1).getActivitycategorymodel() != null && listDta.get(1).getActivitycategorymodel().get(position) != null && listDta.get(1).getActivitycategorymodel().get(position).getActivitylist() != null) {

            List<HomeNewInfo.ActivitycategorymodelBean.ActivitylistBean> itemList = new ArrayList<>();
            for (int i = 0; i < listDta.get(1).getActivitycategorymodel().get(position).getActivitylist().size(); i++) {
                if (i < 6) {
                    itemList.add(listDta.get(1).getActivitycategorymodel().get(position).getActivitylist().get(i));
                }
            }
            HomeNewH9NewAdapter mAdapter1 = new HomeNewH9NewAdapter(mContext, itemList, type, clickType);
            mMyRc2.setAdapter(mAdapter1);
        }
    }


    /**
     * 整合方法
     *
     * @param listDta
     * @param position
     * @param view
     * @param type
     */
    public void initRcAdapter5(List<HomeNewInfo> listDta, final int position, View view, final int clickType, int type) {
        RecyclerView mMyRc2 = (RecyclerView) view;
        if (listDta.get(1) != null && listDta.get(1).getActivitycategorymodel() != null && listDta.get(1).getActivitycategorymodel().get(position) != null && listDta.get(1).getActivitycategorymodel().get(position).getActivitylist() != null) {

            List<HomeNewInfo.ActivitycategorymodelBean.ActivitylistBean> itemList = new ArrayList<>();
            for (int i = 0; i < listDta.get(1).getActivitycategorymodel().get(position).getActivitylist().size(); i++) {
                if (i < 8) {
                    itemList.add(listDta.get(1).getActivitycategorymodel().get(position).getActivitylist().get(i));
                }
            }
            HomeNewH9NewAdapter mAdapter1 = new HomeNewH9NewAdapter(mContext, itemList, type, clickType);
            mMyRc2.setAdapter(mAdapter1);
        }
    }

    /**
     * 整合方法
     *
     * @param listDta
     * @param position
     * @param view
     * @param type
     */
    public void initRcAdapter6(List<HomeNewInfo> listDta, final int position, View view, final int clickType, int type) {
        RecyclerView mMyRc2 = (RecyclerView) view;
        if (listDta.get(1) != null && listDta.get(1).getActivitycategorymodel() != null && listDta.get(1).getActivitycategorymodel().get(position) != null && listDta.get(1).getActivitycategorymodel().get(position).getActivitylist() != null) {

            List<HomeNewInfo.ActivitycategorymodelBean.ActivitylistBean> itemList = new ArrayList<>();
            for (int i = 0; i < listDta.get(1).getActivitycategorymodel().get(position).getActivitylist().size(); i++) {
                if (12 < i && i < listDta.get(1).getActivitycategorymodel().get(position).getActivitylist().size() - 2) {
                    itemList.add(listDta.get(1).getActivitycategorymodel().get(position).getActivitylist().get(i));
                }
            }
            HomeNewH9NewAdapter mAdapter1 = new HomeNewH9NewAdapter(mContext, itemList, type, clickType);
            mMyRc2.setAdapter(mAdapter1);
        }
    }


    /**
     * 设置头部标签
     *
     * @param listDta
     * @param position
     * @param v
     */
    public void initTitleImg(List<HomeNewInfo> listDta, final int position, View v) {
        ImageView mImageView = (ImageView) v;
        if (listDta.get(1).getActivitycategorymodel() != null && listDta.get(1).getActivitycategorymodel().get(position) != null) {
            HomeNewInfo.ActivitycategorymodelBean bean = listDta.get(1).getActivitycategorymodel().get(position);
            GlideImageUtil.normalImg(mContext, bean.getCategoryname(), mImageView);
        }
    }

    /**
     * imageView 加载图片
     *
     * @param listDta
     * @param position
     * @param imgView
     */
    public void initImageView(List<HomeNewInfo> listDta, final int position, ImageView[] imgView) {
        if (listDta.get(1).getActivitycategorymodel() != null && listDta.get(1).getActivitycategorymodel().get(position) != null && listDta.get(1).getActivitycategorymodel().get(position).getActivitylist() != null) {
            List<HomeNewInfo.ActivitycategorymodelBean.ActivitylistBean> info = new ArrayList<>();
            info.addAll(listDta.get(1).getActivitycategorymodel().get(position).getActivitylist());
            if (info != null && info.size() > 0) {
                if (info.size() < imgView.length) {
                    for (int i = 0; i < info.size(); i++) {
                        GlideImageUtil.normalImg(mContext, info.get(i).getThumb(), imgView[i]);
                    }
                } else {
                    for (int i = 0; i < imgView.length; i++) {
                        GlideImageUtil.normalImg(mContext, info.get(i).getThumb(), imgView[i]);
                    }
                }
            }
        }

    }


    private void jsPx(View view, int jgPc, int fenshu, double bili) {
        ImageView img = (ImageView) view;
        float with = DensityUtils.getWidthInPx(mContext);
        float picwith = (with - DensityUtils.dip2px(mContext, jgPc)) / fenshu;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) img.getLayoutParams();
        params.width = (int) picwith;
        params.height = (int) (picwith * bili);
        img.setLayoutParams(params);
    }


    /**
     * imageView 加载图片
     *
     * @param listDta
     * @param position
     * @param imgView
     */
    public void initImageView1(List<HomeNewInfo> listDta, final int position, ImageView[] imgView) {
        if (listDta.get(1).getActivitycategorymodel() != null && listDta.get(1).getActivitycategorymodel().get(position) != null && listDta.get(1).getActivitycategorymodel().get(position).getActivitylist() != null) {
            List<HomeNewInfo.ActivitycategorymodelBean.ActivitylistBean> info = new ArrayList<>();
            info.addAll(listDta.get(1).getActivitycategorymodel().get(position).getActivitylist());
            if (info != null && info.size() > 0) {
                if (info.size() > imgView.length) {
                    if (info.size() > 3) {
                        for (int i = 0; i < imgView.length; i++) {
                            GlideImageUtil.normalImg(mContext, info.get(info.size() - 3 + i).getThumb(), imgView[i]);
                        }
                    }

                } else {
                    if (info.size() > 3) {
                        for (int i = 0; i < info.size(); i++) {
                            GlideImageUtil.normalImg(mContext, info.get(info.size() - 3 + i).getThumb(), imgView[i]);
                        }
                    }
                }
            }
        }
    }


    /**
     * imageView 加载图片
     *
     * @param listDta
     * @param position
     * @param imgView
     */
    public void initImageView7(List<HomeNewInfo> listDta, final int position, ImageView[] imgView) {
        if (listDta.get(1).getActivitycategorymodel() != null && listDta.get(1).getActivitycategorymodel().get(position) != null && listDta.get(1).getActivitycategorymodel().get(position).getActivitylist() != null) {
            List<HomeNewInfo.ActivitycategorymodelBean.ActivitylistBean> info = new ArrayList<>();
            info.addAll(listDta.get(1).getActivitycategorymodel().get(position).getActivitylist());
            if (info != null && info.size() > 0) {
                if (info.size() > imgView.length) {
                    for (int i = 0; i < info.size(); i++) {
                        if (i > 5) {
                            GlideImageUtil.normalImg(mContext, info.get(i).getThumb(), imgView[i - 6]);
                        }
                    }
                }
            }
        }
    }


    /**
     * imageView 加载图片
     *
     * @param listDta
     * @param position
     * @param imgView
     */
    public void initImageView3(List<HomeNewInfo> listDta, final int position, ImageView[] imgView) {
        if (listDta.get(1).getActivitycategorymodel() != null && listDta.get(1).getActivitycategorymodel().get(position) != null && listDta.get(1).getActivitycategorymodel().get(position).getActivitylist() != null) {
            List<HomeNewInfo.ActivitycategorymodelBean.ActivitylistBean> info = new ArrayList<>();
            for (int i = 0; i < listDta.get(1).getActivitycategorymodel().get(position).getActivitylist().size(); i++) {
                if (listDta.get(1).getActivitycategorymodel().get(position).getActivitylist().size() > 8) {
                    if (7 < i && i < listDta.get(1).getActivitycategorymodel().get(position).getActivitylist().size() - 8) {
                        info.add(listDta.get(1).getActivitycategorymodel().get(position).getActivitylist().get(i));
                    }
                }
            }
            if (listDta.get(1).getActivitycategorymodel().get(position).getActivitylist().size() > 2) {
                info.add(listDta.get(1).getActivitycategorymodel().get(position).getActivitylist().get(listDta.get(1).getActivitycategorymodel().get(position).getActivitylist().size() - 2));
                info.add(listDta.get(1).getActivitycategorymodel().get(position).getActivitylist().get(listDta.get(1).getActivitycategorymodel().get(position).getActivitylist().size() - 1));
            }

            if (info != null && info.size() > 0) {
                if (info.size() < imgView.length) {
                    for (int i = 0; i < info.size(); i++) {
                        if (info != null && info.size() > 0) {
                            GlideImageUtil.normalImg(mContext, info.get(i).getThumb(), imgView[i]);
                        }
                    }
                } else {
                    for (int i = 0; i < imgView.length; i++) {
                        if (info != null && info.size() > 0) {
                            GlideImageUtil.normalImg(mContext, info.get(i).getThumb(), imgView[i]);
                        }
                    }
                }
            }
        }
    }

    /**
     * ingView 点击事件
     *
     * @param info
     * @param index
     */
    private void imgClickActivity(List<HomeNewInfo.ActivitycategorymodelBean.ActivitylistBean> info, int index) {
        if (info != null && info.size() > index) {
            mContext.startActivity(new Intent(mContext, ActivityActivity.class)
                    .putExtra("shareUrl", info.get(index).getLink())//TODO 暂无分享连接
                    .putExtra("flag", 1).putExtra("id", info.get(index).getId()).putExtra("title", info.get(index).getBannername())
                    .putExtra("desc", info.get(index).getDesc())
                    .putExtra("thumb", info.get(index).getThumb()));
        }
    }
}
