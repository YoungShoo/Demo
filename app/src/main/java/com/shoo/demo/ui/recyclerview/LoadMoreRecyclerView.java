package com.shoo.demo.ui.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

/**
 * Created by Shoo on 17-2-10.
 */

public class LoadMoreRecyclerView extends RecyclerView {

    private float mStartY;
    private boolean mIsPullUp;

    public LoadMoreRecyclerView(Context context) {
        super(context);
        init();
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        addOnScrollListener(getLoadMoreListener());
    }

    @NonNull
    private OnScrollListener getLoadMoreListener() {
        return new OnScrollListener() {

            private int mLastState = RecyclerView.SCROLL_STATE_IDLE;
            private boolean mNoScrollAction;
            private int mOffset = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (switchedToIdleState(newState) && isAtBottom() && isActionLoadMore()/* && mIsPullUp*/) {
                    if (!isLoadingMore()) {
                        Toast.makeText(getContext(), "开始执行加载更多", Toast.LENGTH_SHORT).show();
                    }
                }
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    mNoScrollAction = true;
                    mOffset = 0;
                }
                mLastState = newState;
            }

            private boolean isActionLoadMore() {
                // 如果非强制无上下滑动才触发加载，则可以通过偏移为0进行判断
//                return mOffset == 0;
                return mNoScrollAction;
            }

            private boolean switchedToIdleState(int newState) {
                return RecyclerView.SCROLL_STATE_IDLE != mLastState && RecyclerView.SCROLL_STATE_IDLE == newState;
            }

            private boolean isAtBottom() {
                // 得到当前显示的最后一个item的view
                View lastChildView = getLayoutManager().getChildAt(getLayoutManager().getChildCount() - 1);
                // 得到lastChildView的bottom坐标值
                int lastChildBottom = lastChildView.getBottom();
                // 得到RecyclerView的底部坐标减去底部padding值，也就是显示内容最底部的坐标
                int recyclerBottom = getBottom() - getPaddingBottom();
                //通过这个lastChildView得到这个view当前的position值
                int lastPosition = getLayoutManager().getPosition(lastChildView);

                // 判断lastChildView的bottom值跟recyclerBottom
                // 判断lastPosition是不是最后一个position
                // 如果两个条件都满足则说明是真正的滑动到了底部
                return lastChildBottom == recyclerBottom && lastPosition == getLayoutManager().getItemCount() - 1;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // 如果列表在底部，且没有上下滑动，这里不会有回调
                // 因为，也可以通过是否有回调这个方式(且dy == 0)，限制仅在无上下滑动时，才触发加载动作
                mNoScrollAction = false;
                mOffset += dy;
            }
        };
    }

    private boolean isLoadingMore() {
        // TODO: 添加条件，过滤重复触发加载
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mStartY = event.getY();
                mIsPullUp = false;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                float curY = event.getY();
                if (mStartY - curY >= getTouchSlop()) {
                    mIsPullUp = true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public int getTouchSlop() {
        return ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }
}
