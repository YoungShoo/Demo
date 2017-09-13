package com.shoo.demo.components.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Shoo on 17-9-13.
 */

public class TBindService {

    private static final String TAG = "TBindService";

    public static void test(final Activity activity) {
        bindServiceDirectly(activity);
//        bindServiceWidthLock(activity);
    }

    private static void bindServiceDirectly(final Activity activity) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                /*
                非同步（阻塞）绑定

                TBindService: test: thread = Thread-2942
                TBindService: test: last ...
                TBindService: onServiceConnected: thread = main
                 */
                Log.d(TAG, "test: thread = " + Thread.currentThread().getName());
                activity.bindService(new Intent(activity, SService.class), new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        Log.d(TAG, "onServiceConnected: thread = " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                }, Context.BIND_AUTO_CREATE);

                Log.d(TAG, "test: last ...");
            }
        }.start();
    }

    private static void bindServiceWidthLock(final Activity activity) {
        new Thread() {
            @Override
            public void run() {
                super.run();

                /*
                 TBindService: test: thread = Thread-2950
                 TBindService: onServiceConnected: thread = main
                 TBindService: test: last ...
                 */
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                Log.d(TAG, "test: thread = " + Thread.currentThread().getName());
                activity.bindService(new Intent(activity, SService.class), new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        Log.d(TAG, "onServiceConnected: thread = " + Thread.currentThread().getName());
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                }, Context.BIND_AUTO_CREATE);
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "test: last ...");
            }
        }.start();
    }

}
