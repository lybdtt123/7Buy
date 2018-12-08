package com.haoniu.zzx.app_ts.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.haoniu.zzx.app_ts.PermissionUtil;
import com.haoniu.zzx.app_ts.R;

/**
 * 欢迎页
 */
public class WelcomeActivity extends BaseActivity {

    private static final int REQUEST_CODE_TEST = 999;
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void initView() {
        // 注册
// 取消注册
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permission == PackageManager.PERMISSION_GRANTED) {
                ImageView ivWelcome = (ImageView) findViewById(R.id.ivWelcome);
                AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
                animation.setDuration(1500);
                ivWelcome.startAnimation(animation);
                handlerLogin.sendEmptyMessageDelayed(1, 1500);
            }else
                PermissionUtil.verifyStoragePermissions(this);
        }else {
            ImageView ivWelcome = (ImageView) findViewById(R.id.ivWelcome);
            AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
            animation.setDuration(1500);
            ivWelcome.startAnimation(animation);
            handlerLogin.sendEmptyMessageDelayed(1, 1500);
            // do something
        }


    }
    /**
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 授予结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ImageView ivWelcome = (ImageView) findViewById(R.id.ivWelcome);
                    AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
                    animation.setDuration(1500);
                    ivWelcome.startAnimation(animation);
                    handlerLogin.sendEmptyMessageDelayed(1, 1500);
                } else {
                    Toast.makeText(this, "申请权限shi白:" + android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
        }
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
