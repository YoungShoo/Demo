package com.shoo.demo.rxjava;

import android.util.Log;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Shoo on 17-9-29.
 */

public class TConcat {

    private static final String TAG = "TConcat";

    public static void test() {
        testFirstOrDefault();

    }

    private static void testFirstOrDefault() {
        Observable<Integer> obs1 = Observable.just(10)
                .doOnNext(getAction());
        Observable<Integer> obs2 = Observable.just(20);
        Observable<Integer> obs3 = Observable.just(30);

        obs1.concatWith(obs2)
                .concatWith(obs3)
                .firstOrDefault(50)
                .subscribe(new DefaultSubscriber<Integer>());
    }

    private static Action1<? super Integer> getAction() {
        return new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d(TAG, "call: integer = [" + integer + "]");
            }
        };
    }

}
