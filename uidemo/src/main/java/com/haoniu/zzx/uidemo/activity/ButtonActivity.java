package com.haoniu.zzx.uidemo.activity;

import android.os.Bundle;

import com.haoniu.zzx.uidemo.R;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;

public class ButtonActivity extends BaseActivity {

    protected String title = "";

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_button);
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
