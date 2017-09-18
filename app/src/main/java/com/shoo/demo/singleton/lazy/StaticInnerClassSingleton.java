package com.shoo.demo.singleton.lazy;

/**
 * Created by Shoo on 17-9-18.
 */

public class StaticInnerClassSingleton {

    private static class Holder {
        private static final StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();
    }

    public static StaticInnerClassSingleton getInstance() {
        return Holder.INSTANCE;
    }

    private StaticInnerClassSingleton() {}
}
