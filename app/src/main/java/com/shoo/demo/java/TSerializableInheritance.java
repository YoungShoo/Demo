package com.shoo.demo.java;

/**
 * Created by Shoo on 17-6-27.
 */

public class TSerializableInheritance {

    public static void main(String[] args) {
        System.out.println(Fruit.sName);    // Fruit
        System.out.println(Apple.sName);    // Apple
    }

    public static class Fruit {
        public static final String sName = "Fruit";
    }

    public static class Apple extends Fruit {
        public static final String sName = "Apple";
    }

}
