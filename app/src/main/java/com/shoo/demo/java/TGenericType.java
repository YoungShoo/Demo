package com.shoo.demo.java;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Shoo on 17-8-9.
 */

public class TGenericType {

    public static void main(String[] args) {
        new TGenericType().printReturnType();

//        new TGenericType().<Apple>printGenericTypeOfTypeReference();

//        getGenericType();
    }

    private Apple testReturnType() {
        return null;
    }

    private List<Apple> testGenericReturnType() {
        return null;
    }

    private void printReturnType() {
        printReturnType("testReturnType");
        printReturnType("testGenericReturnType");
    }

    /*
    return type of testReturnType is: class com.shoo.demo.java.TGenericType$Apple
    return type of testGenericReturnType is: java.util.List<com.shoo.demo.java.TGenericType$Apple>
    Generic return type of testGenericReturnType is: class com.shoo.demo.java.TGenericType$Apple
    */
    private void printReturnType(String methodName) {
        try {
            Method method = getClass().getDeclaredMethod(methodName);
            Type type = method.getGenericReturnType();
            if (type instanceof ParameterizedType) {
                System.out.println("return type of " + methodName + " is: " + type);
                type = ((ParameterizedType) type).getActualTypeArguments()[0];
                System.out.println("Generic return type of " + methodName + " is: " + type);
                return;
            }
            System.out.println("return type of " + methodName + " is: " + type);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private <T> void printGenericTypeOfTypeReference() {
        TypeReference<Market<Apple>> typeReference = new TypeReference<Market<Apple>>() {
        };
        // T
        System.out.println(typeReference.getType());
    }

    private static void getGenericType() {
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
