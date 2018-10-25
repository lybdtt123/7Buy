package com.haoniu.zzx.app_ts.myinterface;

import android.view.MotionEvent;

/**
 * Created by zzx on 2018/1/29 0029.
 */

public interface  OnGestureListener {
    boolean onDown(MotionEvent var1);

    void onShowPress(MotionEvent var1);

    boolean onSingleTapUp(MotionEvent var1);

    boolean onScroll(MotionEvent var1, MotionEvent var2, float var3, float var4);

    void onLongPress(MotionEvent var1);

    boolean onFling(MotionEvent var1, MotionEvent var2, float var3, float var4);
}
