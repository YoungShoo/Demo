package com.shoo.demo.rxjava;

import android.util.Log;

import rx.Subscriber;

/**
 * 此类可以记录Log信息，并且减少不必要的方法重写(例如 onComplete())导致的代码冗长的问题
 * 可以将这里类抽出做为通用的Subscriber
 * @param <T>
 */
public class DefaultSubscriber<T> extends Subscriber<T> {

    private static final String TAG = "DefaultSubscriber";

    @Override
    public void onCompleted() {
        Log.d(TAG, "onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "onError: e = [" + e + "]");
    }

    @Override
    public void onNext(T t) {
        Log.d(TAG, "onNext: t = [" + t + "]");
    }
}
