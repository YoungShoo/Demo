package com.shoo.demo.rxjava;

import android.util.Log;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Shoo on 17-6-16.
 */

public class TOnSubscribeThread {

    private static final String TAG = "TOnSubscribeThread";

    public static void test() {
        /**
         * 结果：处理数据发射线程，而不是调用线程
         *
         * 06-16 12:20:04.500 19287 19318 D TOnSubscribeThread: doOnSubscribe: threadId = 12202, threadName = RxIoScheduler-2
         * 06-16 12:20:04.500 19287 19318 D TOnSubscribeThread: doOnNext: threadId = 12202, threadName = RxIoScheduler-2
         */
        Observable.just(10)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.d(TAG, "doOnSubscribe: threadId = " + Thread.currentThread().getId()
                        + ", threadName = " + Thread.currentThread().getName());
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, "doOnNext: threadId = " + Thread.currentThread().getId()
                                + ", threadName = " + Thread.currentThread().getName());
                    }
                })
                .subscribe(new DefaultSubscriber<Integer>());
    }

}
