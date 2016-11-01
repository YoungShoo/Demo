package com.shoo.demo.tester;

import android.app.Activity;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Shoo on 16-10-22.
 */
public abstract class Tester {

    public static void test(Class<? extends Tester> cl, Activity activity) {
        try {
            Tester tester = cl.getConstructor(Activity.class).newInstance(activity);
            tester.test();
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public final Activity mActivity;

    public Tester(Activity activity) {
        mActivity = activity;
    }

    protected abstract void test();

}
