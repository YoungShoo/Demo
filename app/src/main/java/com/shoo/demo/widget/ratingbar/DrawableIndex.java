package com.shoo.demo.widget.ratingbar;

import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Shoo on 17-11-8.
 */

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
@IntDef({DrawableIndex.BACKGROUND, DrawableIndex.PROGRESS, DrawableIndex.SECONDARY})
public @interface DrawableIndex {

    int BACKGROUND = 0;
    int SECONDARY = 1;
    int PROGRESS = 2;

}
