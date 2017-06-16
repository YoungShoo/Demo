package com.shoo.demo.picasso;

import android.app.Activity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Shoo on 17-4-24.
 */

public class TPicasso {

    public static void test(Activity activity) {
        ImageView imageView = new ImageView(activity);
        activity.setContentView(imageView);
        Picasso.with(activity).load("http://i.imgur.com/DvpvklR.png").into(imageView);
    }
}
