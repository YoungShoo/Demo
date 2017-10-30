package com.shoo.demo.widget.scrollview;

import android.app.Activity;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.shoo.demo.R;
import com.shoo.demo.tester.Tester;

/**
 * Created by Shoo on 16-10-22.
 */
public class TScrollView extends Tester {

    public TScrollView(Activity activity) {
        super(activity);
    }

    @Override
    protected void test() {
        mActivity.setContentView(R.layout.scroll_view);
        final ScrollView scrollView = (ScrollView) mActivity.findViewById(R.id.scroll_view);
        final TextView textView = (TextView) mActivity.findViewById(R.id.text_view);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                String text = "TextView: " + textView.getMeasuredHeight()
                        + ", ScrollView: " + scrollView.getMeasuredHeight();
                Toast.makeText(mActivity, text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
