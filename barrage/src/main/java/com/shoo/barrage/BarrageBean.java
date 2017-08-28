package com.shoo.barrage;

import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

/**
 * Created by Shoo on 17-8-22.
 */

public class BarrageBean implements Comparable<BarrageBean>, BarrageState {

    private int mX;
    private int mY;
    private String mContent;
    @ColorInt private int mTextColor;
    @ColorInt private int mBgColor;
    private long mDuration;
    @BarrageState.BarrageStateDef private int mState;
    private int mDrawingAlpha;

    public BarrageBean() {
        mState = READY;
    }

    public int getX() {
        return mX;
    }

    public void setX(int x) {
        mX = x;
    }

    public int getY() {
        return mY;
    }

    public void setY(int y) {
        mY = y;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(@ColorInt int textColor) {
        mTextColor = textColor;
    }

    public int getBgColor() {
        return mBgColor;
    }

    public void setBgColor(@ColorInt int bgColor) {
        mBgColor = bgColor;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public int getState() {
        return mState;
    }

    public void setState(@BarrageState.BarrageStateDef int state) {
        mState = state;
    }

    public int getDrawingAlpha() {
        return mDrawingAlpha;
    }

    public void setDrawingAlpha(int drawingAlpha) {
        mDrawingAlpha = drawingAlpha;
    }

    @Override
    public int compareTo(@NonNull BarrageBean that) {
        return this.getY() - that.getY();
    }

    @Override
    public String toString() {
        return "BarrageItemBean{" +
                "mX=" + mX +
                ", mY=" + mY +
                ", mContent='" + mContent + '\'' +
                ", mTextColor=" + mTextColor +
                ", mBgColor=" + mBgColor +
                ", mDuration=" + mDuration +
                ", mState=" + mState +
                ", mDrawingAlpha=" + mDrawingAlpha +
                '}';
    }

    public boolean resolveBounds(Rect bounds) {
        return mX >= bounds.left + 36 && mX <= bounds.right - 36 && mY >= bounds.top && mY <= bounds.bottom;
    }

    public boolean resolveState() {
        return READY == mState || BUBBLING == mState || FINISHED == mState;
    }

    public void setupNextAlpha(long interval) {
        if (mDrawingAlpha >= 255) {
            return;
        }

        float increment = 255.0f * interval / mDuration;
        int nextAlpha = (int) (mDrawingAlpha + increment);
        if (nextAlpha > 255) {
            nextAlpha = 255;
        }
        mDrawingAlpha = nextAlpha;
    }

    public void setupNextState() {
        if (mDrawingAlpha <= 0) {
            setState(READY);
        } else if (mDrawingAlpha < 255) {
            setState(BUBBLING);
        } else {
            setState(FINISHED);
        }
    }

    public void reset() {
        setState(BarrageState.READY);
        setDrawingAlpha(0);
    }
}
