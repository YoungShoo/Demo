package com.shoo.demo.java;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Shoo on 17-3-15.
 */
public class TClassAndObjectLock {

    private static final String TAG = "TClassAndObjectLock";
    private static final long SLEEP_TIME = 2000L;

    public static void test() {

        ExecutorService executorService = Executors.newFixedThreadPool(14);

        /**
         结论：
         1. 单实例的普通方法不加锁，多线程可以同时访问：nonSyncFunc 同时执行；
         2. 同一对象的两个方法加锁，相互间存在竞争关系：objFunc1、objFunc2先后间隔执行；
         3. 静态方法锁跟类锁都存在竞争关系：staticFunc / objClsFunc / staticClsFunc 先后间隔执行两次；
         4. 静态方法锁跟类锁彼此间存在竞争关系：staticFunc、objClsFunc、staticClsFunc 相互间先后间隔执行；
         5. 这两种锁仅跟类相关，与类实例（对象）方法锁不存在竞争关系：objFunc1、staticFunc 同时执行；
         */

        /**
         验证：1、2
         03-17 00:02:09.433 D/TClassAndObjectLock( 4261): objFunc1 start: thread = pool-1-thread-1
         03-17 00:02:09.434 D/TClassAndObjectLock( 4261): nonSyncFunc start: thread = pool-1-thread-2
         03-17 00:02:09.436 D/TClassAndObjectLock( 4261): nonSyncFunc start: thread = pool-1-thread-4
         03-17 00:02:11.433 D/TClassAndObjectLock( 4261): objFunc1 end: thread = pool-1-thread-1
         03-17 00:02:11.434 D/TClassAndObjectLock( 4261): objFunc2 start: thread = pool-1-thread-3
         03-17 00:02:11.434 D/TClassAndObjectLock( 4261): nonSyncFunc end: thread = pool-1-thread-2
         03-17 00:02:11.436 D/TClassAndObjectLock( 4261): nonSyncFunc end: thread = pool-1-thread-4
         03-17 00:02:13.434 D/TClassAndObjectLock( 4261): objFunc2 end: thread = pool-1-thread-3
         */
        /*executorService.submit(new Runnable() {
            @Override
            public void run() {
                TClassAndObjectLock.getInstance().objFunc1();
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                TClassAndObjectLock.getInstance().nonSyncFunc();
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                TClassAndObjectLock.getInstance().objFunc2();
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                TClassAndObjectLock.getInstance().nonSyncFunc();
            }
        });*/

        /**
         验证：3、4、5
         03-17 00:03:23.794 D/TClassAndObjectLock( 4434): objFunc1 start: thread = pool-1-thread-1
         03-17 00:03:23.796 D/TClassAndObjectLock( 4434): staticFunc start: thread = pool-1-thread-5
         03-17 00:03:25.794 D/TClassAndObjectLock( 4434): objFunc1 end: thread = pool-1-thread-1
         03-17 00:03:25.796 D/TClassAndObjectLock( 4434): objFunc1 start: thread = pool-1-thread-2
         03-17 00:03:25.797 D/TClassAndObjectLock( 4434): staticFunc end: thread = pool-1-thread-5
         03-17 00:03:25.797 D/TClassAndObjectLock( 4434): staticFunc start: thread = pool-1-thread-6
         03-17 00:03:27.796 D/TClassAndObjectLock( 4434): objFunc1 end: thread = pool-1-thread-2
         03-17 00:03:27.797 D/TClassAndObjectLock( 4434): objFunc2 start: thread = pool-1-thread-3
         03-17 00:03:27.798 D/TClassAndObjectLock( 4434): staticFunc end: thread = pool-1-thread-6
         03-17 00:03:27.798 D/TClassAndObjectLock( 4434): objClsFunc start: thread = pool-1-thread-7
         03-17 00:03:29.798 D/TClassAndObjectLock( 4434): objFunc2 end: thread = pool-1-thread-3
         03-17 00:03:29.799 D/TClassAndObjectLock( 4434): objFunc2 start: thread = pool-1-thread-4
         03-17 00:03:29.799 D/TClassAndObjectLock( 4434): objClsFunc end: thread = pool-1-thread-7
         03-17 00:03:29.800 D/TClassAndObjectLock( 4434): objClsFunc start: thread = pool-1-thread-8
         03-17 00:03:31.799 D/TClassAndObjectLock( 4434): objFunc2 end: thread = pool-1-thread-4
         03-17 00:03:31.800 D/TClassAndObjectLock( 4434): objClsFunc end: thread = pool-1-thread-8
         03-17 00:03:31.801 D/TClassAndObjectLock( 4434): staticClsFunc start: thread = pool-1-thread-9
         03-17 00:03:33.801 D/TClassAndObjectLock( 4434): staticClsFunc end: thread = pool-1-thread-9
         03-17 00:03:33.803 D/TClassAndObjectLock( 4434): staticClsFunc start: thread = pool-1-thread-10
         03-17 00:03:35.803 D/TClassAndObjectLock( 4434): staticClsFunc end: thread = pool-1-thread-10
         */
        final TClassAndObjectLock obj = new TClassAndObjectLock();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                obj.objFunc1();
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                obj.objFunc1();
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                obj.objFunc2();
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                obj.objFunc2();
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                TClassAndObjectLock.staticFunc();
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                TClassAndObjectLock.staticFunc();
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                obj.objClsFunc();
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                obj.objClsFunc();
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                TClassAndObjectLock.staticClsFunc();
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                TClassAndObjectLock.staticClsFunc();
            }
        });
    }

    private static final TClassAndObjectLock INSTANCE = new TClassAndObjectLock();

    public synchronized static TClassAndObjectLock getInstance() {
        return INSTANCE;
    }

    public void nonSyncFunc() {
        Log.d(TAG, "nonSyncFunc start: thread = " + Thread.currentThread().getName());
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "nonSyncFunc end: thread = " + Thread.currentThread().getName());
    }

    public synchronized void objFunc1() {
        Log.d(TAG, "objFunc1 start: thread = " + Thread.currentThread().getName());
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "objFunc1 end: thread = " + Thread.currentThread().getName());
    }

    public synchronized void objFunc2() {
        Log.d(TAG, "objFunc2 start: thread = " + Thread.currentThread().getName());
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "objFunc2 end: thread = " + Thread.currentThread().getName());
    }

    public synchronized static void staticFunc() {
        Log.d(TAG, "staticFunc start: thread = " + Thread.currentThread().getName());
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "staticFunc end: thread = " + Thread.currentThread().getName());
    }

    public static void staticClsFunc() {
        synchronized (TClassAndObjectLock.class) {
            Log.d(TAG, "staticClsFunc start: thread = " + Thread.currentThread().getName());
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "staticClsFunc end: thread = " + Thread.currentThread().getName());
        }
    }

    public void objClsFunc() {
        synchronized (TClassAndObjectLock.class) {
            Log.d(TAG, "objClsFunc start: thread = " + Thread.currentThread().getName());
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "objClsFunc end: thread = " + Thread.currentThread().getName());
        }
    }

}
