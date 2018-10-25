package com.haoniu.zzx.uidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.RotateAnimation;

import com.haoniu.zzx.uidemo.R;

/**
 * Created by zzx on 2017/11/23 0023.
 */

public class TestView extends View {

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Paint p1 = new Paint();
//        p1.setColor(getResources().getColor(R.color.colorAccent));
//        Paint p2 = new Paint();
//        p2.setColor(getResources().getColor(R.color.app_color_theme_3));
//        Paint p3 = new Paint();
//        p3.setColor(getResources().getColor(R.color.colorWhite));
//        canvas.drawRect(300, 300, 250, 250, p3);
//        canvas.drawRect(100, 100, 150, 150, p1);
//        canvas.rotate(15);
//        canvas.save();
//        canvas.rotate(-30);
//        canvas.drawRect(300, 300, 250, 250, p2);
//        canvas.restore();
//        canvas.drawRect(300, 300, 250, 250, p3);

        Paint mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.app_color_blue));
        Rect rect = new Rect(50, 0, 150, 50);
        canvas.translate(200, 200);
        for (int i = 0; i < 36; i++) {
            canvas.drawRect(rect, mPaint);
            canvas.rotate(10);
        }
    }
}
