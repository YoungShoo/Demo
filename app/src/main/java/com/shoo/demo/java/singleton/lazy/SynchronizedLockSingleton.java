package com.shoo.demo.java.singleton.lazy;

/**
 * Created by Shoo on 17-9-18.
 */

public class SynchronizedLockSingleton {

    private static SynchronizedLockSingleton sInstance;

    public static synchronized SynchronizedLockSingleton getInstance() {
        if (sInstance == null) {
            sInstance = new SynchronizedLockSingleton();
        }
        return sInstance;
    }

    private SynchronizedLockSingleton() {}
}
