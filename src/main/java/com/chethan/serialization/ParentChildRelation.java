package com.chethan.serialization;

import java.io.*;

/**
 * Created by Chethan on Apr 14, 2023.
 */

public class ParentChildRelation {


    public static void main(String[] args) throws Throwable {
        String filePath = "C:\\Users\\chethan\\Desktop\\PC.ser";

        //serialization
        Child              child              = new Child();
        FileOutputStream   fileOutputStream   = new FileOutputStream(filePath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        child.name = "anything";
        child.id = 123456;
        System.out.println(child.name+"-=-=-=-"+child.id);
        objectOutputStream.writeObject(child);
        objectOutputStream.close();
        fileOutputStream.close();

        //Deserialization
        FileInputStream   fileInputStream   = new FileInputStream(filePath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object            object            = objectInputStream.readObject();
        System.out.println(object);
        Child child1 = (Child) object;
        System.out.println(child1);
        System.out.println(child1.i + "-=-=-=-=" + child1.childName);
        System.out.println(child1.id + "-=-=-=-=-=" + child1.name);
        fileInputStream.close();
        objectInputStream.close();
    }
}


class Parent  {
    String name = "parent";
    int    id   = 9001;


}


class Child extends Parent implements Serializable {


    String childName = "child1";
    int    i         = 1001;

  Child(){


  }
}
