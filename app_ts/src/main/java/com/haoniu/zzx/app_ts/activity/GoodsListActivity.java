package com.haoniu.zzx.app_ts.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.adapter.GoodsCenAdapter;
import com.haoniu.zzx.app_ts.adapter.GoodsLeftAdapter;
import com.haoniu.zzx.app_ts.adapter.GoodsListItemAdapter;
import com.haoniu.zzx.app_ts.adapter.GoodsRightAdapter;
import com.haoniu.zzx.app_ts.adapter.ItemSelectAdapter;
import com.haoniu.zzx.app_ts.adapter.ItemTextAdapter;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.HttpUtils;
import com.haoniu.zzx.app_ts.http.JsonCallback;
import com.haoniu.zzx.app_ts.model.CategoryModel;
import com.haoniu.zzx.app_ts.model.CotegoryListModel;
import com.haoniu.zzx.app_ts.model.GoodsListModel;
import com.haoniu.zzx.app_ts.model.ItemTextModel;
import com.haoniu.zzx.app_ts.model.SelectModel;
import com.haoniu.zzx.app_ts.utils.ToastUtils;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 商品列表
 */
public class GoodsListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.edSearch)
    EditText edSearch;
    @BindView(R.id.tvGeneral)
    TextView tvGeneral;
    @BindView(R.id.tvSales)
    TextView tvSales;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.ivPriceTop)
    ImageView ivPriceTop;
    @BindView(R.id.ivPriceBottom)
    ImageView ivPriceBottom;
    @BindView(R.id.llPrice)
    LinearLayout llPrice;
    @BindView(R.id.llNoData)
    LinearLayout llNoData;
    @BindView(R.id.tvScreen)
    TextView tvScreen;
    @BindView(R.id.ivScreen)
    ImageView ivScreen;
    @BindView(R.id.llScreen)
    LinearLayout llScreen;
    @BindView(R.id.llScreenChoose)
    LinearLayout llScreenChoose;
    @BindView(R.id.recycleViewText)
    RecyclerView recycleViewText;
    @BindView(R.id.itemRecyclerView)
    RecyclerView selRecyclerView;
    @BindView(R.id.mRefreshLayout)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.recycleViewGoods)
    RecyclerView recycleViewGoods;
    @BindView(R.id.leftRecyclerView)
    RecyclerView leftRecyclerView;
    @BindView(R.id.cenRecyclerView)
    RecyclerView cenRecyclerView;
    @BindView(R.id.rightRecyclerView)
    RecyclerView rightRecyclerView;

    private String id;
    private int page = 1;
    private boolean shop;
    /**
     * 分类
     */
    private ItemTextAdapter itemTextAdapter;
    private int selIndexText = -1;
    private List<ItemTextModel> itemTextModels;
    /**
     * 选择
     */
    private ItemSelectAdapter selectAdapter;
    private List<SelectModel> selectModels;

    /**
     * 商品列表
     */
    private GoodsListItemAdapter itemAdapter;
    private List<GoodsListModel> models;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_goods_list);
    }

    @Override
    protected void initView() {
        itemTextModels = new ArrayList<>();
        itemTextAdapter = new ItemTextAdapter(itemTextModels);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleViewText.setLayoutManager(layoutManager);
        recycleViewText.setAdapter(itemTextAdapter);
        itemTextAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                page = 1;
                if (selIndexText == -1) {
                    selIndexText = i;
                    itemTextModels.get(i).setCheck(true);
                    itemTextAdapter.notifyDataSetChanged();
                    models.clear();
                    requestGoodsList(selIndex, 0);
                } else if (selIndexText != i) {
                    itemTextModels.get(selIndexText).setCheck(false);
                    selIndexText = i;
                    itemTextModels.get(selIndexText).setCheck(true);
                    itemTextAdapter.notifyDataSetChanged();
                    models.clear();
                    requestGoodsList(selIndex, 0);
                }
            }
        });
        initSelect();
        initDialogRecyclerView();
        requestTextMsg(id);

        models = new ArrayList<>();
        itemAdapter = new GoodsListItemAdapter(models);
        mRefreshLayout.setDelegate(this);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        recycleViewGoods.setLayoutManager(gridLayoutManager);
        recycleViewGoods.setAdapter(itemAdapter);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();
    }

    private void initSelect() {
        selectModels = new ArrayList<>();
        selectModels.add(new SelectModel("推荐商品"));
        selectModels.add(new SelectModel("新品上市"));
        selectModels.add(new SelectModel("热卖商品"));
        selectModels.add(new SelectModel("促销商品"));
        selectModels.add(new SelectModel("卖家包邮"));
        selectModels.add(new SelectModel("限时抢购"));
        selectAdapter = new ItemSelectAdapter(selectModels);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        selRecyclerView.setLayoutManager(gridLayoutManager);
        selectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                selectModels.get(i).setCheck(!selectModels.get(i).isCheck());
                selectAdapter.notifyDataSetChanged();
            }
        });
        selRecyclerView.setAdapter(selectAdapter);
    }

    /**
     * 左  中  右
     */
    private GoodsLeftAdapter leftAdapter;
    private List<CategoryModel> leftModels;
    private int indexLeftSel = -1;
    private int indexCenSel = -1;
    private int indexRightSel = -1;
    private List<CotegoryListModel> cenModels;
    private GoodsCenAdapter cenAdapter;
    private List<CotegoryListModel.ListBean> rightModels;
    private GoodsRightAdapter rightAdapter;

    private void initDialogRecyclerView() {
        leftModels = new ArrayList<>();
        leftAdapter = new GoodsLeftAdapter(leftModels);
        leftRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        leftRecyclerView.setAdapter(leftAdapter);
        leftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (indexLeftSel != i) {
                    if (indexLeftSel == -1) {
                        indexLeftSel = i;
                    }
                    leftModels.get(indexLeftSel).setCheck(false);
                    indexLeftSel = i;
                    leftModels.get(indexLeftSel).setCheck(true);
                    cenModels.clear();
                    indexCenSel = -1;
                    rightModels.clear();
                    indexRightSel = -1;
                    leftAdapter.notifyDataSetChanged();
                    cenAdapter.notifyDataSetChanged();
                    rightAdapter.notifyDataSetChanged();
                    requestGlobalDetial(i);
                }
            }
        });

        cenModels = new ArrayList<>();
        cenAdapter = new GoodsCenAdapter(cenModels);
        cenRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        cenRecyclerView.setAdapter(cenAdapter);
        cenAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (indexCenSel != i) {
                    if (indexCenSel == -1) {
                        indexCenSel = i;
                    }
                    cenModels.get(indexCenSel).setCheck(false);
                    indexCenSel = i;
                    cenModels.get(indexCenSel).setCheck(true);
                    rightModels.clear();
                    indexRightSel = -1;
                    rightModels.addAll(cenModels.get(i).getList());
                    cenAdapter.notifyDataSetChanged();
                    rightAdapter.notifyDataSetChanged();
                }
            }
        });


        rightModels = new ArrayList<>();
        rightAdapter = new GoodsRightAdapter(rightModels);
        rightRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        rightRecyclerView.setAdapter(rightAdapter);
        rightAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (indexRightSel != i) {
                    if (indexRightSel == -1) {
                        indexRightSel = i;
                    }
                    rightModels.get(indexRightSel).setCheck(false);
                    indexRightSel = i;
                    rightModels.get(indexRightSel).setCheck(true);
                    rightAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void initLogic() {
//        edSearch.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
//                    if (!checkEmpty(edSearch)) {
//                        requestGoodsList(selIndex);
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });
//        hideSoftInput(edSearch);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        id = extras.getString("id");
        shop = extras.getBoolean("shop");
    }

    @OnClick({R.id.llGlobalBack, R.id.tvGeneral, R.id.tvSales, R.id.llPrice, R.id.llScreen, R.id.tvCancel,
            R.id.tvConfirm, R.id.llScreenChoose, R.id.tvSearch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llGlobalBack:
                finish();
                break;
            case R.id.tvGeneral:
                selIndex = 0;
                resertColor();
                tvGeneral.setTextColor(getResources().getColor(R.color.colorRed));
                page = 1;
                models.clear();
                requestGoodsList(0, 0);
                break;
            case R.id.tvSales:
                selIndex = 1;
                resertColor();
                tvSales.setTextColor(getResources().getColor(R.color.colorRed));
                page = 1;
                models.clear();
                requestGoodsList(1, 0);
                break;
            case R.id.llPrice:
                resertColor();
                tvPrice.setTextColor(getResources().getColor(R.color.colorRed));
                if (selIndex == 2) {
                    if (!isTop) {
                        ivPriceTop.setBackgroundResource(R.mipmap.img_totop_r);
                        ivPriceBottom.setBackgroundResource(R.mipmap.img_tobottom_b);
                    } else {
                        ivPriceTop.setBackgroundResource(R.mipmap.img_totop_b);
                        ivPriceBottom.setBackgroundResource(R.mipmap.img_tobottom_r);
                    }
                    isTop = !isTop;
                } else {
                    ivPriceTop.setBackgroundResource(R.mipmap.img_totop_r);
                    ivPriceBottom.setBackgroundResource(R.mipmap.img_tobottom_b);
                    isTop = true;
                }
                selIndex = 2;
                page = 1;
                models.clear();
                requestGoodsList(2, 0);
                break;
            case R.id.llScreen:
                selIndex = 3;
                resertColor();
                tvScreen.setTextColor(getResources().getColor(R.color.colorRed));
                ivScreen.setBackgroundResource(R.mipmap.img_screen_r);
                llScreenChoose.setVisibility(View.VISIBLE);
                if (leftModels.size() == 0) {
                    requestLeft();
                }
                break;
            case R.id.tvCancel:
                llScreenChoose.setVisibility(View.GONE);
                cenModels.clear();
                rightModels.clear();
                indexCenSel = -1;
                indexRightSel = -1;
                if (indexLeftSel != -1) {
                    leftModels.get(indexLeftSel).setCheck(false);
                }
                indexLeftSel = -1;
                for (int i = 0; i < selectModels.size(); i++) {
                    selectModels.get(i).setCheck(false);
                }
                selectAdapter.notifyDataSetChanged();
                leftAdapter.notifyDataSetChanged();
                cenAdapter.notifyDataSetChanged();
                rightAdapter.notifyDataSetChanged();
                models.clear();
                page = 1;
                requestGoodsList(3, 0);
                break;
            case R.id.tvConfirm:
                if (indexRightSel != -1) {
                    selIndexText = -1;
                    itemTextModels.clear();
                    requestTextMsg(rightModels.get(indexRightSel).getId());
                } else {
                    selIndexText = -1;
                    recycleViewText.setVisibility(View.GONE);
                }
                llScreenChoose.setVisibility(View.GONE);
                models.clear();
                page = 1;
                requestGoodsList(3, 0);
                break;
            case R.id.llScreenChoose:
                llScreenChoose.setVisibility(View.GONE);
                break;
            case R.id.tvSearch:
                if (edSearch.getText().toString() != null && !edSearch.getText().toString().equals("")) {
                    isSearch = true;
                    models.clear();
                    requestGoodsList(selIndex, 1);
                    isSearch = false;
                    hideSoftInput(edSearch);
                } else {
                    ToastUtils.showTextToast(mContext, "请输入您要搜索的商品名称");
                }
                break;
        }
    }

    private boolean isSearch;

    private void requestGoodsList(final int index, int type) {//type 为1 是客户要求搜索改为全局搜索 0，为之前的逻辑代码
        Map<String, Object> map = new HashMap<>();
        if (isSearch)
            page = 1;
        map.put("page", page);
        map.put("pagesize", 20);
        if (type == 1) {
            map.put("cate", "0");
        } else {
            if (shop) {
                map.put("shopid", id);
            } else {
                map.put("cate", id);
            }
            if (index == 1) {//销量
                map.put("order", "sales");
            } else if (index == 2) {//价格
                map.put("order", "minprice");
                if (isTop) {
                    map.put("by", "asc");
                } else {
                    map.put("by", "desc");
                }
            }
        }

        if (!checkEmpty(edSearch)) {
            map.put("keywords", edSearch.getText().toString());
        }
        if (type != 1) {
            if (selectModels.get(0).isCheck()) {
                map.put("isrecommand", "1");
            }
            if (selectModels.get(1).isCheck()) {
                map.put("ishot", "1");
            }
            if (selectModels.get(2).isCheck()) {
                map.put("isnew", "1");
            }
            if (selectModels.get(3).isCheck()) {
                map.put("isdiscount", "1");
            }
            if (selectModels.get(4).isCheck()) {
                map.put("issendfree", "1");
            }
            if (selectModels.get(5).isCheck()) {
                map.put("istime", "1");
            }
            if (indexLeftSel != -1) {
                map.put("cate", leftModels.get(indexLeftSel).getId());
            }
            if (indexCenSel != -1) {
                map.put("cate", cenModels.get(indexCenSel).getId());
            }
            if (indexRightSel != -1) {
                map.put("cate", rightModels.get(indexRightSel).getId());
            }
            if (selIndexText != -1) {
                map.put("cate", itemTextModels.get(selIndexText).getId());
            }
        }
        HttpUtils.requestGet(mContext, AppConfig.requestGoodsList, map, new JsonCallback<List<GoodsListModel>>(mContext) {
            @Override
            public void onSuccess(Response<List<GoodsListModel>> response) {
                if (response.body() != null && response.body().size() > 0 && selIndex == index) {
                    models.addAll(response.body());
                    itemAdapter.notifyDataSetChanged();
                }
                if (page == 1 && models.size() > 0) {
                    recycleViewGoods.smoothScrollToPosition(0);
                }
                if (models.size() > 0) {
                    mRefreshLayout.setVisibility(View.VISIBLE);
                    llNoData.setVisibility(View.GONE);
                } else {
                    mRefreshLayout.setVisibility(View.GONE);
                    llNoData.setVisibility(View.VISIBLE);
                }
                if (response.body() == null || response.body().size() == 0) {
                    page--;
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
            }

            @Override
            public void onError(Response<List<GoodsListModel>> response) {
                super.onError(response);
                itemAdapter.notifyDataSetChanged();
                if (page < 1) {
                    page++;
                }
                if (models.size() > 0) {
                    mRefreshLayout.setVisibility(View.VISIBLE);
                    llNoData.setVisibility(View.GONE);
                } else {
                    mRefreshLayout.setVisibility(View.GONE);
                    llNoData.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * left
     */
    private void requestLeft() {
        HttpUtils.requestGet(mContext, AppConfig.requestCategory, null, new JsonCallback<List<CategoryModel>>(mContext, "加载中...") {
            @Override
            public void onSuccess(Response<List<CategoryModel>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    leftModels.addAll(response.body());
                    leftAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * center
     */
    private void requestGlobalDetial(int position) {
        cenModels.clear();
        Map<String, Object> map = new HashMap<>();
        map.put("cateid", leftModels.get(position).getId());
        HttpUtils.requestPosts(mContext, AppConfig.requestCategoryList, map, new JsonCallback<List<CotegoryListModel>>(mContext, "加载中...") {
            @Override
            public void onSuccess(Response<List<CotegoryListModel>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    cenModels.addAll(response.body());
                }
                cenAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 分类
     */
    private void requestTextMsg(String id) {
        HttpUtils.requestGet(mContext, AppConfig.requestCateList + id, null, new JsonCallback<List<ItemTextModel>>(mContext) {
            @Override
            public void onSuccess(Response<List<ItemTextModel>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    recycleViewText.setVisibility(View.VISIBLE);
                    itemTextModels.addAll(response.body());
                    itemTextAdapter.notifyDataSetChanged();
                } else {
                    recycleViewText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Response<List<ItemTextModel>> response) {
                super.onError(response);
                recycleViewText.setVisibility(View.GONE);
            }
        });

    }


    private boolean isTop = true;
    private int selIndex = 0;

    private void resertColor() {
        tvGeneral.setTextColor(getResources().getColor(R.color.colorGrayText88));
        tvSales.setTextColor(getResources().getColor(R.color.colorGrayText88));
        tvPrice.setTextColor(getResources().getColor(R.color.colorGrayText88));
        ivPriceTop.setBackgroundResource(R.mipmap.img_totop_b);
        ivPriceBottom.setBackgroundResource(R.mipmap.img_tobottom_b);
        tvScreen.setTextColor(getResources().getColor(R.color.colorGrayText88));
        ivScreen.setBackgroundResource(R.mipmap.img_screen_b);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        models.clear();
        if (!TextUtils.isEmpty(edSearch.getText().toString().trim())) {
            requestGoodsList(selIndex, 1);
        } else {
            requestGoodsList(selIndex, 0);
        }

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        page++;
        if (!TextUtils.isEmpty(edSearch.getText().toString().trim())) {
            requestGoodsList(selIndex, 1);
        } else {
            requestGoodsList(selIndex, 0);
        }
        return true;
    }

}
