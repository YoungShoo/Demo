package com.shoo.demo.java;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Shoo on 17-11-1.
 */

public class TypeReference<T> {

    private Type type;

    public TypeReference() {
        Type superClass = getClass().getGenericSuperclass();
        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }
}
