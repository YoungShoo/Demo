package com.shoo.demo.singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Created by Shoo on 17-9-18.
 */

public class TSerializedSingleton {

    public static void main(String[] args) {
        /*
                  singleton: com.shoo.demo.singleton.SerializedSingleton@355da254, hashCode = 895328852
        serializedSingleton: com.shoo.demo.singleton.SerializedSingleton@355da254, hashCode = 895328852
         */

        SerializedSingleton singleton = SerializedSingleton.getInstance();

        try {
            ObjectOutput os = new ObjectOutputStream(new FileOutputStream("file.ser"));
            os.writeObject(singleton);
            os.close();

            ObjectInput oi = new ObjectInputStream(new FileInputStream("file.ser"));
            SerializedSingleton serializedSingleton = (SerializedSingleton) oi.readObject();
            oi.close();

            System.out.println("singleton: " + singleton + ", hashCode = " + singleton.hashCode());
            System.out.println("serializedSingleton: " + serializedSingleton + ", hashCode = " + serializedSingleton
                    .hashCode());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
