package com.shoo.demo.java.accessrights;

import com.shoo.demo.java.accessrights.innerpackage.Human;

/**
 * Created by Shoo on 17-11-15.
 */

public class Male extends Human {

    @Override
    protected void getName() {
        super.getName();
    }

    // 不同包，无法重写或直接访问
    // @Override
    void getAge() {

    }
}
