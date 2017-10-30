package com.shoo.demo.java;

import java.util.HashMap;

/**
 * Created by Shoo on 17-10-18.
 */

public class Info {

    // person 是类成员变量，随 new Info() 一起被创建，分配在堆内存中！！！被 GcRoot 间接引用！
    // GcRoot -> info -> new Info() -> person
    private Person person;

    public Info() {
        // new Person() 分配在堆内存中，被堆内存中的 person 变量引用，被 GcRoot 间接引用！！！
        // GcRoot -> info -> new Info() -> person -> new Person()

        person = new Person();  // 会被 GcRoot （间接）引用!!!

        // 如果 person 置空，则 new Person() 会被回收
    }

    public static void main(String[] args) {
        // info 变量定义在静态方法区内，被静态方法区（GcRoot）引用
        // new Info() 分配在堆内存中，被栈内存中的 info 变量引用，被 GC Root 间接引用
        // GcRoot -> info -> new Info()

        Info test = new Info();

        // 如果 info 置空，则 new Info() -> person -> new Person() 都会被回收
    }

    public void func() {
        Person person = new Person();
        // hashMap 定义在方法 func 内，分配在栈内存中，可作为 GcRoot
        // hashMap (GC Root) 对 new HashMap() 持有强引用，对 new HashMap() 对象内的元素持有间接引用
        HashMap<String, Person> hashMap = new HashMap<>();
        hashMap.put("xpz", person);
    }   // 方法结束后，hashMap 被回收，new HashMap() 堆内存不存在任何引用，则后续 GC 会被回收，其内部元素随之一起被回收

    private static class Person {
        // 类成员变量，随 Person 对象创建，分配在堆内存中，不作为 GC Root
        HashMap mHashmap;
    }
}
