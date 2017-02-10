package com.shoo.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

    private final List<Integer> mList = new ArrayList<>();

    public MyRecyclerAdapter(){
        for (int i = 0; i < 8; i++) {
            mList.add(mColors[i % (mColors.length - 1)]);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        TextView textView = new TextView(context);
        textView.setLayoutParams(new ViewGroup.LayoutParams(/*ViewGroup.LayoutParams
        .MATCH_PARENT*/600, 600));
        textView.setGravity(Gravity.CENTER);
        textView.setText("list item");
        textView.setBackgroundColor(context.getResources().getColor(viewType));
        textView.setOnClickListener(this);
        return new MyViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "item clicked", Toast.LENGTH_SHORT).show();
    }

    public void sendToBack(int position) {
        if (position >= 0 && position < mList.size()) {
            Integer color = mList.remove(position);
            mList.add(color);
            notifyDataSetChanged();
        }
    }
}
