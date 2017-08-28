package com.shoo.barrage;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Shoo on 17-8-24.
 */

public interface BarrageState {

    int READY = 0;
    int BUBBLING = 1;
    int PAUSED = 2;
    int FINISHED = 3;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({READY, BUBBLING, PAUSED, FINISHED})
    @interface BarrageStateDef {
    }

}
