package com.shoo.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shoo.demo.textview.MyTextView;
import com.shoo.demo.util.AnimUtils;

/**
 * Created by Shoo on 16-11-6.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener {

    private int[] mColors = {
            android.R.color.holo_red_light,
            android.R.color.holo_green_light,
            android.R.color.holo_blue_light,
            android.R.color.holo_orange_light,
    };

    @Override
    public int getItemViewType(int position) {
        return position % mColors.length;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        MyTextView textView = new MyTextView(context);
        textView.setText("list item");
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                200));
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(context.getResources().getColor(mColors[viewType]));
        textView.setOnClickListener(this);
        return new MyViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onClick(View v) {
//        AnimUtils.performDeleteAnim(v);
        v.requestLayout();
        v.requestLayout();
        v.requestLayout();
    }
}
