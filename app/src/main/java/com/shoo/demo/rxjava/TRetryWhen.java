package com.shoo.demo.rxjava;

import android.util.Log;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Shoo on 17-9-28.
 */

public class TRetryWhen {

    private static final String TAG = "TRetryWhen";

    public static void test() {
        Observable.just(10)
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, "call: integer = [" + integer + "]");
                    }
                })
                .flatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(Integer integer) {
                        return Observable.error(new NullPointerException("test"));
                    }
                })
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        return observable
                                .flatMap(new Func1<Throwable, Observable<?>>() {
                                    @Override
                                    public Observable<?> call(Throwable throwable) {
                                        Log.d(TAG, "call: throwable = [" + throwable + "]");
                                        // 这样就会一直循环执行整个数据流了！！！
                                        // 如果这样返回一个新的 Observable（obs1），接下来根据不会触发这个（obs1）的执行
                                        // 这里返回一个 Observable 只是表明是否需要执行重试，重新走原来的数据流
                                        return Observable.just(20);

                                        // 这样才能结束掉整个数据流，回调到 Subscriber.onError()
                                        // return Observable.error(throwable);
                                        // 这样也能结束掉整个数据流，但Subscriber不会有任何回调(onComplete也不会回调)
                                        //return Observable.empty();
                                    }
                                });
                    }
                })
                .subscribe(new DefaultSubscriber<>());
    }

}
