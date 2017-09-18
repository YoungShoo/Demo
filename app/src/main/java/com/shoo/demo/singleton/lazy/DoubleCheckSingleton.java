package com.shoo.demo.singleton.lazy;

/**
 * Created by Shoo on 17-9-18.
 */

public class DoubleCheckSingleton {

    private static volatile DoubleCheckSingleton sInstance;

    public static DoubleCheckSingleton getInstance() {
        if (sInstance == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (sInstance == null) {
                    sInstance = new DoubleCheckSingleton();
                }
            }
        }

        return sInstance;
    }

    private DoubleCheckSingleton() {}

}
