package com.shoo.demo.recyclerview.stack;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

/**
 * Created by Shoo on 16-12-26.
 */

public class StackSwipeCallback extends ItemTouchHelper.SimpleCallback {

    private static final String TAG = "StackSwipeCallback";

    // 顶部卡片最大旋转角度
    private static final float MAX_ROTATE_DELTA = 25f;

    private SwipeCallback mSwipeCallback;

    public StackSwipeCallback() {
        // 上下左右都可以滑动删除
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP |
                ItemTouchHelper.DOWN);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        viewHolder.itemView.setRotation(0);
        if (mSwipeCallback != null) {
            mSwipeCallback.onSwiped(viewHolder, direction);
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        int childCount = recyclerView.getChildCount();
        if (childCount <= 0) {
            return;
        }

        // 卡片平移系数：以列表宽度的一半作为临界值
        double distance = Math.abs(dX);
        double disFactor = distance / (recyclerView.getMeasuredWidth() / 2);
        if (disFactor > 1.0) {
            disFactor = 1.0;
        }

        // 顶层卡片旋转
        View topView = recyclerView.getChildAt(childCount - 1);
        float rotation = (float) (disFactor * MAX_ROTATE_DELTA);
        // 向左滑动
        if (dX < 0) {
            rotation = -rotation;
        }
        topView.setRotation(rotation);

        // 底层卡片上浮
        // 最底部作为新的卡片项显示，不需要移动
        int bottomIndex = 1;
        // 最顶部是正在拖动的卡片，不需要处理平移缩放
        int topIndex = childCount - 1;
        for (int i = bottomIndex; i < topIndex; i++) {
            View child = recyclerView.getChildAt(i);
            // 缩放
            float scaleFactor = StackLayoutManager.SCALE_FACTOR;
            double scale = 1 - (topIndex - i) * scaleFactor + disFactor * scaleFactor;
            child.setScaleX((float) scale);
            child.setScaleY((float) scale);
            // 平移
            float translationY = (float) ((1 - scale) * child.getMeasuredHeight() * 2);
            child.setTranslationY(translationY);
        }
    }

    public void setSwipeCallback(SwipeCallback swipeCallback) {
        mSwipeCallback = swipeCallback;
    }

    public interface SwipeCallback {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction);
    }
}
