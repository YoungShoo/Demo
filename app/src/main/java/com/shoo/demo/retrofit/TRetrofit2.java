package com.shoo.demo.retrofit;

import com.shoo.demo.rxjava.DefaultSubscriber;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by Shoo on 17-2-13.
 */

public class TRetrofit2 {

    public static void test() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final GitHubService service = retrofit.create(GitHubService.class);

        service.listRepos("octocat")
                .subscribeOn(Schedulers.io())
                .subscribe(new DefaultSubscriber<List<Repo>>());
    }

}
