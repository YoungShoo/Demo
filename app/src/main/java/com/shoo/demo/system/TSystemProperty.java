package com.shoo.demo.system;

import android.app.Activity;
import android.util.Log;

/**
 * Created by Shoo on 17-9-18.
 */

public class TSystemProperty {

    private static final String TAG = "TSystemProperty";

    private static final String PROPERTY_NAME = "app";

    public static void test(Activity activity) {
        System.setProperty(PROPERTY_NAME, "app_before");
        Log.d(TAG, "before: " + System.getProperty(PROPERTY_NAME));
        System.setProperty(PROPERTY_NAME, "app_after");
        Log.d(TAG, "after: " + System.getProperty(PROPERTY_NAME));
    }

}
