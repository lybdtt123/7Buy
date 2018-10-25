package com.haoniu.zzx.uidemo.activity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.haoniu.zzx.uidemo.R;
import com.haoniu.zzx.uidemo.view.TestView;

import butterknife.BindView;
import butterknife.OnClick;

public class DrawTestActivity extends BaseActivity {
    protected String title = "";
    @BindView(R.id.testView)
    TestView testView;
    @BindView(R.id.ivBg)
    ImageView ivBg;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_draw_test);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        title = extras.getString("title");
    }

    private RotateAnimation animation;

    @Override
    protected void initView() {
        setTitle(title);
        animation = new RotateAnimation(0, 720,Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
    }

    @OnClick(R.id.btStart)
    public void onClick() {
        ivBg.setAnimation(animation);
        animation.start();
    }
}
