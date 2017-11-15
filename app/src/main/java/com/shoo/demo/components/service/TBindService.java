package com.shoo.demo.components.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.CountDownLatch;

/**
 * IPC 客户端操作是阻塞的，就算没有返回值
 * bindService / startService 是非阻塞的，不需要等待 onBind 回调
 *
 * Created by Shoo on 17-9-13.
 */

public class TBindService {

    private static final String TAG = "TBindService";

    private static IRemoteService sRemoteService;
    private static ServiceConnection sConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            /*
              onServiceConnected:
              curPid = 27740, binder = android.os.BinderProxy@d7a8369,
              remotePid = 27755, sRemoteService = com.shoo.demo.components.service.IRemoteService$Stub$Proxy@d6a21ee
             */

            sRemoteService = IRemoteService.Stub.asInterface(binder);
            int pid = -1;
            try {
                pid = sRemoteService.getPid();
                long start = System.currentTimeMillis();
                // IPC 客户端操作是同步，尽管没有返回值
                // TBindService: onServiceConnected: duration = 2002
                sRemoteService.basicTypes(0, 0L, false, 0f, 0d, null);
                Log.d(TAG, "onServiceConnected: duration = " + (System.currentTimeMillis() - start));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "onServiceConnected: curPid = " + Process.myPid()+ ", binder = " + binder
                    + ", remotePid = " + pid + ", sRemoteService = " + sRemoteService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            sRemoteService = null;
        }
    };

    public static void test(final Activity activity) {
        Button button = new Button(activity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindServiceDirectly(activity);
            }
        });
        button.setText("Click");

        activity.setContentView(button);
//        bindServiceWithLock(activity);
//        testServiceLifeCycle(activity);
    }

    private static void bindServiceDirectly(final Activity activity) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                /*
                非同步（阻塞）绑定
                TBindService: test: thread = Thread-2679, time = 487901735
                TBindService: test: last ...              time = 487901741
                TBindService: onServiceConnected: thread = main
                 */
                Log.d(TAG, "test: thread = " + Thread.currentThread().getName() + ", time = " + SystemClock.uptimeMillis());
                Intent service = new Intent(activity, SService.class);
//                activity.unbindService(sConn);
                activity.bindService(service, sConn, Context.BIND_AUTO_CREATE);

                /*
                非阻塞
                TBindService: test: thread = Thread-2693, time = 488035899
                TBindService: test: last ...              time = 488035924
                */
//                activity.startService(service);
                Log.d(TAG, "test: last ... time = " + SystemClock.uptimeMillis());
            }
        }.start();
    }

    private static void bindServiceWithLock(final Activity activity) {
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

    private static void testServiceLifeCycle(final Activity activity) {
        final Intent intent = new Intent(activity, SService.class);

        /*
        SService: onCreate() called
        SService: onBind() called with: intent = [Intent { cmp=com.shoo.demo/.components.service.SService }]
        SService: onStartCommand()
        SService: onStart() called with: intent = [Intent { cmp=com.shoo.demo/.components.service.SService }], startId = [1]
        SService: onUnbind() called with: intent = [Intent { cmp=com.shoo.demo/.components.service.SService }]
        SService: onDestroy() called
         */
        /*
        activity.bindService(intent, sConn, Context.BIND_AUTO_CREATE);
        activity.startService(intent);
        activity.stopService(intent);
        activity.unbindService(sConn);
        */

        /*
        SService: onCreate()
        SService: onStartCommand()
        SService: onStart() called with: intent = [Intent { cmp=com.shoo.demo/.components.service.SService }], startId = [1]
        SService: onBind() called with: intent = [Intent { cmp=com.shoo.demo/.components.service.SService }]
        SService: onUnbind() called with: intent = [Intent { cmp=com.shoo.demo/.components.service.SService }]
        SService: onRebind() called: when service is still running and onUnbind() return true
         */
        activity.startService(intent);

        activity.bindService(intent, sConn, Context.BIND_AUTO_CREATE);
        activity.unbindService(sConn);
        activity.bindService(intent, sConn, Context.BIND_AUTO_CREATE);

        activity.stopService(intent);
    }

}
