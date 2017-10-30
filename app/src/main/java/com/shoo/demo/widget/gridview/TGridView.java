package com.shoo.demo.widget.gridview;

import android.app.Activity;
import android.view.Gravity;
import android.widget.GridView;

import com.shoo.demo.adapter.TextAdapter;

/**
 * Created by Shoo on 17-2-13.
 */

public class TGridView {

    public static void test(Activity activity) {
        GridView gridView = new GridView(activity);
        gridView.setNumColumns(3);
        gridView.setColumnWidth(200);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setAdapter(new TextAdapter());
        gridView.setGravity(Gravity.CENTER);
        activity.setContentView(gridView);
    }

}
