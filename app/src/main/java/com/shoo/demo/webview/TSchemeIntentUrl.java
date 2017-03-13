package com.shoo.demo.webview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Shoo on 17-1-24.
 */

public class TSchemeIntentUrl {

    public static void test(final Activity activity) {

        WebView webView = new WebView(activity);
        webView.loadUrl("file:///android_asset/html/scheme.html");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && !TextUtils.isEmpty(url)) {
                    String host = Uri.parse(url).getHost();
                    if ("www.baidu.com".equalsIgnoreCase(host)) {
                        return true;
                    }
                }

                Intent intent = new Intent();
                intent.setData(Uri.parse(url));
                if (intent.resolveActivity(activity.getPackageManager()) != null) {
                    activity.startActivity(intent);
                    return true;
                }

                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        activity.setContentView(webView);
    }

}
