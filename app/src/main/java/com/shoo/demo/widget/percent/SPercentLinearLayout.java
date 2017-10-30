package com.shoo.demo.widget.percent;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Shoo on 16-10-20.
 */
public class SPercentLinearLayout extends LinearLayout {

    private SPercentLayoutHelper mHelper = new SPercentLayoutHelper(this);

    public SPercentLinearLayout(Context context) {
        super(context);
    }

    public SPercentLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SPercentLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mHelper.restoreOriginParams();
    }

    public static class LayoutParams extends LinearLayout.LayoutParams implements SPercentLayoutHelper.SPercentLayoutParams {

        private SPercentLayoutHelper.SPercentLayoutInfo mLayoutInfo = null;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            mLayoutInfo = SPercentLayoutHelper.generateLayoutInfo(c, attrs);
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
