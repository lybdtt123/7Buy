package com.haoniu.zzx.uidemo.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by zzx on 2017/11/23 0023.
 */

public class SlideLayout extends FrameLayout {
    private Activity mActivity;
    private Scroller mScroller;
    private int interceptDownX, interceptX, interceptY;
    private int touchDownX, touchX, touchY;
    private boolean isScrolling;

    public SlideLayout(@NonNull Context context) {
        this(context, null);
    }

    public SlideLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mScroller = new Scroller(context);
        mActivity = (Activity) context;
        ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
        View childView = decorView.getChildAt(0);
        decorView.removeView(childView);
        addView(childView);
        decorView.addView(this);
    }

//    public void bindActivity(Activity activity) {
//        mActivity = activity;
//        ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
//        View childView = decorView.getChildAt(0);
//        decorView.removeView(childView);
//        addView(childView);
//        decorView.addView(this);
//    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                interceptDownX = x;
                interceptX = x;
                interceptY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = x - interceptX;
                int moveY = y - interceptY;
                // 判断是否从边缘开始 且 横向移动
                if (interceptDownX < (getWidth() / 10) && Math.abs(moveX) > Math.abs(moveY)) {
                    intercept = true;
                } else {
                    intercept = false;
                }
                interceptX = x;
                interceptY = y;
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                interceptDownX = interceptX = interceptY = 0;
                break;
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownX = x;
                touchX = x;
                touchY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = x - touchX;
                int moveY = y - touchY;
                // 判断是否从边缘开始 且 横向移动
                if (!isScrolling && touchDownX < (getWidth() / 10) && Math.abs(moveX) > Math.abs(moveY)) {
                    isScrolling = true;
                }
                if (isScrolling) {
                    int dx = touchX - x;
                    scrollBy(dx, 0);
                }
                touchX = x;
                touchY = y;
                break;
            case MotionEvent.ACTION_UP:
                isScrolling = false;
                touchDownX = touchX = touchY = 0;
                //是否滑动到一半  getScrollX 负值
                int scrollX = getScrollX();
                if (-getScrollX() < getWidth() / 2) {
                    scrollBack();
                } else {
                    scrollClose();
                }
                break;
        }
        return true;
    }

    private void scrollBack() {
        int currX = getScrollX();
        int dx = -currX;
        // 起点   偏移量
        mScroller.startScroll(currX, 0, dx, 0, 300);
        invalidate();
    }

    private void scrollClose() {
        int currX = getScrollX();
        int dx = -currX - getWidth();
        // 起点   偏移量
        mScroller.startScroll(currX, 0, dx, 0, 300);
        invalidate();
    }

    @Override
    public void computeScroll() {
//        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), 0);
            postInvalidate();
        } else if (-getScrollX() >= getWidth()){
            mActivity.finish();
        }
//        if (mScroller.computeScrollOffset()) {
//            scrollTo(mScroller.getCurrX(), 0);
//            postInvalidate();
//        } else if (-getScrollX() >= getWidth()) {
//            mActivity.finish();
//        }
    }
}
