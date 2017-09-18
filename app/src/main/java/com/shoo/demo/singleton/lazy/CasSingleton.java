package com.shoo.demo.singleton.lazy;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Shoo on 17-9-18.
 */

public class CasSingleton {

    private static final AtomicReference<CasSingleton> INSTANCE = new AtomicReference<>();

    public static CasSingleton getInstance() {
        for (;;) {
            CasSingleton instance = INSTANCE.get();
            if (instance != null) {
                return instance;
            }

            instance = new CasSingleton();
            if (INSTANCE.compareAndSet(null, instance)) {
                return instance;
            }
        }
    }

    private CasSingleton() {}

}
