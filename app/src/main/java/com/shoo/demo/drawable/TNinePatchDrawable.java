package com.shoo.demo.drawable;

import android.app.Activity;
import android.widget.ImageView;

import com.shoo.demo.R;

/**
 * Created by xiaoyuanxiu on 17-1-18.
 */

public class TNinePatchDrawable {

    public static void test(Activity activity) {
        ImageView imageView = new ImageView(activity);
        imageView.setImageResource(R.drawable.wg_bg);
        // 当ImageView设置的src是.9图时，需要设置以下伸缩属性，以确保.9正常拉伸
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        imageView.setBackgroundResource(R.drawable.wg_bg);

        activity.setContentView(imageView);
    }
}
