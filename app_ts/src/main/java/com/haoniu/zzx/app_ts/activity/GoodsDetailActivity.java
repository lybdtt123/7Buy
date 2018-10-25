package com.haoniu.zzx.app_ts.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.fragment.GoodsDetailFragment;
import com.haoniu.zzx.app_ts.fragment.QuestFragment;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.AppContext;
import com.haoniu.zzx.app_ts.http.HttpUtils;
import com.haoniu.zzx.app_ts.http.JsonCallback;
import com.haoniu.zzx.app_ts.model.GoodsDetailModel;
import com.haoniu.zzx.app_ts.myinterface.CommonEnity;
import com.haoniu.zzx.app_ts.myinterface.EventInterface;
import com.haoniu.zzx.app_ts.utils.PreferenceUtils;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.lzy.okgo.model.Response;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品详情
 */
public class GoodsDetailActivity extends BaseFragmentActivity {
    private final String[] tabTitles = {"商品", "常见问题"};
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.tvGoodsNum)
    TextView tvGoodsNum;
    @BindView(R.id.ivGoodsFocus)
    ImageView ivGoodsFocus;
    private GoodsDetailFragment goodsDetailFragment;
    private QuestFragment questFragment;
    private List<Fragment> fragmentList;

    private String id;
    private GoodsDetailModel goodsDetailModel;



    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_goods_detail);
    }

    @Override
    protected void initView() {
        isLogin = !checkEmpty(PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null));
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        id = extras.getString("id");
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        goodsDetailFragment = new GoodsDetailFragment();
        questFragment = new QuestFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("detailModel", goodsDetailModel);
        bundle1.putString("id", id);
        goodsDetailFragment.setArguments(bundle1);
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("questionBean", goodsDetailModel.getQuestion());
        questFragment.setArguments(bundle2);
        fragmentList.add(goodsDetailFragment);
        fragmentList.add(questFragment);
        Adapter adapter = new Adapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initLogic() {
        requestGoodsDetail();
        setEventInterface(new EventInterface() {
            @Override
            public void setEvent(CommonEnity enity) {
                if (enity.getType().equals("addToCar")) {
                    int cartcount = (int) enity.getData();
                    goodsDetailModel.setCartCount(cartcount + "");
                    if (!StringUtils.isEmpty(goodsDetailModel.getCartCount())) {
                        tvGoodsNum.setText(goodsDetailModel.getCartCount());
                        tvGoodsNum.setVisibility(View.VISIBLE);
                    } else {
                        tvGoodsNum.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private boolean isLogin;

    @Override
    protected void onResume() {
        super.onResume();
        // 之前未登录 且 已经去登录成功
        if (!isLogin && goodsDetailModel != null && goodsDetailModel.getUser() == null
                && !checkEmpty(PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null))) {
            requestGoodsDetail();
        }
    }

    /**
     * 商品详情
     */
    private void requestGoodsDetail() {
        Map<String, Object> map = new HashMap<>();
        if (!checkEmpty(PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null))) {
            map.put("cookie", PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null));
        }
        map.put("id", id);
        HttpUtils.requestGet(mContext, AppConfig.requestGoodsDetail, map, new JsonCallback<GoodsDetailModel>(mContext) {
            @Override
            public void onSuccess(Response<GoodsDetailModel> response) {
                if (response != null && response.body() != null) {
                    if (goodsDetailModel == null) {
                        goodsDetailModel = response.body();
                        initData();
                    } else if (goodsDetailModel.getUser() == null && response.body().getUser() != null) {
                        goodsDetailModel.setUser(response.body().getUser());
                        goodsDetailModel.setCartCount(response.body().getCartCount() + "");
                    }
                    initUserUI();
                }
            }
        });
    }

    /**
     * 个人信息  关注与否 购物车数量
     */
    private void initUserUI() {
        if (goodsDetailModel.getUser() != null && goodsDetailModel.getUser().getMobile() != null) {
            if (goodsDetailModel.getUser().getIsFavorite() == 1) {
                ivGoodsFocus.setBackgroundResource(R.mipmap.img_heart_red);
            } else {
                ivGoodsFocus.setBackgroundResource(R.mipmap.img_heart_gray);
            }
        }
        if (!StringUtils.isEmpty(goodsDetailModel.getCartCount())) {
            tvGoodsNum.setText(goodsDetailModel.getCartCount());
            tvGoodsNum.setVisibility(View.VISIBLE);
        } else {
            tvGoodsNum.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.ll_back, R.id.llKeFu, R.id.llGoodsCar, R.id.llGoodsFocus, R.id.tvGoodsAddCar, R.id.tvGoodsBuy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.llKeFu://客服
                if (!checkEmpty(PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null))) {
                    if (goodsDetailModel != null && goodsDetailModel.getUser() != null) {
                        conversationWrapper();
                    } else {
                        requestGoodsDetail();
                    }
                } else {
                    startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestLogin));
                }
                break;
            case R.id.llGoodsCar://购物车
                if (!checkEmpty(PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null))) {
                    startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestShop));
                } else {
                    startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestLogin));
                }
                break;
            case R.id.llGoodsFocus://关注
                if (!checkEmpty(PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null))) {
                    requestFocus();
                } else {
                    startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestLogin));
                }
                break;
            case R.id.tvGoodsAddCar://加入购物车
                if (!checkEmpty(PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null))) {
                    //  加入购物车  选择规格 --- > 加入
                    goodsDetailFragment.operateSpecDialog(true);
                } else {
                    startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestLogin));
                }
                break;
            case R.id.tvGoodsBuy://立即购买
                if (!checkEmpty(PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null))) {
                    judgeBuy();
                } else {
                    startActivity(new Intent(mContext, WebviewActivity.class).putExtra("url", AppConfig.requestLogin));
                }
                break;
        }
    }

    private int optionid;
    private int goodNum;

    /**
     * 判断是否购买
     */
    private void judgeBuy() {
        if (goodsDetailFragment.isHasOption()) {
            if (goodsDetailFragment.getmOptionsBean() == null) {
                showToast("请选择商品规格!");
                return;
            }
            optionid = Integer.parseInt(goodsDetailFragment.getmOptionsBean().getId());
        }
        if (goodsDetailFragment.getGoodsNum() == -1) {
            showToast("请选择商品数量!");
            return;
        }
        goodNum = goodsDetailFragment.getGoodsNum();
        Intent intent = new Intent(mContext, WebviewActivity.class);
        intent.putExtra("url", AppConfig.requestToBuy + "id=" + id + "&optionid=" + optionid + "&total=" + goodNum);
        startActivity(intent);
    }

    /**
     * 关注
     */
    private void requestFocus() {
        Map<String, Object> map = new HashMap<>();
        if (goodsDetailModel.getUser().getIsFavorite() == 1) {
            map.put("isfavorite", "0");
        } else {
            map.put("isfavorite", "1");
        }
        map.put("id", id);
        map.put("cookie", PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null));
        HttpUtils.requestGet(mContext, AppConfig.requestGoodsFocus, map, new JsonCallback<String>(mContext) {
            @Override
            public void onSuccess(Response<String> response) {
                if (goodsDetailModel.getUser().getIsFavorite() == 1) {
                    goodsDetailModel.getUser().setIsFavorite(0);
                } else {
                    goodsDetailModel.getUser().setIsFavorite(1);
                }
                if (goodsDetailModel.getUser().getIsFavorite() == 1) {
                    ivGoodsFocus.setBackgroundResource(R.mipmap.img_heart_red);
                } else {
                    ivGoodsFocus.setBackgroundResource(R.mipmap.img_heart_gray);
                }
            }
        });
    }

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;

    /**
     * 美洽客服
     */
    private void conversationWrapper() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GoodsDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            HashMap<String, String> clientInfo = new HashMap<>();
            clientInfo.put("name", goodsDetailModel.getUser().getNickname());
            clientInfo.put("avatar", AppConfig.main + goodsDetailModel.getUser().getAvatar());
            Intent intent = new MQIntentBuilder(mContext)
                    .setClientInfo(clientInfo)
                    .setPreSendTextMessage("商品名称:" + goodsDetailModel.getGoods().getTitle() +
                            "\n商品链接:" + goodsDetailModel.getUser().getLink())
                    .setPreSendImageMessage(new File(goodsDetailModel.getGoods().getThumb()))
                    .build();
            startActivity(intent);
        }
    }


    private class Adapter extends FragmentPagerAdapter {
        private Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
