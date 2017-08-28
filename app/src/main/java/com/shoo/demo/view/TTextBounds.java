package com.shoo.demo.view;

import android.app.Activity;
import android.view.ViewGroup;

/**
 * Created by Shoo on 17-8-28.
 */

public class TTextBounds {

    public static void test(Activity activity) {
        TextBoundsView view = new TextBoundsView(activity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT);
        activity.setContentView(view, params);
    }

}
