package com.haoniu.zzx.app_ts.adapter;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.haoniu.zzx.app_ts.model.MainClassifyModel;
import com.haoniu.zzx.app_ts.model.NormalModel;
import com.haoniu.zzx.app_ts.myinterface.CommonEnity;
import com.haoniu.zzx.app_ts.utils.PreferenceUtils;
import com.haoniu.zzx.app_ts.utils.QiNiuGlideUtils;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.haoniu.zzx.app_ts.utils.ToastUtils;
import com.lzy.okgo.model.Response;
import com.squareup.picasso.Picasso;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2017/9/27 0027.
 */

public class GoodsBottomItemAdapter extends BaseQuickAdapter<NormalModel, BaseViewHolder> {
    private List<MainClassifyModel> classifyModels;
    private List<TextView> tabTextList;

    // , List<MainClassifyModel> classifyModels
    public GoodsBottomItemAdapter(List<NormalModel> data) {
        super(R.layout.adapter_item_bottom_good, data);
        this.classifyModels = classifyModels;
        tabTextList = new ArrayList<>();
    }

    protected boolean isScrolling = false;

    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final NormalModel normalModel) {
        if (normalModel.getTitle() != null) {
            String msg = normalModel.getTitle();
            if (msg.contains("&#039;")) {
                msg = msg.replaceAll("&#039;", "'");
            }
            baseViewHolder.setText(R.id.tvGoodName, msg);
        }
        baseViewHolder.setText(R.id.tvGoodPrice, "￥" + normalModel.getMinprice());
        baseViewHolder.setText(R.id.tvGoodCountry, normalModel.getCountry());

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
        if (!StringUtils.isEmpty(normalModel.getThumb()) && !isScrolling) {
            Glide.with(mContext)
                    .load(normalModel.getThumb())
//                    .load(QiNiuGlideUtils.boundary480(normalModel.getThumb()))
                    .error(R.mipmap.img_square)
                    .into(ivGoodImg);
        } else {
            ivGoodImg.setBackgroundResource(R.mipmap.img_square);
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

//    @Override
//    public long getHeaderId(int position) {
//        return 1;
//    }
//
//    @Override
//    public MyHeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_fix_head, parent, false);
//        return new MyHeaderViewHolder(itemView);
//    }

//    @Override
//    public void onBindHeaderViewHolder(final MyHeaderViewHolder holder, int position) {
//        holder.llCursor.setWeightSum(classifyModels.size());
//        final int width = (int) (DensityUtils.getWidthInPx(mContext) / classifyModels.size());
//        int heigth = DensityUtils.dip2px(mContext, 49);
//        for (int i = 0; i < classifyModels.size(); i++) {
//            TextView textView = new TextView(mContext);
//            textView.setWidth(width);
//            textView.setHeight(heigth);
//            textView.setGravity(Gravity.CENTER);
//            try {
//                textView.setTextColor(mContext.getResources().getColor(R.color.colorGrayText88));
//            } catch (Exception e) {
//
//            }
//            textView.setText(classifyModels.get(i).getBannername() == null ? "" : classifyModels.get(i).getBannername());
//            tabTextList.add(textView);
//            holder.llFixHeadTop.addView(textView);
//            final int finalI = i;
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (finalI == selIndex) {
//                        return;
//                    } else if (headSelIndex != null) {
//                        selIndex = finalI;
//                        headSelIndex.SelIndex(finalI);
//                        TranslateAnimation anim = new TranslateAnimation(fromX, selIndex * width, 0, 0);
//                        anim.setFillAfter(true);//设置动画结束时停在动画结束的位置
//                        anim.setDuration(100);
//                        //保存动画结束时游标的位置,作为下次滑动的起点
//                        fromX = selIndex * width;
//                        holder.vCursor.startAnimation(anim);
//                        //设置Tab切换颜色
//                        for (int i = 0; i < tabTextList.size(); i++) {
//                            try {
//                                tabTextList.get(i).setTextColor(i == selIndex ? mContext.getResources().getColor(R.color.colorRed) :
//                                        mContext.getResources().getColor(R.color.colorGrayText88));
//                            } catch (Exception e) {
//                            }
//                        }
//                    }
//                }
//            });
//        }
//    }

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

    private int fromX;
    private HeadSelIndex headSelIndex;

    public void setHeadSelIndex(HeadSelIndex headSelIndex) {
        this.headSelIndex = headSelIndex;
    }

    private int selIndex;

    public interface HeadSelIndex {
        void SelIndex(int position);
    }

    public static class MyHeaderViewHolder extends BaseViewHolder {

        LinearLayout llFixHead, llCursor, llFixHeadTop;
        View vCursor;

        public MyHeaderViewHolder(View itemView) {
            super(itemView);
            vCursor = itemView.findViewById(R.id.v_tab_cursor);
            llCursor = (LinearLayout) itemView.findViewById(R.id.llCursor);
            llFixHead = (LinearLayout) itemView.findViewById(R.id.llFixHead);
            llFixHeadTop = (LinearLayout) itemView.findViewById(R.id.llFixHeadTop);
        }
    }

}
