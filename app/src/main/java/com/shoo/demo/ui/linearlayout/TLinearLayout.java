package com.shoo.demo.ui.linearlayout;

import android.app.Activity;

import com.shoo.demo.R;
import com.shoo.demo.tester.Tester;

/**
 * Created by Shoo on 16-10-22.
 */
public class TLinearLayout extends Tester {

    public TLinearLayout(Activity activity) {
        super(activity);
    }

    @Override
    protected void test() {
        mActivity.setContentView(R.layout.linear_layout);
    }
}
