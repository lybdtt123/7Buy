package com.haoniu.zzx.uidemo.activity;

import android.os.Bundle;

import com.haoniu.zzx.uidemo.R;

public class DiyViewActivity extends BaseActivity {

    protected String title = "";

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_diy_view);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        title = extras.getString("title");
    }

    @Override
    protected void initView() {
        setTitle(title);
    }
}
