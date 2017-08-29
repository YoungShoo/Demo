package com.shoo.demo.java;

import java.lang.reflect.Method;

/**
 * Created by xiaoyuanxiu on 17-8-29.
 */

public class TDeclaredMethods {

    public static void main(String[] args) {
        Class<Apple> clazz = Apple.class;
        Method[] methods = clazz.getDeclaredMethods();
        /*
        getDeclaredMethods: 当前类定义的所有静态及非静态的方法
            publicApple
            privateApple
            publicStaticApple
         */
        System.out.println("getDeclaredMethods:");
        for (Method method : methods) {
            System.out.println(method.getName());
        }

        /*
        getMethods: 当前类及所有父类定义的所有 Public 方法
            publicApple
            publicStaticApple
            publicFruit
            publicStaticFruit
            wait
            wait
            wait
            equals
            toString
            hashCode
            getClass
            notify
            notifyAll
         */
        System.out.println("getMethods:");
        methods = clazz.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }

    private static class Apple extends Fruit {

        public static void publicStaticApple() {

        }

        public void publicApple() {

        }

        private void privateApple() {

        }

    }

    private static class Fruit {

        public static void publicStaticFruit() {

        }

        public void publicFruit() {

        }

        protected void protectedFruit() {

        }

        private void privateFruit() {

        }

    }
}
