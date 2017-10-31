package com.shoo.demo.java;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Shoo on 17-10-31.
 */

public class AnnotationIntDef {

    public static void main(String[] args) {
        printFruit(FruitType.BANANA);
//        printFruit(2);    // won't compile
    }

    public static void printFruit(@FruitType int type) {
        System.out.println("fruit id is " + type);
    }

    @IntDef({FruitType.APPLE, FruitType.PEAR, FruitType.ORANGE, FruitType.BANANA})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FruitType {
        int APPLE = 0;
        int PEAR = 1;
        int ORANGE = 2;
        int BANANA = 3;
    }
}
