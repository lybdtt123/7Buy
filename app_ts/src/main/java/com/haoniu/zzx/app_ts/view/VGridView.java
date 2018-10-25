package com.haoniu.zzx.app_ts.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by zzx on 2017/9/22 0022.
 */

public class VGridView extends GridView {

    public VGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        int descendantFocusability = getDescendantFocusability();
        final boolean took = super.requestFocus(direction, previouslyFocusedRect);
        switch (descendantFocusability) {
            case FOCUS_BLOCK_DESCENDANTS:
//ViewGroup本身进行处理，不管是否处理成功，都不会分发给ChildView进行处理
                return super.requestFocus(direction, previouslyFocusedRect);
            case FOCUS_BEFORE_DESCENDANTS: {
//ViewGroup本身先对焦点进行处理，如果没有处理(返回false)则分发给child View进行处理
                return took ? took : onRequestFocusInDescendants(direction, previouslyFocusedRect);
            }
            case FOCUS_AFTER_DESCENDANTS: {
//先分发给Child View进行处理，如果所有的Child View都没有处理(false)，则自己再处理
                //final boolean took = onRequestFocusInDescendants(direction, previouslyFocusedRect);
                return took ? took : super.requestFocus(direction, previouslyFocusedRect);
            }
            default:
                throw new IllegalStateException("descendant focusability must be "
                        + "one of FOCUS_BEFORE_DESCENDANTS, FOCUS_AFTER_DESCENDANTS, FOCUS_BLOCK_DESCENDANTS "
                        + "but is " + descendantFocusability);
        }
    }


    /**
     * 屏蔽android4.4 setAdapter时View抢焦点的BUG
     */
    @Override
    public boolean isInTouchMode() {
        if (19 == Build.VERSION.SDK_INT) {
            return !(hasFocus() && !super.isInTouchMode());
        } else {
            return super.isInTouchMode();
        }
    }

}
