package com.haoniu.zzx.app_ts.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.TestActivity;
import com.haoniu.zzx.app_ts.adapter.GoodsBottomItemAdapter;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.HttpUtils;
import com.haoniu.zzx.app_ts.http.JsonCallback;
import com.haoniu.zzx.app_ts.model.BigModel;
import com.haoniu.zzx.app_ts.model.MainClassifyModel;
import com.haoniu.zzx.app_ts.model.NormalModel;
import com.haoniu.zzx.app_ts.utils.ToastUtils;
import com.haoniu.zzx.app_ts.view.HomepageHeadView;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import self.androidbase.utils.DensityUtils;

/**
 * 首页
 */

public class HomeFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.llFixHeadTop)
    LinearLayout llFixHeadTop;
    @BindView(R.id.llFixHead)
    LinearLayout llFixHead;
    @BindView(R.id.v_tab_cursor)
    View vTabCursor;
    @BindView(R.id.llCursor)
    LinearLayout llCursor;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.mRefreshLayout)
    BGARefreshLayout mRefreshLayout;
    //    @BindView(R.id.swipe_container)
//    SwipeRefreshLayout swipeContainer;

    private boolean isFrist = true;
    private LinearLayout llFixHeadItem, llCursorItem, llFixHeadTopItem;
    private View vTabCursorItem;

    private BigModel bigModel;
    private GoodsBottomItemAdapter bottomItemAdapter;
    private int page = 1;
    private List<NormalModel> normalModels;
    private List<MainClassifyModel> classifyModels;

    private GridLayoutManager gridLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.frg_home;
    }

    @Override
    protected void initData() {
        super.initData();
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mContext.startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestSearchGeneral));
                startActivity(TestActivity.class);
            }
        });
        normalModels = new ArrayList<>();
        classifyModels = new ArrayList<>();
        recycleView.setFocusable(true);
        recycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //最后一个可见的ITEM
//                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                View view = gridLayoutManager.findViewByPosition(1);
                if (view != null) {
                    if (view.getTop() <= DensityUtils.dip2px(mContext, 50)) {
                        llFixHeadItem.setVisibility(View.INVISIBLE);
                        llFixHead.setVisibility(View.VISIBLE);
                    } else {
                        mHandler.sendEmptyMessageDelayed(310, 0);
                        llFixHeadItem.setVisibility(View.VISIBLE);
                        llFixHead.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        mRefreshLayout.setDelegate(this);
        gridLayoutManager = new GridLayoutManager(mContext, 2);
        recycleView.setLayoutManager(gridLayoutManager);
        bottomItemAdapter = new GoodsBottomItemAdapter(normalModels );
        bottomItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ToastUtils.showTextToast(mContext, i + "  ---");
            }
        });
        recycleView.setAdapter(bottomItemAdapter);
        requestMsg();
        llFixHead.requestFocus();
    }

    private void initView() {
        bottomItemAdapter.removeAllHeaderView();
        HomepageHeadView headView = new HomepageHeadView(mContext, bigModel);
        llFixHeadItem = (LinearLayout) headView.findViewById(R.id.llFixHead1);
        llCursorItem = (LinearLayout) headView.findViewById(R.id.llCursor1);
        llFixHeadTopItem = (LinearLayout) headView.findViewById(R.id.llFixHeadTop1);
        vTabCursorItem = headView.findViewById(R.id.v_tab_cursor1);
        bottomItemAdapter.addHeaderView(headView);
    }

    private void requestMsg() {
        HttpUtils.requestGet(mContext, AppConfig.requestHomeMsg, null, new JsonCallback<String>(mContext, "加载中...") {
            @Override
            public void onSuccess(Response<String> response) {
                if (response.body() != null) {
                    bigModel = JSON.parseObject(response.body(), BigModel.class);
                    requestClassify();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
//                swipeContainer.setRefreshing(false);
            }
        });
    }

    private int selIndex;
    private List<TextView> tabTextList, tabTextListItem;

    private void initClassify() {
        initView();
        tabTextList = new ArrayList<>();
        tabTextListItem = new ArrayList<>();
        llCursor.setWeightSum(classifyModels.size());
        llCursorItem.setWeightSum(classifyModels.size());
        int width = (int) (DensityUtils.getWidthInPx(mContext) / classifyModels.size());
        int heigth = DensityUtils.dip2px(mContext, 49);
        for (int i = 0; i < classifyModels.size(); i++) {
            TextView textView = new TextView(mContext);
            textView.setWidth(width);
            textView.setHeight(heigth);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(R.color.colorGrayText88));
            textView.setText(classifyModels.get(i).getBannername());
            tabTextList.add(textView);
            llFixHeadTop.addView(textView);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == selIndex) {
                        return;
                    } else {
                        normalModels.clear();
                        page = 1;
                        requestGoods(finalI);
                    }
                }
            });
        }
        initBottomView();
        tabTextList.get(0).setTextColor(getResources().getColor(R.color.colorRed));
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        requestGoods(0);
//        mRefreshLayout.beginRefreshing();
    }

    private void initBottomView() {
        int width = (int) (DensityUtils.getWidthInPx(mContext) / classifyModels.size());
        int heigth = DensityUtils.dip2px(mContext, 49);
        for (int i = 0; i < classifyModels.size(); i++) {
            TextView textView = new TextView(mContext);
            textView.setWidth(width);
            textView.setHeight(heigth);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(R.color.colorGrayText88));
            textView.setText(classifyModels.get(i).getBannername());
            tabTextListItem.add(textView);
            llFixHeadTopItem.addView(textView);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == selIndex) {
                        return;
                    } else {
                        normalModels.clear();
                        page = 1;
                        requestGoods(finalI);
                    }
                }
            });
        }
    }

    private void requestClassify() {
        HttpUtils.requestGet(mContext, AppConfig.requestMainClassify, null, new JsonCallback<List<MainClassifyModel>>(mContext, "加载中...") {
            @Override
            public void onSuccess(Response<List<MainClassifyModel>> response) {
                if (response.body() != null) {
                    classifyModels.addAll(response.body());
                    initClassify();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    private void requestGoods(final int index) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        HttpUtils.requestGet(mContext, classifyModels.get(index).getEachUrl(), map, new JsonCallback<List<NormalModel>>(mContext, "加载中...") {
            @Override
            public void onSuccess(Response<List<NormalModel>> response) {
                selIndex = index;
                if (response.body() != null && response.body().size() > 0) {
                    normalModels.addAll(response.body());
                }
                initTextViewUI();
            }


            @Override
            public void onFinish() {
                super.onFinish();
                mRefreshLayout.endLoadingMore();
//                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onError(Response<List<NormalModel>> response) {
                super.onError(response);
                if (page > 1) {
                    page--;
                }
            }
        });
    }

    private float fromX;

    private void initTextViewUI() {
        scrollCursor();
        bottomItemAdapter.notifyDataSetChanged();
        if (page == 1 && normalModels.size() > 0 && !isFrist) {
//            recycleView.scrollToPosition(1);
            gridLayoutManager.scrollToPosition(2);
//            gridLayoutManager.scrollToPositionWithOffset(2, 0);
        }
//        else if (isFrist) {
//            recycleView.scrollToPosition(0);
//        }
//        recycleView.scrollTo(0, (int) y);
//        recycleView.smoothScrollToPosition(0);

        isFrist = false;
    }

    /**
     * 滑动游标
     */
    private void scrollCursor() {
        int width = (int) (DensityUtils.getWidthInPx(mContext) / classifyModels.size());
        TranslateAnimation anim = new TranslateAnimation(fromX, selIndex * width, 0, 0);
        anim.setFillAfter(true);//设置动画结束时停在动画结束的位置
        anim.setDuration(100);
        //保存动画结束时游标的位置,作为下次滑动的起点
        vTabCursor.startAnimation(anim);
        mHandler.sendEmptyMessageDelayed(310, 0);
        //设置Tab切换颜色
        for (int i = 0; i < tabTextList.size(); i++) {
            tabTextList.get(i).setTextColor(i == selIndex ? getResources().getColor(R.color.colorRed) : getResources().getColor(R.color.colorGrayText88));
            tabTextListItem.get(i).setTextColor(i == selIndex ? getResources().getColor(R.color.colorRed) : getResources().getColor(R.color.colorGrayText88));
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 310) {
                int width = (int) (DensityUtils.getWidthInPx(mContext) / classifyModels.size());
                TranslateAnimation anim1 = new TranslateAnimation(fromX, selIndex * width, 0, 0);
                anim1.setFillAfter(true);//设置动画结束时停在动画结束的位置
                anim1.setDuration(100);
                fromX = selIndex * width;
                vTabCursorItem.startAnimation(anim1);
            }
        }
    };

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mRefreshLayout.endRefreshing();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        page++;
        requestGoods(selIndex);
        return true;
    }
}
