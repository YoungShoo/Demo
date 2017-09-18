package com.shoo.demo.singleton;

import com.shoo.demo.singleton.lazy.DoubleCheckSingleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Shoo on 17-9-18.
 */

public class DestroySingleton {

    public static void main(String[] args) {
        /*
        instance 1: com.shoo.demo.singleton.lazy.DoubleCheckSingleton@266474c2
        instance 2: com.shoo.demo.singleton.lazy.DoubleCheckSingleton@6f94fa3e
         */
        DoubleCheckSingleton instance1 = DoubleCheckSingleton.getInstance();
        System.out.println("instance 1: " + instance1);

        Class<DoubleCheckSingleton> clazz = DoubleCheckSingleton.class;
        try {
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            for (Constructor<?> constructor : constructors) {
                constructor.setAccessible(true);
                Object instance2 = constructor.newInstance();
                System.out.println("instance 2: " + instance2);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
