package com.shoo.demo.java;

import static com.shoo.demo.java.ItemTypeDescriptor.TYPE_MUSIC;

/**
 * Created by Shoo on 17-2-5.
 */

public class TEnumeratedAnnotation {

    public static void test() {
        // Must be one of: TYPE_MUSIC, TYPE_PHOTO, TYPE_TEXT
//        ItemTypeDescriptor dsc = new ItemTypeDescriptor(0);   // won't compile
        ItemTypeDescriptor descriptor = new ItemTypeDescriptor(TYPE_MUSIC);
        int itemType = descriptor.itemType;
    }
}
