package com.shoo.demo.exception;

import android.util.Log;

import com.shoo.demo.rxjava.DefaultSubscriber;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Shoo on 17-6-9.
 */

public class TExceptionStack {

    private static final String TAG = "TExceptionStack";

    public static void test() {
        Observable.just(1)
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        funcThrowingException();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new DefaultSubscriber<Integer>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e(TAG, "Reader", e);
//                        Log.d(TAG, Log.getStackTraceString(e));
                    }
                });
    }

    private static void funcThrowingException() {
        throw new NullPointerException("test exception stack!");
    }

}
