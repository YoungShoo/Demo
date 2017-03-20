package com.shoo.demo.java;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shoo on 17-3-20.
 */

public class TSuperAndExtends {

    public static void test() {
        List<Fruit> fruits = new ArrayList<>();
        List<Apple> apples = new ArrayList<>();
        List<RedApple> redApples = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            fruits.add(new Fruit());
        }

        for (int i = 0; i < 5; i++) {
            apples.add(new Apple());
        }

        /**
         * 总结：读地板类，写天花板类
         */

        // extends: 不可以传父类
//        readExtendsList(fruits);
        readExtendsList(apples);
        readExtendsList(redApples);
//
        writeSuperList(fruits);
        writeSuperList(apples);
//        // super: 不可以传子类
//        writeSuperList(redApples);
    }

    /**
     * 读地板
     *
     * @param apples
     */
    private static void readExtendsList(List<? extends Apple> apples) {
        for (int i = 0; i < 5; i++) {
            // extends: 只读，不可写
            Apple apple = apples.get(i);
//            apples.add(new Apple());
            Log.d("Shoo", "readExtendsList: apple = " + apple);
        }
    }

    /**
     * 写天花板
     *
     * @param apples
     */
    private static void writeSuperList(List<? super Apple> apples) {
        for (int i = 0; i < 5; i++) {
            // super: 可读，可写
            apples.add(new Apple());
            // 不知道具体父类，不可强转
            Object apple = apples.get(i);
            Log.d("Shoo", "writeSuperList: apple = " + apple);
        }
    }

    public static class Fruit {

    }

    public static class Apple extends Fruit {

    }

    public static class RedApple extends Apple {

    }

}
