package com.shoo.demo.widget.linearlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Shoo on 16-10-22.
 */
public class MyLinearLayout extends LinearLayout {
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int heightSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, heightSpec);
//    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getOrientation() == VERTICAL) {
            layoutVertical(l, t, r, b);
        } else {
            super.onLayout(changed, l, t, r, b);
        }
    }

    private void layoutVertical(int left, int top, int right, int bottom) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();

        int childLeft = paddingLeft;
        int childRight = right - paddingRight;
        int childTop = getPaddingTop();
        int childBottom;

        int childSpace = right - left;

        int childCount = getChildCount();
        for (int i = 0; i <childCount; i++) {
            View child = getChildAt(i);
            if (GONE == child.getVisibility()) {
                continue;
            }

            LinearLayout.LayoutParams lp = (LayoutParams) child.getLayoutParams();
            childLeft = paddingLeft + lp.leftMargin;
            childRight = childRight - lp.rightMargin;
            childTop += lp.topMargin;
            childBottom = childTop + child.getMeasuredHeight();
            child.layout(childLeft, childTop, childRight, childBottom);

            childTop = childBottom + lp.bottomMargin;
        }
    }
}
