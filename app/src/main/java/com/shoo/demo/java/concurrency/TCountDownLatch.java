package com.shoo.demo.java.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Shoo on 17-9-14.
 */

public class TCountDownLatch {

    private static CountDownLatch sCountDownLatch = new CountDownLatch(4);

    public static void main(String[] args) {
        /*
        start: 1505382543619
        countdown: 1505382543642
        countdown: 1505382543646
        countdown: 1505382543653
        countdown: 1505382543705
        waited: 86
         */
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 4; i++) {
            executorService.execute(createTask());
        }

        long start = System.currentTimeMillis();
        System.out.println("start: " + start);

        try {
            sCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("waited: " + (System.currentTimeMillis() - start));
    }

    private static Runnable createTask() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep((long) (Math.random() * 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("countdown: " + System.currentTimeMillis());
                    sCountDownLatch.countDown();
                }
            }
        };
    }
}
