package com.shoo.demo.window;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.shoo.demo.R;

/**
 * Created by Shoo on 17-1-22.
 */

public class TSystemUiFlags {

    public static void test(AppCompatActivity activity) {

        final int sdk = Build.VERSION.SDK_INT;

        if (sdk >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            setTransparentStatusBar(activity);
        } else if (sdk >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            // 设置透明状态栏
            if ((params.flags & bits) == 0) {
                params.flags |= bits;
                window.setAttributes(params);
            }
        }
//        activity.getSupportActionBar().hide();

        View view = LayoutInflater.from(activity).inflate(R.layout.activity_main, null);
//        view.setFitsSystemWindows(true);
        activity.setContentView(view);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void setTransparentStatusBar(AppCompatActivity activity) {
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

}
