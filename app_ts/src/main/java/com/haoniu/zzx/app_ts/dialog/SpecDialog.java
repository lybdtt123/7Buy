package com.haoniu.zzx.app_ts.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.adapter.Spec1Adapter;
import com.haoniu.zzx.app_ts.adapter.SpecFlowAdapter;
import com.haoniu.zzx.app_ts.decoration.DividerItemDecoration;
import com.haoniu.zzx.app_ts.http.AppConfig;
import com.haoniu.zzx.app_ts.http.AppContext;
import com.haoniu.zzx.app_ts.http.HttpUtils;
import com.haoniu.zzx.app_ts.http.JsonCallback;
import com.haoniu.zzx.app_ts.layoutmanager.FullyLinearLayoutManager;
import com.haoniu.zzx.app_ts.model.SpecificationsModel;
import com.haoniu.zzx.app_ts.myinterface.CommonEnity;
import com.haoniu.zzx.app_ts.utils.PreferenceUtils;
import com.haoniu.zzx.app_ts.utils.QiNiuGlideUtils;
import com.haoniu.zzx.app_ts.utils.StringUtils;
import com.haoniu.zzx.app_ts.utils.ToastUtils;
import com.haoniu.zzx.app_ts.view.RoundAngleImageView;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2017/12/15 0015.
 * 规格对话框
 */

public class SpecDialog extends Dialog {
    @BindView(R.id.llSpecification)
    LinearLayout llSpecification;//
    @BindView(R.id.tvDiaSpecPrice)
    TextView tvDiaSpecPrice;//价格
    @BindView(R.id.tvDiaSpec)
    TextView tvDiaSpec;//规格
    @BindView(R.id.ivDiaSpecDis)
    ImageView ivDiaSpecDis;//差
    @BindView(R.id.specRecyclerView)
    RecyclerView specRecyclerView;
    @BindView(R.id.tvDiaSpecNumReduce)
    TextView tvDiaSpecNumReduce;//减
    @BindView(R.id.edDiaSpecNum)
    EditText edDiaSpecNum;//数量
    @BindView(R.id.tvDiaSpecNumAdd)
    TextView tvDiaSpecNumAdd;//加
    @BindView(R.id.tvDiaSpecEnsure)
    TextView tvDiaSpecEnsure;//确定
    @BindView(R.id.ivDiaSpecPhoto)
    RoundAngleImageView ivDiaSpecPhoto;//选中商品的图片

    private SpecificationsModel specificationsModel;
    private Context mContext;
    private SpecFlowAdapter spec1Adapter;
//    private Spec1Adapter spec1Adapter;

    private int goodsNum = 1;
    //是否加入购物车
    private boolean addCar;

    public void setAddCar(boolean addCar) {
        this.addCar = addCar;
    }

    public SpecDialog(@NonNull Context context, SpecificationsModel specificationsModel) {
        super(context, R.style.Theme_Light_FullScreenDialogAct);
        setContentView(R.layout.dialog_goods_specifications);
        ButterKnife.bind(this);
        Window window = this.getWindow();
        window.setWindowAnimations(R.style.myDialogAnim);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(lp);
        mContext = context;
        this.specificationsModel = specificationsModel;
        initClick();
        initView();
    }

    private void initView() {
        if (specificationsModel.getGoods() != null) {
            Glide.with(mContext)
                    .load(QiNiuGlideUtils.boundary320(specificationsModel.getGoods().getThumb())).into(ivDiaSpecPhoto);
            //   (specificationsModel.getGoods().getMaxprice().equals(specificationsModel.getGoods().getMinprice()))
            if (Double.parseDouble(specificationsModel.getGoods().getMaxprice()) == Double.parseDouble(specificationsModel.getGoods().getMinprice())) {

                showPrice = "￥" + specificationsModel.getGoods().getMinprice();
            } else {
                showPrice = "￥" + specificationsModel.getGoods().getMinprice()
                        + "~" + specificationsModel.getGoods().getMaxprice();
            }
            tvDiaSpecPrice.setText(showPrice);
        }
        spec1Adapter = new SpecFlowAdapter(specificationsModel.getSpecs());

//        spec1Adapter = new Spec1Adapter(specificationsModel.getSpecs());
        spec1Adapter.setSpecItemClick(new SpecFlowAdapter.SpecItemClick() {
            @Override   //  selSpec 大规格   selSmall 每个规格里面的小分类
            public void onItemClick(int selSpec, int selSmall) {
                if (!StringUtils.isEmpty(specificationsModel.getSpecs().get(selSpec).getItems().get(selSmall).getThumb())) {
                    Glide.with(mContext).load(specificationsModel.getSpecs().get(selSpec).getItems().get(selSmall).getThumb()).into(ivDiaSpecPhoto);
                }
                initPrice();
            }
        });
        specRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        specRecyclerView.setAdapter(spec1Adapter);
        final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) specRecyclerView.getLayoutParams();
        int height = layoutParams.height;
//        //  specRecyclerView 高度
        spec1Adapter.setRecyclerViewHeight(new SpecFlowAdapter.RecyclerViewHeight() {
            @Override
            public void viewTotalHeight(int height) {
                if (height >= DensityUtils.dip2px(mContext, 250)) {
                    layoutParams.height = DensityUtils.dip2px(mContext, 250);
                }
//                else if (height > 0) {
//                    layoutParams.height = height ;
//                }
                specRecyclerView.setLayoutParams(layoutParams);
            }
        });
        edDiaSpecNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edDiaSpecNum.getText().toString().startsWith("0") || StringUtils.isEmpty(edDiaSpecNum.getText().toString())) {
                    goodsNum = 1;
                    edDiaSpecNum.setText("1");
                } else {
                    goodsNum = Integer.parseInt(edDiaSpecNum.getText().toString());
                }
                if (goodsNum > 1) {
                    tvDiaSpecNumReduce.setTextColor(mContext.getResources().getColor(R.color.colorGrayText48));
                    tvDiaSpecNumReduce.setBackgroundResource(R.drawable.shape_gray_0);
                    tvDiaSpecNumReduce.setFocusable(true);
                } else {
                    tvDiaSpecNumReduce.setTextColor(mContext.getResources().getColor(R.color.colorGrayM));
                    tvDiaSpecNumReduce.setBackgroundResource(R.drawable.shape_white_0);
                    tvDiaSpecNumReduce.setFocusable(false);
                }
            }
        });
        if (specificationsModel.getSpecs() == null || specificationsModel.getSpecs().size() == 0) {
            tvDiaSpec.setText("");
        } else {
            tvDiaSpecEnsure.setClickable(false);
            tvDiaSpecEnsure.setBackgroundColor(mContext.getResources().getColor(R.color.colorGrayText));
        }
    }

    //  切换
    private void initClick() {
        tvDiaSpecEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optionsBean != null) {
                    specInfo.specDetailInfo(optionsBean, goodsNum);
                } else {
                    specInfo.specDetailInfo(goodsNum);
                }
                if (addCar) {
                    requestAddToShopCar();
                }
                dismiss();
            }
        });
    }

    private String specKey, goodsName;
    private SpecificationsModel.OptionsBean optionsBean;
    private String showPrice;

    //  价格
    private void initPrice() {
        specKey = "";
        goodsName = "";
        List<Integer> selList = spec1Adapter.getSelList();
        //   i   大规格for循环  取出 小规格里面对应的title;
        for (int i = 0; i < specificationsModel.getSpecs().size(); i++) {
            if (selList.get(i) != -1) {
                specKey = specKey + "_" + specificationsModel.getSpecs().get(i).getItems().get(selList.get(i)).getId();
                goodsName = goodsName + "+" + specificationsModel.getSpecs().get(i).getItems().get(selList.get(i)).getTitle();
            }
        }
        if (specKey.startsWith("_")) {
            specKey = specKey.replaceFirst("_", "");
        }
        if (goodsName.startsWith("+")) {
            goodsName = goodsName.replaceFirst("\\+", "");
        }
        for (SpecificationsModel.OptionsBean optionsBean : specificationsModel.getOptions()) {
            if (optionsBean.getSpecs() != null) {
                if (specKey.equals(optionsBean.getSpecs())) { // id_
                    if (optionsBean.getStock().equals("0")) {
                        this.optionsBean = optionsBean;
                        tvDiaSpecEnsure.setClickable(false);
                        tvDiaSpecEnsure.setBackgroundColor(mContext.getResources().getColor(R.color.colorGrayText));
                    } else {
                        this.optionsBean = optionsBean;
                        tvDiaSpecEnsure.setClickable(true);
                        tvDiaSpecEnsure.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                    }
                    tvDiaSpecPrice.setText("￥" + optionsBean.getMarketprice());
                    tvDiaSpec.setText(goodsName + "（库存 " + optionsBean.getStock() + "）");
                    return;
                }
            }
        }
        tvDiaSpec.setText("请选择规格");
        tvDiaSpecPrice.setText(showPrice);
        tvDiaSpecEnsure.setClickable(false);
        tvDiaSpecEnsure.setBackgroundColor(mContext.getResources().getColor(R.color.colorGrayText));
    }

    @OnClick({R.id.ivDiaSpecDis, R.id.tvDiaSpecNumReduce, R.id.tvDiaSpecNumAdd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivDiaSpecDis:
                dismiss();
                break;
            case R.id.tvDiaSpecNumReduce:
                goodsNum--;
                edDiaSpecNum.setText(goodsNum + "");
                break;
            case R.id.tvDiaSpecNumAdd:
                goodsNum++;
                edDiaSpecNum.setText(goodsNum + "");
                break;
        }
    }

    /**
     * 加入购物车
     */
    private void requestAddToShopCar() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", specificationsModel.getGoods().getId());
        map.put("total", goodsNum);
        if (optionsBean != null) {
            map.put("optionid", optionsBean.getId());
        } else {
            map.put("optionid", "0");
        }
        map.put("cookie", PreferenceUtils.getStringPreferences(mContext, AppContext.getInstance().COOKIE, null));
        HttpUtils.requestGet(mContext, AppConfig.requestAddToCar, map, new JsonCallback<String>() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!StringUtils.isEmpty(response.body())) {
                    JSONObject obj;
                    int cartcount = 0;
                    try {
                        obj = new JSONObject(response.body());
                        cartcount = obj.getInt("cartcount");
                        if (cartcount != 0) {
                            EventBus.getDefault().post(new CommonEnity<>("addToCar", cartcount));
                            ToastUtils.showTextToast(mContext, "加入成功");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private SpecInfo specInfo;

    public void setSpecInfo(SpecInfo specInfo) {
        this.specInfo = specInfo;
    }

    public interface SpecInfo {
        void specDetailInfo(SpecificationsModel.OptionsBean optionsBean, int goodsNum);

        void specDetailInfo(int goodsNum);
    }
}
