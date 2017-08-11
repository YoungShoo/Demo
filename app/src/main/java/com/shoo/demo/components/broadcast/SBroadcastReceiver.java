package com.shoo.demo.components.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Shoo on 17-8-9.
 */

public class SBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
    }
}
