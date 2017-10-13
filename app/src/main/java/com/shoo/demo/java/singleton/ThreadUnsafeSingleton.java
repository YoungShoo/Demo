package com.shoo.demo.java.singleton;

/**
 * Created by Shoo on 17-9-18.
 */

public class ThreadUnsafeSingleton {

    private static ThreadUnsafeSingleton sInstance;

    public static ThreadUnsafeSingleton getInstance() {
        if (sInstance == null) {
            sInstance = new ThreadUnsafeSingleton();
        }
        return sInstance;
    }

    private ThreadUnsafeSingleton() {}

}
