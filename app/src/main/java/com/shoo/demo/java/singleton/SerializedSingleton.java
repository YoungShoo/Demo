package com.shoo.demo.java.singleton;

import java.io.Serializable;

/**
 * Created by Shoo on 17-9-18.
 */

public class SerializedSingleton implements Serializable {

    private static final long serialVersionUID = -1L;

    private static class Holder {
        private static final SerializedSingleton INSTANCE = new SerializedSingleton();
    }

    public static SerializedSingleton getInstance() {
        return Holder.INSTANCE;
    }

    private SerializedSingleton() {}

    /**
     * This method will be invoked when you deserialize the object.
     *
     * @return
     */
    protected Object readResolve() {
        return Holder.INSTANCE;
    }

}
