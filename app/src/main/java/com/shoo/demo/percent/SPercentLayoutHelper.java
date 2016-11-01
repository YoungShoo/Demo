package com.shoo.demo.percent;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.shoo.demo.R;

/**
 * Created by Shoo on 16-10-20.
 */
public class SPercentLayoutHelper {

    private final ViewGroup mHost;

    public SPercentLayoutHelper(ViewGroup host) {
        mHost = host;
    }

    public static SPercentLayoutInfo generateLayoutInfo(Context context, AttributeSet attrs) {
        SPercentLayoutInfo info = null;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PercentLayout);
        float fraction = typedArray.getFraction(R.styleable.PercentLayout_layoutWidthPercent, 1, 1, -1.0F);
        if (fraction != -1.0F) {
            info = new SPercentLayoutInfo();
            info.widthPercent = fraction;
        }
        fraction = typedArray.getFraction(R.styleable.PercentLayout_layoutHeightPercent, 1, 1, -1.0F);
        if (fraction != -1.0F) {
            info = info != null ? info : new SPercentLayoutInfo();
            info.heightPercent = fraction;
        }
        typedArray.recycle();

        return info;
    }

    public static void fetchWidthAndHeight(ViewGroup.LayoutParams params, TypedArray array, int widthAttr, int heightAttr) {
        params.width = array.getLayoutDimension(widthAttr, 0);
        params.height = array.getLayoutDimension(heightAttr, 0);
    }

    public void adjustChildren(int widthMeasureSpec, int heightMeasureSpec) {
        int widthHint = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightHint = View.MeasureSpec.getSize(heightMeasureSpec);
        int size = mHost.getChildCount();
        for (int i = 0; i < size; i++) {
            View child = mHost.getChildAt(i);
            ViewGroup.LayoutParams params = child.getLayoutParams();
            if (params instanceof SPercentLayoutParams) {
                SPercentLayoutInfo info = ((SPercentLayoutParams) params).getPercentLayoutInfo();
                info.fillLayoutParams(params, widthHint, heightHint);
            }
        }
    }

    public boolean handleMeasuredStateTooSmall() {
        boolean needSecondMeasure = false;

        int size = mHost.getChildCount();
        for (int i = 0; i < size; i++) {
            View child = mHost.getChildAt(i);
            ViewGroup.LayoutParams params = child.getLayoutParams();
            if (params instanceof SPercentLayoutParams) {
                SPercentLayoutInfo info = ((SPercentLayoutParams) params).getPercentLayoutInfo();
                if (handleMeasuredWidthStateTooSmall(child, info)) {
                    params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    needSecondMeasure = true;
                }
                if (handleMeasuredHeightStateTooSmall(child, info)) {
                    params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    needSecondMeasure = true;
                }
            }
        }

        return needSecondMeasure;
    }

    private boolean handleMeasuredWidthStateTooSmall(View view, SPercentLayoutInfo info) {
        int state = ViewCompat.getMeasuredWidthAndState(view) & ViewCompat.MEASURED_STATE_MASK;
        return state == ViewCompat.MEASURED_STATE_TOO_SMALL && info.widthPercent >= 0.0F
                && info.mOriginWidth == ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    private boolean handleMeasuredHeightStateTooSmall(View view, SPercentLayoutInfo info) {
        int state = ViewCompat.getMeasuredHeightAndState(view) & ViewCompat.MEASURED_STATE_MASK;
        return state == ViewCompat.MEASURED_STATE_TOO_SMALL && info.heightPercent >= 0.0F
                && info.mOriginHeight == ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    public void restoreOriginParams() {
        int size = mHost.getChildCount();
        for (int i = 0; i < size; i++) {
            View child = mHost.getChildAt(i);
            ViewGroup.LayoutParams params = child.getLayoutParams();
            if (params instanceof SPercentLayoutParams) {
                SPercentLayoutInfo info = ((SPercentLayoutParams) params).getPercentLayoutInfo();
                info.restoreLayoutParams(params);
            }
        }
    }

    public interface SPercentLayoutParams {
        SPercentLayoutInfo getPercentLayoutInfo();
    }

    public static class SPercentLayoutInfo {

        public float widthPercent = -1.0F;
        public float heightPercent = -1.0F;

        int mOriginWidth;
        int mOriginHeight;

        public void fillLayoutParams(ViewGroup.LayoutParams layoutParams, int widthHint, int heightHint) {
            mOriginWidth = layoutParams.width;
            mOriginHeight = layoutParams.height;

            if (widthPercent >= 0.0F) {
                layoutParams.width = (int) (widthPercent * widthHint);
            }
            if (heightPercent >= 0.0F) {
                layoutParams.height = (int) (heightPercent * heightHint);
            }
        }

        public void restoreLayoutParams(ViewGroup.LayoutParams layoutParams) {
            layoutParams.width = mOriginWidth;
            layoutParams.height = mOriginHeight;
        }
    }
}
