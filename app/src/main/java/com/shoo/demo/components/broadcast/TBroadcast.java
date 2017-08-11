package com.shoo.demo.components.broadcast;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.util.List;

/**
 * Created by Shoo on 17-8-9.
 */

public class TBroadcast {

    private static final String TAG = "TBroadcast";

    public static void test(Activity activity) {
        testAsyncRegister(activity);
    }

    private static void testAsyncRegister(final Activity activity) {
        // It's OK to register on async thread!
        new Thread() {
            @Override
            public void run() {
                IntentFilter filter = new IntentFilter(Intent.ACTION_SEND);
                activity.registerReceiver(new SBroadcastReceiver(), filter);
                Log.d(TAG, "registerReceiver on async thread done!");

                Intent intent = new Intent(Intent.ACTION_SEND);
                List list = activity.getPackageManager().queryBroadcastReceivers(intent, 0);
                Log.d(TAG, "list = " + list);
            }
        }.start();
    }
}
