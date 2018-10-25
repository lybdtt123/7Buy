package com.haoniu.zzx.app_ts.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.haoniu.zzx.app_ts.Constant;
import com.haoniu.zzx.app_ts.R;
import com.haoniu.zzx.app_ts.model.CommonEventBusEnity;
import com.haoniu.zzx.app_ts.myinterface.CommonEnity;
import com.haoniu.zzx.app_ts.utils.L;
import com.haoniu.zzx.app_ts.utils.ToastUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;


public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);
        api = WXAPIFactory.createWXAPI(this, Constant.WECHAT);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        // 支付结果回调...
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {//支付成功
                L.d("TAG", "RESP:" + "成功");
                ToastUtils.showTextToast(this, "支付成功!");
                EventBus.getDefault().post(new CommonEnity<>("wxpay"));
                finish();
            } else {
                EventBus.getDefault().post(new CommonEnity<>("wxpay"));
                WXPayEntryActivity.this.finish();
            }
        } else {
            EventBus.getDefault().post(new CommonEnity<>("wxpay"));
            WXPayEntryActivity.this.finish();
            L.d("TAG", "RESP:" + "失败111");
        }
    }
}
