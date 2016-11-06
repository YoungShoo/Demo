package com.shoo.demo.util;

import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewParent;

/**
 * Created by Shoo on 16-11-6.
 */

public class AnimUtils {

    public interface AnimViewContainer {
        void performDeleteAnim(View view);
    }

    public static void performDeleteAnim(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof AnimViewContainer) {
            ((AnimViewContainer) parent).performDeleteAnim(view);
        }
    }
}
