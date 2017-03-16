package com.shoo.demo.rxjava;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.exceptions.AssemblyStackTraceException;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Shoo on 17-3-6.
 */

public class TZip {

    public static void test() {
        // Banner
        Observable<String> bannerObservable = Observable.just("Banner")
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        // 模拟错误
                        return Observable.error(new NullPointerException());
                    }
                })
                .onErrorReturn(new Func1<Throwable, String>() {
                    @Override
                    public String call(Throwable throwable) {
                        return "Banner Error";
                    }
                });
        // 列表：延迟发射结果
        Observable<String> listObservable = Observable.timer(50, TimeUnit.MILLISECONDS)
                .flatMap(new Func1<Long, Observable<String>>() {
                    @Override
                    public Observable<String> call(Long aLong) {
                        return Observable.just("list");
                    }
                });
        // 合并结果
        Subscription result = Observable
                .zip(bannerObservable, listObservable, new Func2<String, String, String>() {
                    @Override
                    public String call(String banner, String list) {
                        return "Banner: " + banner + ", List: " + list;
                    }
                }).subscribe(new DefaultSubscriber<String>());

    }
}
