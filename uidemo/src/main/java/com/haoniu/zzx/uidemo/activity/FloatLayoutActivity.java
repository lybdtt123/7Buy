package com.haoniu.zzx.uidemo.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haoniu.zzx.uidemo.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class FloatLayoutActivity extends BaseActivity {

    protected String title = "";

    @BindView(R.id.mFloatLayout)
    QMUIFloatLayout mFloatLayout;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_float_layout);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        title = extras.getString("title");
    }

    @Override
    protected void initView() {
        setTitle(title);
        for (int i = 0; i < 30; i++) {
            addItem();
        }
    }

    @OnClick({R.id.btAdd, R.id.btReduce, R.id.btLeft, R.id.btCen, R.id.btRight, R.id.btOne,R.id.btMore})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btAdd:
                addItem();
                break;
            case R.id.btReduce:
                removeItem();
                break;
            case R.id.btLeft:
                mFloatLayout.setGravity(Gravity.LEFT);
                break;
            case R.id.btCen:
                mFloatLayout.setGravity(Gravity.CENTER);
                break;
            case R.id.btRight:
                mFloatLayout.setGravity(Gravity.RIGHT);
                break;
            case R.id.btOne:
                mFloatLayout.setMaxLines(1);
                break;
            case R.id.btMore:
                mFloatLayout.setMaxLines(Integer.MAX_VALUE);
                break;
        }
    }

    private void addItem() {
        int currentChildCount = mFloatLayout.getChildCount();

        TextView textView = new TextView(mContext);
        int textViewPadding = QMUIDisplayHelper.dp2px(mContext, 4);
        textView.setPadding(textViewPadding, textViewPadding, textViewPadding, textViewPadding);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.qmui_config_color_white));
        textView.setText(String.valueOf(currentChildCount));
        textView.setBackgroundResource(currentChildCount % 2 == 0 ? R.color.app_color_theme_3 : R.color.app_color_theme_4);

        int textViewSize = QMUIDisplayHelper.dpToPx(60);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(textViewSize, textViewSize);
        mFloatLayout.addView(textView, lp);
    }

    private void removeItem() {
        if (mFloatLayout.getChildCount() == 0) {
            return;
        }
        mFloatLayout.removeView(mFloatLayout.getChildAt(mFloatLayout.getChildCount() - 1));
    }
}
