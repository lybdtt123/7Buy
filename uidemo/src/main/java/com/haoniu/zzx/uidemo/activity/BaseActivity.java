package com.haoniu.zzx.uidemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.haoniu.zzx.uidemo.R;
import com.haoniu.zzx.uidemo.utils.ToastUtils;
import com.haoniu.zzx.uidemo.view.SlideLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.ButterKnife;

/**
 * Created by zzx on 2017/10/19 0019.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public Context mContext;

    private QMUITopBar mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
        initContentView(savedInstanceState);
        new SlideLayout(this);
        mTopBar = (QMUITopBar) findViewById(R.id.topbar);
        if (mTopBar != null) {
            initTopBar();
        }
        initView();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);

    }

    protected abstract void initContentView(Bundle bundle);

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    protected abstract void getBundleExtras(Bundle extras);

    protected abstract void initView();

    private void initTopBar() {
        mTopBar.setBackgroundColor(getResources().getColor(R.color.app_color_blue));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setTitle(String title) {
        mTopBar.setTitle(title);
    }

    protected void toast(String msg) {
        ToastUtils.showTextToast(mContext, msg);
    }

    protected void print(String msg) {
        Log.d("TAG", msg);
    }
}
