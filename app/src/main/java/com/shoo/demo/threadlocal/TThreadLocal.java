package com.shoo.demo.threadlocal;

import android.app.Activity;
import android.os.Looper;
import android.util.Log;

import com.shoo.demo.tester.Tester;

/**
 * Created by Shoo on 16-10-22.
 */
public class TThreadLocal extends Tester {

    private static final String TAG = "TThreadLocal";

    ThreadLocal<String> mThreadLocal = new ThreadLocal<>();

    public TThreadLocal(Activity activity) {
        super(activity);
    }

    @Override
    protected void test() {
        mThreadLocal.set("main thread");
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Log.d(TAG, "async thread, before: " + mThreadLocal.get());
                mThreadLocal.set("asyncThread");
                Log.d(TAG, "async thread, after: " + mThreadLocal.get());
                Looper.loop();
            }
        }.start();

        Log.d(TAG, "main thread: " + mThreadLocal.get());

        mActivity.getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "main thread, delay: " + mThreadLocal.get());
            }
        }, 200);

        /**
         * 异步线程重置mThreadLocal的值，主线程设置的值不受影响
         *
         * D/TThreadLocal( 3327): main thread: main thread
         * D/TThreadLocal( 3327): async thread, before: null
         * D/TThreadLocal( 3327): async thread, after: asyncThread
         * D/TThreadLocal( 3327): main thread, delay: main thread
         */
    }
}
