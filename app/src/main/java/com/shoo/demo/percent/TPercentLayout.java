package com.shoo.demo.percent;

import android.app.Activity;

import com.shoo.demo.R;
import com.shoo.demo.tester.Tester;

/**
 * Created by Shoo on 16-10-22.
 */
public class TPercentLayout extends Tester {

    public TPercentLayout(Activity activity) {
        super(activity);
    }

    @Override
    protected void test() {
        mActivity.setContentView(R.layout.percent_layout);
    }
}
