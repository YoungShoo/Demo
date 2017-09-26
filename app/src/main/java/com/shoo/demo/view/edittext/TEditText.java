package com.shoo.demo.view.edittext;

import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Shoo on 17-9-26.
 */

public class TEditText {

    private static final String TAG = "TEditText";

    public static void test(Activity activity) {
        EditText editText = new EditText(activity);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // ViewRootImpl.dispatchInputEvent ...
                // key back event should be listened by overriding EditText.onKeyPreIme
                Log.d(TAG, "onEditorAction: v = [" + v + "], actionId = [" + actionId + "], event = [" + event + "]");
                return false;
            }
        });

        activity.setContentView(editText);
    }
}
