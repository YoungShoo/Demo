package com.shoo.demo.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shoo on 16-10-24.
 */
public class TextAdapter extends BaseAdapter {

    private List<String> mList = new ArrayList<>();
    private int[] mColors = {
            android.R.color.holo_red_light,
            android.R.color.holo_green_light,
            android.R.color.holo_blue_light,
            android.R.color.holo_orange_light,
    };

    {
        mList.add("first_item");
        mList.add("second_item");
        mList.add("third_item");
        mList.add("fourth_item");
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        TextView textView = new TextView(context);
        textView.setText(mList.get(position));
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(context.getResources().getColor(mColors[position]));
        return textView;
    }
}
