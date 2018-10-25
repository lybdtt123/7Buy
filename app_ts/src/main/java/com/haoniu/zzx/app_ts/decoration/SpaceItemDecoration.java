package com.haoniu.zzx.app_ts.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import self.androidbase.utils.DensityUtils;

/**
 * Created by zzx on 2017/12/15 0015.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemDecoration(Context ctx, int space) {
        this.space = DensityUtils.dip2px(ctx, space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = 3;
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = 3;
    }
}
