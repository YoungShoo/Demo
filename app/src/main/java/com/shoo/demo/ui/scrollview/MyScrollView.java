package com.shoo.demo.ui.scrollview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;

/**
 * Created by Shoo on 16-10-22.
 */
public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Indicates this ScrollView whether it should stretch its content height to fill
        // the viewport or not.
//        if (!isFillViewport()) {
//            return;
//        }

        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.UNSPECIFIED) {
            return;
        }

        if (getChildCount() > 0) {
            final View child = getChildAt(0);
            final int height = getMeasuredHeight();
            if (child.getMeasuredHeight() < height) {
                final int widthPadding;
                final int heightPadding;
                final FrameLayout.LayoutParams lp = (LayoutParams) child.getLayoutParams();
                final int targetSdkVersion = getContext().getApplicationInfo().targetSdkVersion;
                int paddingLeft = getPaddingLeft();
                int paddingRight = getPaddingRight();
                int paddingTop = getPaddingTop();
                int paddingBottom = getPaddingBottom();
                if (targetSdkVersion >= Build.VERSION_CODES.M) {
                    widthPadding = paddingLeft + paddingRight + lp.leftMargin + lp.rightMargin;
                    heightPadding = paddingTop + paddingBottom + lp.topMargin + lp.bottomMargin;
                } else {
                    widthPadding = paddingLeft + paddingRight;
                    heightPadding = paddingTop + paddingBottom;
                }

                final int childWidthMeasureSpec = getChildMeasureSpec(
                        widthMeasureSpec, widthPadding, lp.width);
                final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                        height - heightPadding, MeasureSpec.EXACTLY  /*MeasureSpec.getMode(heightMeasureSpec)*/);
                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }
    }
}
