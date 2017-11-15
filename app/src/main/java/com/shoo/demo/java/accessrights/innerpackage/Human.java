package com.shoo.demo.java.accessrights.innerpackage;

/**
 * Created by Shoo on 17-11-15.
 */

public class Human {

    // 对于本包和其子类可见
    protected void getName() {

    }

    // 只能被同一包中的类访问
    void getAge() {

    }

}
