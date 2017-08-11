package com.shoo.demo.java;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Shoo on 17-8-9.
 */

public class TGenericType {

    public static void main(String[] args) {
        /*
         * fruit = com.shoo.demo.java.TGenericType$Apple@5cad8086
         */
        AppleMarket market = new AppleMarket();
        Object fruit = createFruit(market);
        System.out.println("fruit = " + fruit);
    }

    private static <T> T createFruit(Object market) {
        ParameterizedType parameterizedType = (ParameterizedType) market.getClass().getGenericSuperclass();
        Class<?> clazz = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        T obj = null;
        try {
            obj = (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return obj;
    }

    private static class Market<T> {

    }

    private static class AppleMarket extends Market<Apple> {

    }

    /**
     * private static class Apple
     *
     * java.lang.IllegalAccessException:
     * Class com.shoo.demo.java.TGenericType can not access a member of class com.shoo.demo.java.TGenericType$Apple
     * with modifiers "private"
     */
    public static class Apple {

    }

}
