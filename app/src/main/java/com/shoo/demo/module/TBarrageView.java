package com.shoo.demo.module;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.shoo.barrage.BarrageView;

/**
 * Created by Shoo on 17-8-28.
 */

public class TBarrageView {

    @TargetApi(Build.VERSION_CODES.M)
    public static void test(Activity activity) {
        FrameLayout frameLayout = new FrameLayout(activity);
        activity.setContentView(frameLayout);

        WebView webView = new WebView(activity);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://stackoverflow.com");
        frameLayout.addView(webView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        final BarrageView barrageView = new BarrageView(webView);
        barrageView.start();

        webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                barrageView.scrollTo(scrollX, scrollY);
            }
        });
    }

}
