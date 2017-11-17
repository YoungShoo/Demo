package com.shoo.demo.widget;

import android.app.Activity;
import android.widget.ListView;

import com.shoo.demo.adapter.TextAdapter;

/**
 * Created by Shoo on 17-11-14.
 */

public class TListView {

    public static void test(Activity activity) {
        ListView listView = new ListView(activity);
        listView.setAdapter(new TextAdapter());
        activity.setContentView(listView);
    }

}
