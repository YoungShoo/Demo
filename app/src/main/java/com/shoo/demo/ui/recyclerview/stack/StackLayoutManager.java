package com.shoo.demo.ui.recyclerview.stack;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Shoo on 16-12-25.
 */

public class StackLayoutManager extends RecyclerView.LayoutManager {

    // 显示卡片数量
    public static final int STACK_ITEM_COUNT = 4;
    // 底部卡片缩放系数
    public static final float SCALE_FACTOR = 0.04f;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int itemCount = getItemCount();
        if (itemCount <= 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }

        detachAndScrapAttachedViews(recycler);
        // 卡片数量
        int bottomIndex = itemCount > STACK_ITEM_COUNT ? STACK_ITEM_COUNT : itemCount;
        // 由底部至顶部布局卡片
        for (int i = bottomIndex - 1; i >= 0; --i) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            // 卡片居中显示
            int decoratedMeasuredWidth = getDecoratedMeasuredWidth(view);
            int decoratedMeasuredHeight = getDecoratedMeasuredHeight(view);
            int widthSpace = getWidth() - decoratedMeasuredWidth;
            int heightSpace = getHeight() - decoratedMeasuredHeight;
            int left = widthSpace / 2;
            int top = heightSpace / 2;
            int right = left + decoratedMeasuredWidth;
            int bottom = top + decoratedMeasuredHeight;
            layoutDecorated(view, left, top, right, bottom);
            int index = i;
            // 底部卡片与倒数第二项重叠，以处理跟手滑动顶部卡片，倒数第二项上浮时，底部仍有卡片显示
            if (index == bottomIndex - 1 && bottomIndex > 1) {
                index = bottomIndex - 2;
            }
            // 卡片缩放
            float scale = 1 - index * SCALE_FACTOR;
            view.setScaleX(scale);
            view.setScaleY(scale);
            // 往下平移，以显示卡片底部边缘，达到卡片重叠效果
            view.setTranslationY((1 - scale) * view.getMeasuredHeight() * 2);
        }
    }
}
