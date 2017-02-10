package com.shoo.demo.java;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ItemTypeDescriptor {
    // ... type definitions
    public static final int TYPE_MUSIC = 0;
    public static final int TYPE_PHOTO = 1;
    public static final int TYPE_TEXT = 2;

    public final int itemType;

    // Describes when the annotation will be discarded
    @Retention(RetentionPolicy.SOURCE)
    // Enumerate valid values for this interface
    @IntDef({TYPE_MUSIC, TYPE_PHOTO, TYPE_TEXT})
    // Create an interface for validating int types
    public @interface ItemTypeDef {
    }

    // Mark the argument as restricted to these enumerated types
    public ItemTypeDescriptor(@ItemTypeDef int itemType) {
        this.itemType = itemType;
    }
}