package com.haoniu.zzx.uidemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.haoniu.zzx.uidemo.R;
import com.qmuiteam.qmui.widget.QMUIEmptyView;

import butterknife.BindView;
import butterknife.OnClick;

public class EmptyViewActivity extends BaseActivity {


    @BindView(R.id.mEmptyView)
    QMUIEmptyView mEmptyView;

    protected String title = "";


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_empty_view);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        title = extras.getString("title");
    }

    @Override
    protected void initView() {
        setTitle(title);

    }

    @OnClick({R.id.btTwo, R.id.btOne, R.id.btLoad, R.id.btOneC, R.id.btTwoC})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btTwo:
//                mEmptyView.show("titleText", "detailText");
                Bundle bundle = new Bundle();
                startActivity(new Intent(mContext,MainActivity.class));
                break;
            case R.id.btOne:
                mEmptyView.show("titleText", null);
                break;
            case R.id.btLoad:
                mEmptyView.show(true);
                break;
            case R.id.btOneC:
                mEmptyView.show(false, "titleText", null, "点击", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast("点击");
                    }
                });
                break;
            case R.id.btTwoC:
                mEmptyView.show(false, "titleText", "detailText", "点击", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast("点击");
                    }
                });
                break;
        }
    }
}
