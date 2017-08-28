package com.shoo.barrage;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Shoo on 17-8-22.
 */

public class BarrageView extends View implements IBarrageView, SwitchBtnDrawer.OnSwitchBtnClickListener {

    private final View mTargetView;
    private ActionHandler mActionHandler;
    private BarrageDrawer mBarrageDrawer;
    private SwitchBtnDrawer mSwitchBtnDrawer;
    private DataHolder mDataHolder;
    private boolean mIsAttachedToTargetView;
    private boolean mIsBarrageShown;

    public BarrageView(View targetView) {
        super(targetView.getContext());
        mTargetView = targetView;
        mActionHandler = new ActionHandler(this);

        mBarrageDrawer = new BarrageDrawer();
        mSwitchBtnDrawer = new SwitchBtnDrawer(this, this);

        mDataHolder = new DataHolder();
        mDataHolder.mock();

        mIsAttachedToTargetView = false;
        setBarrageShown(false);
        setClickable(true);
    }

    @Override
    public void onSwitchBtnClicked() {
        // reset data to redisplay barrages when barrages are shown again
        /*if (!mIsBarrageShown) {
            mDataHolder.reset();
        }*/

        if (mIsBarrageShown) {
            hide();
        } else {
            show();
        }
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (isShown() && mIsBarrageShown) {
            resume();
        } else if (!isShown() && !mIsBarrageShown) {
            pause();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mSwitchBtnDrawer.handleTouchEvent(event);
    }

    @Override
    public void start() {
        ensureAttachState();
        setBarrageShown(true);
        mActionHandler.start();
    }

    private void ensureAttachState() {
        if (!mIsAttachedToTargetView) {
            attachToTargetView();
            mIsAttachedToTargetView = true;
        }
    }

    private void attachToTargetView() {
        Log.d(Constants.LOG_TAG, "attachToTargetView " + "");
        if (mTargetView.getParent() instanceof FrameLayout) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
//            params.width = mTargetView.getMeasuredWidth();
//            params.height = mTargetView.getMeasuredHeight();
            ((FrameLayout) mTargetView.getParent()).addView(this, params);
            bringToFront();
        } else {
            // TODO: 17-8-28 其他布局处理
        }
    }

    @Override
    public void resume() {
        mActionHandler.resume();
    }

    @Override
    public void pause() {
        mActionHandler.pause();
    }

    @Override
    public void stop() {
        setBarrageShown(false);
        mActionHandler.stop();
    }

    @Override
    public void show() {
        setBarrageShown(true);
        mActionHandler.start();
    }

    @Override
    public void hide() {
        setBarrageShown(false);
        mActionHandler.stop();
    }

    @Override
    public void release() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mSwitchBtnDrawer.resetBounds();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mSwitchBtnDrawer.offsetBounds(l - oldl, t - oldt);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIsBarrageShown) {
            mBarrageDrawer.draw(canvas, mDataHolder.getBarrages());
        }
        mSwitchBtnDrawer.draw(canvas);
    }

    public void setBarrageShown(boolean barrageShown) {
        mIsBarrageShown = barrageShown;
        mSwitchBtnDrawer.setSwitchState(barrageShown);
    }
}
