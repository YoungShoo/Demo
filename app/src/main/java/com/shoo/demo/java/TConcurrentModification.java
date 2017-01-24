package com.shoo.demo.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xiaoyuanxiu on 17-1-23.
 */

public class TConcurrentModification {

    private static final List<String> sListeners = Collections.synchronizedList(new ArrayList<String>());

    public static void test() {
        opAddThread();
        opRemoveThread();
        opAddThread();
        opForEachThread();
        opAddThread();
        opForEachThread();
        opRemoveThread();
        opAddThread();
    }

    private static void opAddThread() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    System.out.println("add");
                    sListeners.add(String.valueOf(i + 1));
                }
            }
        }.start();
    }

    private static void opForEachThread() {
        new Thread() {
            @Override
            public void run() {
                synchronized (sListeners) {
                    for (String listener : sListeners) {
                        System.out.println("for each: " + listener + "***************");
                    }
                }
            }
        }.start();
    }

    private static void opRemoveThread() {
        new Thread() {
            @Override
            public void run() {
                while (sListeners.size() > 50000) {
                    System.out.println("remove");
                    sListeners.remove(0);
                }
            }
        }.start();
    }
}
