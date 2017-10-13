package com.shoo.demo.ui.recyclerview.stack;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Shoo on 16-11-6.
 */

public class TStackRecyclerView {

    public static void test(Activity activity) {
        final RecyclerView recyclerView = new RecyclerView(activity);
        activity.setContentView(recyclerView);

        // 卡片布局
        recyclerView.setLayoutManager(new StackLayoutManager());
        final StackRecyclerAdapter adapter = new StackRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        // 滑动监听
        StackSwipeCallback callback = new StackSwipeCallback();
        callback.setSwipeCallback(new StackSwipeCallback.SwipeCallback() {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // 删除的卡片（数据）移至底部循环利用，以达到卡片循环的效果
                adapter.sendToBack(viewHolder.getAdapterPosition());
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
