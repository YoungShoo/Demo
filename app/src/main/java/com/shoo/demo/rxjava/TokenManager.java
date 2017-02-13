package com.shoo.demo.rxjava;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Random;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

/**
 * Created by Shoo on 16-11-2.
 */
public class TokenManager {

    private static final String TAG = "TokenManager";

    // mTokenSubject对象锁
    private final Object mLock = new Object();

    // 保存token信息的Subject对象
    private Subject<String, String> mTokenSubject;
    // 管理token请求的订阅状态
    private Subscription mTokenSubscription;
    // 标记mTokenSubject是否有效：一旦执行mTokenSubject.onError(e)，则需要重新创建mTokenSubject，该标记将被设置为true
    private boolean mIsSubjectInvalid;
    // 模拟token请求失败的计算器
    private int mErrorCnt = 0;

    private static class Holder {
        public static final TokenManager INSTANCE = new TokenManager();
    }

    public static TokenManager getInstance() {
        return Holder.INSTANCE;
    }

    private TokenManager() {

    }

    /**
     * Delete me: 使用方法
     */
    public static void usage() {
        TokenManager.getInstance().getToken()
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String token) {
                        // 获取token成功
                        return Observable.just("request url with token [" + token + "]");
                    }
                })
                .subscribe(new DefaultSubscriber<String>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        // 请求token失败时，将会直接触发此回调
                    }

                    @Override
                    public void onNext(String s) {
                        super.onNext(s);
                        // 使用token进行相应接口请求返回的结果
                    }
                });
    }

    /**
     * Delete me: 测试结果
     */
    public static void test() {
        // 模拟先后发出10个请求
        for (int i = 0; i < 10; i++) {
            final int index = i;
            Observable.just(i)
                    .flatMap(new Func1<Integer, Observable<String>>() {
                        @Override
                        public Observable<String> call(Integer integer) {
                            // 使用异步线程，模拟请求来自不同线程的场景
                            return Observable.just(integer)
                                    .observeOn(Schedulers.io())
                                    .flatMap(new Func1<Integer, Observable<String>>() {
                                        @Override
                                        public Observable<String> call(Integer integer) {
                                            // 先后执行请求
                                            try {
                                                Thread.sleep(new Random().nextInt(50));
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            // 1. 先获取token
                                            return getInstance().getToken();
                                        }
                                    });
                        }
                    })
                    .flatMap(new Func1<String, Observable<String>>() {
                        @Override
                        public Observable<String> call(String token) {
                            // 2.1 获取token成功，使用token作为参数进行相应接口的请求
                            return Observable.just("request url result with token [" + token + "]");
                        }
                    })
                    .subscribe(new DefaultSubscriber<String>() {

                        @Override
                        public void onNext(String result) {
                            // 3. 使用token进行请求得到的结果
                            Log.d(TAG, index + ". onNext: result = " + result);
                        }

                        @Override
                        public void onError(Throwable e) {
                            // 2.2 获取token失败，进行错误反馈及错误提示
                            Log.d(TAG, index + ". onError: e = " + e);
                        }
                    });
        }
        /**
         * 结果：
         * setupTokenSubject仅被调用了三次，前两次请求token失败（3、0），第三次请求token成功（7或者其他），
         * 之后的请求都直接使用mTokenSubject中的token值，所以不会再次创建mTokenSubject以及请求token信息
         *
         D/TokenManager(24211): setupTokenSubject
         D/TokenManager(24211): setupTokenSubject
         D/TokenManager(24211): 3. onError: e = java.lang.RuntimeException: get token failed
         D/TokenManager(24211): 0. onError: e = java.lang.RuntimeException: get token failed
         D/TokenManager(24211): setupTokenSubject
         D/TokenManager(24211): 7. onNext: result = request url result with token [xiaopangzi]
         D/TokenManager(24211): 8. onNext: result = request url result with token [xiaopangzi]
         D/TokenManager(24211): 6. onNext: result = request url result with token [xiaopangzi]
         D/TokenManager(24211): 1. onNext: result = request url result with token [xiaopangzi]
         D/TokenManager(24211): 2. onNext: result = request url result with token [xiaopangzi]
         D/TokenManager(24211): 4. onNext: result = request url result with token [xiaopangzi]
         D/TokenManager(24211): 9. onNext: result = request url result with token [xiaopangzi]
         D/TokenManager(24211): 5. onNext: result = request url result with token [xiaopangzi]
         */
    }

    /**
     * 获取token信息：如果已经成功获取过，则直接返回；否则，执行token请求
     *
     * @return
     */
    public Observable<String> getToken() {
        // 加锁：以防止多线程访问时，创建多个不必要的mTokenSubject并执行多次token请求
        synchronized (mLock) {
            if (mTokenSubject == null || mIsSubjectInvalid) {
                mIsSubjectInvalid = false;
                mTokenSubject = BehaviorSubject.create();
                Subject<String, String> tokenSubject = mTokenSubject;
                setupTokenSubject(tokenSubject);
            }

            return mTokenSubject;
        }
    }

    /**
     * 请求token并通过tokenSubject(mTokenSubject)进行发射
     *
     * @param tokenSubject
     */
    private void setupTokenSubject(final Subject<String, String> tokenSubject) {
        Log.d(TAG, "setupTokenSubject");
        mTokenSubscription = requestTokenByRetrofit()
                .subscribe(new DefaultSubscriber<String>() {

                    @Override
                    public void onNext(String token) {
                        super.onNext(token);
                        synchronized (mLock) {
                            // 请求token成功，发射结果
                            tokenSubject.onNext(token);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        synchronized (mLock) {
                            // 请求token失败，发射异常
                            // 此后调用getToken()方法时，会重新创建mTokenSubject并请求token信息
                            // 这就解决了执行Subject.onError()后，该Subject无法正常使用的问题
//                            mTokenSubject.onNext(null);
                            tokenSubject.onError(e);
                            mIsSubjectInvalid = true;
                        }
                    }
                });
    }

    /**
     * 请求token信息：这里模拟前两次请求失败，后面的请求成功的场景
     *
     * @return
     */
    @NonNull
    private Observable<String> requestTokenByRetrofit() {
        // TODO: 这里实现token的请求
        return Observable
                .just("xiaopangzi")
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return ++mErrorCnt > 2 ? Observable.just(s) : Observable.<String>error(new
                                RuntimeException("get token failed"));
                    }
                });
    }

    /**
     * 中断token请求，并销毁已经获取的token信息
     * 可以在Activity.onDestroy()方法执行该操作
     */
    public void shutDown() {
        if (mTokenSubscription != null && !mTokenSubscription.isUnsubscribed()) {
            mTokenSubscription.unsubscribe();
            mTokenSubscription = null;
        }
        mTokenSubject = null;
        mIsSubjectInvalid = false;
        mErrorCnt = 0;
    }

}
