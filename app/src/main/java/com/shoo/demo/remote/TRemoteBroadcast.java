package com.shoo.demo.remote;

import android.app.Activity;
import android.content.Intent;

import com.shoo.demo.SecondActivity;

/**
 * Created by Shoo on 17-9-13.
 */

public class TRemoteBroadcast {

    private static final String ACTION = "com.shoo.action.BROADCAST";

    public static void test(Activity activity) {
        Intent intent = new Intent(activity, SecondActivity.class);
        activity.startActivity(intent);

//        sendBroadcast(activity);
    }

    public static void sendBroadcast(Activity activity) {
        /*
        SBroadcastReceiver: onReceive() called with: context = [android.app.ReceiverRestrictedContext@469ef4d], intent = [Intent { act=com.shoo.action.BROADCAST flg=0x10 cmp=com.shoo.demo/.components.broadcast.SBroadcastReceiver }], process = 4735
        SBroadcastReceiver2: onReceive() called with: context = [android.app.ReceiverRestrictedContext@746067c], intent = [Intent { act=com.shoo.action.BROADCAST flg=0x10 cmp=com.shoo.demo/.components.broadcast.SBroadcastReceiver2 }], process = 4721
         */
        Intent broadcastIntent = new Intent(ACTION);
        activity.sendBroadcast(broadcastIntent);
    }
}
