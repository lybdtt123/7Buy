package com.haoniu.zzx.app_ts.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.haoniu.zzx.app_ts.R;
import com.umeng.analytics.MobclickAgent;

/**
 * 欢迎页
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void initView() {
        ImageView ivWelcome = (ImageView) findViewById(R.id.ivWelcome);
        AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
        animation.setDuration(1500);
        ivWelcome.startAnimation(animation);
        handlerLogin.sendEmptyMessageDelayed(1, 1500);
    }

    @Override
    protected void initLogic() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    Handler handlerLogin = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(WelcomeActivity.this, HomePageActivity.class));
            WelcomeActivity.this.finish();
        }
    };
}
