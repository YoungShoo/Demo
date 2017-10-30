package com.shoo.demo.widget.recyclerview;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shoo.demo.tester.Tester;
import com.shoo.demo.adapter.MyRecyclerAdapter;

/**
 * Created by Shoo on 16-11-6.
 */

public class TRecyclerView extends Tester {

    public TRecyclerView(Activity activity) {
        super(activity);
    }

    @Override
    protected void test() {
        final RecyclerView recyclerView = new LoadMoreRecyclerView(mActivity);
        mActivity.setContentView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        final MyRecyclerAdapter adapter = new MyRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
//                recyclerView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter.notifyDataSetChanged();
//                    }
//                });
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }, 1000);
    }
}
