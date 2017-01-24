package com.shoo.demo.webview;

import android.app.Activity;
import android.webkit.WebView;

/**
 * Created by xiaoyuanxiu on 17-1-24.
 */

public class TSchemeIntentUrl {

    public static void test(Activity activity) {

        WebView webView = new WebView(activity);
        webView.loadUrl("file:///android_asset/html/scheme.html");

        activity.setContentView(webView);
    }

}
