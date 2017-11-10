package com.shoo.demo.widget.ratingbar;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;

import com.shoo.demo.R;

/**
 * Created by Shoo on 17-11-8.
 */

public class TRatingBar {

    private static int[] sDrRes = {R.drawable.ic_background, R.drawable.ic_secondary, R.drawable.ic_progress};

    public static void test(Activity activity) {
        DrawableRatingBar ratingBar = new DrawableRatingBar(activity);

        ratingBar.setNumStars(8);
        ratingBar.setStepSize(0.5f);
        ratingBar.setRating(2.5f);
        ratingBar.setIsIndicator(false);

        for (int i = 0; i < 3; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), sDrRes[i]);
            if (bitmap != null) {
                ratingBar.setDrawable(i, bitmap);
            }
        }

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ratingBar.setLayoutParams(params);
        activity.setContentView(ratingBar);
    }
}
