package com.haoniu.zzx.uidemo.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;
import android.widget.SeekBar;

import com.haoniu.zzx.uidemo.R;
import com.qiushui.blurredview.BlurredView;

import butterknife.BindView;

public class BlurredViewActivity extends BaseActivity {
    @BindView(R.id.mBlurredView)
    BlurredView mBlurredView;
    @BindView(R.id.mSeekBar)
    SeekBar mSeekBar;
    protected String title = "";

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_blurred_view);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        title = extras.getString("title");
    }

    @Override
    protected void initView() {
        setTitle(title);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mBlurredView.setBlurredLevel(progress);
                mBlurredView.setBlurredTop(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mBlurredView.setBlurredLevel(seekBar.getProgress());
                mBlurredView.setBlurredTop(seekBar.getProgress());
            }
        });
        mBlurredView.showBlurredView();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void blurBitmap(Bitmap bitmap, float radius) {
        RenderScript rs=  RenderScript.create(this);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
    }
}
