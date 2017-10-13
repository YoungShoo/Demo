package com.shoo.demo.ui.view;

import android.app.Activity;
import android.view.ViewGroup;

import com.shoo.demo.tester.Tester;

/**
 * Created by Shoo on 16-10-22.
 */
public class TView extends Tester {

    public TView(Activity activity) {
        super(activity);
    }

    @Override
    protected void test() {
        TextBoundsView myView = new TextBoundsView(mActivity);
        myView.setBackgroundColor(mActivity.getResources().getColor(android.R.color.holo_red_light));
        myView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600));
        mActivity.setContentView(myView);
    }

}
