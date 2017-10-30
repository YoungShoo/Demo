package com.shoo.demo.widget.percent;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Shoo on 16-10-20.
 */
public class SPercentFrameLayout extends FrameLayout {

    private SPercentLayoutHelper mHelper = new SPercentLayoutHelper(this);

    public SPercentFrameLayout(Context context) {
        super(context);
    }

    public SPercentFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SPercentFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(this.getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mHelper.adjustChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHelper.handleMeasuredStateTooSmall()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mHelper.restoreOriginParams();
    }

    public static class LayoutParams extends FrameLayout.LayoutParams implements SPercentLayoutHelper.SPercentLayoutParams {

        private SPercentLayoutHelper.SPercentLayoutInfo mLayoutInfo;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            mLayoutInfo = SPercentLayoutHelper.generateLayoutInfo(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(int width, int height, int gravity) {
            super(width, height, gravity);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(FrameLayout.LayoutParams source) {
            super(source);
        }

        @Override
        public SPercentLayoutHelper.SPercentLayoutInfo getPercentLayoutInfo() {
            if (mLayoutInfo == null) {
                mLayoutInfo = new SPercentLayoutHelper.SPercentLayoutInfo();
            }
            return mLayoutInfo;
        }

        @Override
        protected void setBaseAttributes(TypedArray a, int widthAttr, int heightAttr) {
            SPercentLayoutHelper.fetchWidthAndHeight(this, a, widthAttr, heightAttr);
        }
    }
}
