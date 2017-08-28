package com.shoo.barrage;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Shoo on 17-8-28.
 */

public class SwitchBtnDrawer {

    public interface OnSwitchBtnClickListener {
        void onSwitchBtnClicked();
    }

    private static final int RADIUS = 64;
    private static final int MARGIN = 40;
    private static final int TEXT_SIZE = 60;
    private static final int STROKE_WIDTH = 5;
    private static final String TEXT = "弹";

    private final View mAnchorView;
    private final OnSwitchBtnClickListener mListener;
    private Paint mPaint;
    private Rect mBtnBounds;
    private Rect mTextBounds;
    private boolean mIsHandled;
    private boolean mIsPressed;
    private boolean mIsSwitchOn;

    SwitchBtnDrawer(View anchorView, OnSwitchBtnClickListener listener) {
        mAnchorView = anchorView;
        mListener = listener;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(TEXT_SIZE);
        mPaint.setStrokeWidth(STROKE_WIDTH);

        mBtnBounds = new Rect();
        mTextBounds = new Rect();
        mPaint.getTextBounds(TEXT, 0, TEXT.length(), mTextBounds);

        mIsHandled = false;
        mIsPressed = false;
        mIsSwitchOn = false;
    }

    void resetBounds() {
        int centerX = mAnchorView.getMeasuredWidth() + mAnchorView.getScrollX() - RADIUS - MARGIN;
        int centerY = mAnchorView.getMeasuredHeight() + mAnchorView.getScrollY() - RADIUS - MARGIN;
        mBtnBounds.set(centerX - RADIUS, centerY - RADIUS, centerX + RADIUS, centerY + RADIUS);
    }

    void offsetBounds(int dx, int dy) {
        mBtnBounds.offset(dx, dy);
    }

    void draw(Canvas canvas) {
        // draw background
        int bgColor = mIsSwitchOn
                ? (mIsPressed ? Color.GRAY : Color.YELLOW)
                : (mIsPressed ? Color.GRAY : Color.LTGRAY);
        mPaint.setColor(bgColor);
        canvas.drawCircle(mBtnBounds.centerX(), mBtnBounds.centerY(), RADIUS, mPaint);

        // draw text
        mPaint.setColor((mIsSwitchOn && !mIsPressed) ? Color.BLACK : Color.WHITE);
        int x = mBtnBounds.centerX() - mTextBounds.centerX();
        int y = mBtnBounds.centerY() + mTextBounds.height() / 2 - mTextBounds.bottom;
        canvas.drawText(TEXT, x, y, mPaint);
    }

    void setSwitchState(boolean on) {
        mIsSwitchOn = on;
    }

    boolean handleTouchEvent(MotionEvent event) {
        Log.d(Constants.LOG_TAG, "onTouch: event = " + event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsHandled = mBtnBounds.contains(((int) event.getX()) + mAnchorView.getScrollX(),
                        ((int) event.getY()) + mAnchorView.getScrollY());
                mIsPressed = mIsHandled;
                if (mIsHandled) {
                    mAnchorView.invalidate();
                }
                return mIsHandled;
            case MotionEvent.ACTION_MOVE:
                if (!mBtnBounds.contains(((int) event.getX()) + mAnchorView.getScrollX(),
                        ((int) event.getY()) + mAnchorView.getScrollY())) {
                    mIsHandled = false;
                    return false;
                }
                return true;
            case MotionEvent.ACTION_UP:
                mIsPressed = false;
                mAnchorView.invalidate();
                if (mIsHandled) {
                    mListener.onSwitchBtnClicked();
                    return true;
                }
                return false;
            case MotionEvent.ACTION_CANCEL:
                mAnchorView.invalidate();
                mIsPressed = false;
                return false;
            default:
                return false;
        }
    }
}
