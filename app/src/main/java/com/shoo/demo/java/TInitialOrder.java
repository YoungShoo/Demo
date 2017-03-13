package com.shoo.demo.java;

import android.util.Log;

/**
 * Created by Shoo on 17-2-17.
 */

public class TInitialOrder {

    private static final String TAG = "TInitialOrder";

    public static String sStr = "field";
    public static String sStrBeforeStaticBlock = sStr;

    static {
        Log.d(TAG, "before block initial: sStr = " + sStr);
        sStr = "block";
        Log.d(TAG, "after block initial: sStr = " + sStr);
    }

    public static String sStrAfterStaticBlock = sStr;

    public static void test() {
        /**
         * D/TInitialOrder(13206): before block initial: sStr = field
         * D/TInitialOrder(13206): after block initial: sStr = block
         * D/TInitialOrder(13206): test: sStr = block
         * D/TInitialOrder(13206): test: sStrBeforeStaticBlock = field
         * D/TInitialOrder(13206): test: sStrAfterStaticBlock = block
         */
        Log.d(TAG, "test: sStr = " + sStr);
        Log.d(TAG, "test: sStrBeforeStaticBlock = " + sStrBeforeStaticBlock);
        Log.d(TAG, "test: sStrAfterStaticBlock = " + sStrAfterStaticBlock);
    }
}
