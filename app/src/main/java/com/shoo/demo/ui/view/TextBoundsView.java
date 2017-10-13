package com.shoo.demo.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Shoo on 16-10-22.
 */
public class TextBoundsView extends View {

    private static final String TAG = "MyView";
    private static final String TEXT = "口";

    private Paint mPaint;
    private Rect mTextBounds;
    private Paint.FontMetrics mFontMetrics;
    private int mWidth;
    private int mHeight;

    public TextBoundsView(Context context) {
        super(context);
        init(context);
    }

    public TextBoundsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextBoundsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(200);

        mFontMetrics = mPaint.getFontMetrics();

        // mFontMetrics: ascent = -185.54688, descent = 48.828125
        Log.d(TAG, "init: mFontMetrics: ascent = " + mFontMetrics.ascent + ", descent = " + mFontMetrics.descent);

        mTextBounds = new Rect();
        mPaint.getTextBounds(TEXT, 0, TEXT.length(), mTextBounds);

        // 口: mTextBounds = Rect(27, -146 - 174, 10), width = 147, height = 156
        // j: mTextBounds = Rect(-6, -143 - 33, 42), width = 39, height = 185
        Log.d(TAG, "init: mTextBounds = " + mTextBounds + ", width = " + mTextBounds.width() + ", height = " +
                mTextBounds.height());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(300, 300);
        canvas.drawColor(Color.BLACK);

        mPaint.setColor(Color.GRAY);
        canvas.drawRect(0, 0, mWidth, mHeight, mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.drawLine(0, mFontMetrics.ascent, mWidth, mFontMetrics.ascent, mPaint);

        mPaint.setColor(Color.YELLOW);
        canvas.drawLine(0, mFontMetrics.descent, mWidth, mFontMetrics.descent, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawLine(0, 0, 0, mHeight, mPaint);
        canvas.drawLine(mTextBounds.width(), 0, mTextBounds.width(), mHeight, mPaint);
        canvas.drawLine(mTextBounds.centerX(), mTextBounds.centerY(), mTextBounds.centerX(), mHeight, mPaint);
        canvas.drawLine(mTextBounds.left, mTextBounds.top, mTextBounds.left, mHeight, mPaint);
        canvas.drawLine(mTextBounds.right, mTextBounds.bottom, mTextBounds.right, mHeight, mPaint);
        canvas.drawLine(mTextBounds.left, mTextBounds.bottom, mTextBounds.right, mTextBounds.bottom, mPaint);

        // 效果：坐标原点右上方显示（baseline, mTextBounds.left > 0）
        mPaint.setColor(Color.WHITE);
        canvas.drawText(TEXT, 0, 0, mPaint);

        // 效果：以坐标原点(0, 0)为起始点，左部及顶部与坐标原点对齐
        mPaint.setColor(Color.RED);
        canvas.drawText(TEXT, -mTextBounds.left, mTextBounds.height() - mTextBounds.bottom, mPaint);

        // 效果：以坐标原点(0, 0)为中心点
        mPaint.setColor(Color.GREEN);
        canvas.drawText(TEXT, -mTextBounds.centerX(), mTextBounds.height() / 2 - mTextBounds.bottom, mPaint);

        canvas.restore();
    }
}
