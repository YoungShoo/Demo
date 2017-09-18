package com.shoo.demo.singleton.eager;

/**
 * Created by Shoo on 17-9-18.
 */

public class EagerSingleton {

    private static volatile EagerSingleton INSTANCE = new EagerSingleton();

    public static EagerSingleton getInstance() {
        return INSTANCE;
    }

    private EagerSingleton() {}

}
