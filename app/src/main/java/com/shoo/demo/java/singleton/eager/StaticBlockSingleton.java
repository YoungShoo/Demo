package com.shoo.demo.java.singleton.eager;

/**
 * Created by Shoo on 17-9-18.
 */

public class StaticBlockSingleton {

    private static StaticBlockSingleton sInstance;

    static {
        sInstance = new StaticBlockSingleton();
    }

    public static StaticBlockSingleton getInstance() {
        return sInstance;
    }

    private StaticBlockSingleton() {}

}
