package com.chethan.serialization;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Chethan on Apr 13, 2023.
 */

public class SerialClass {


    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\chethan\\Desktop\\packing.ser";

        //Serialization
        Packing            packing1           = new Packing("apple", 999);
        Packing            packing2           = new Packing("apple1", 999);
        Packing            packing3           = new Packing("apple25", 999);
        Packing            packing4           = new Packing("apple3", 999);
        Packing            packing5           = new Packing("apple4", 999);
        FileOutputStream   fileOutputStream   = new FileOutputStream(filePath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        List<Packing>      packings           = Arrays.asList(packing1, packing2, packing3, packing4, packing5);
        objectOutputStream.writeObject(packing1);
        objectOutputStream.writeObject(packings);
        objectOutputStream.close();
        fileOutputStream.close();

        //Deserialization
        FileInputStream   fileInputStream   = new FileInputStream(filePath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object            object            = objectInputStream.readObject();
        System.out.println(object);
        Packing outPacking = (Packing) object;
        System.out.println(outPacking);
        System.out.println(outPacking.name + "-=-=-=-=" + outPacking.number + "-=-=-=-=-=" + outPacking.pass);
        Object object2 = objectInputStream.readObject();
        fileInputStream.close();
        objectInputStream.close();

        List<Packing> outPack = (List) object2;
        System.out.println(outPack);

        System.out.println(object2 instanceof List);

        Cat                c                   = new Cat();
        FileOutputStream   fileOutputStream1   = new FileOutputStream(filePath);
        ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(fileOutputStream1);
        objectOutputStream1.writeObject(c);
        objectOutputStream1.close();
        fileOutputStream1.close();

        FileInputStream   fileInputStream1   = new FileInputStream(filePath);
        ObjectInputStream objectInputStream1 = new ObjectInputStream(fileInputStream1);
        Object            object1            = objectInputStream1.readObject();
        Cat               cat                = (Cat) object1;
        System.out.println(cat.d.r.j);
        System.out.println();
        fileInputStream.close();
        objectInputStream.close();


        Account account = new Account("durga","anushka123",999);
        FileOutputStream   fileOutputStream2   = new FileOutputStream(filePath);
        ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(fileOutputStream2);
        objectOutputStream2.writeObject(account);
        fileOutputStream2.close();
        objectOutputStream2.close();

        FileInputStream   fileInputStream2   = new FileInputStream(filePath);
        ObjectInputStream objectInputStream2 = new ObjectInputStream(fileInputStream2);
        Account            account1            = (Account)objectInputStream2.readObject();
        System.out.println(account1.password);
        System.out.println(account1.pin);
        fileInputStream.close();
        objectInputStream.close();
    }

}


class Packing implements Serializable {
    static String name;
    int number;
    transient static final String pass = "pass2022";

    Packing(String name, int number) {
        this.name = name;
        this.number = number;
    }
}

class Cat implements Serializable {
//    String name;
//
//    Cat(String name) {
//        this.name = name;
//    }

    Dog d = new Dog();
}

class Dog implements Serializable {
    //        String name;
//        Dog(String name){
//            this.name = name;
//        }
    Rat r = new Rat();
}

class Rat implements Serializable {
    int j = 20;
}


class Account implements Serializable {
    String name;
    transient String password;
    transient  int pin;

    Account(String name, String password, int pin) {
        this.name = name;
        this.password = password;
        this.pin = pin;
    }

    private  void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        char[] pass=password.toCharArray();

        for(int i= 0; i<pass.length;i++){
            int value =(int) pass[i];
            int enValue = (value+7)*3;
            pass[i] =  (char) enValue;
        }
        String newPass = new String(pass);
        System.out.println("encrypting password ==> "+newPass);
        oos.writeObject(newPass);
        oos.writeInt(pin+1000);
    }

    private void readObject (ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        String password = ois.readObject().toString();
        char[] pass=password.toCharArray();

        for(int i= 0; i<pass.length;i++){
            int value =(int) pass[i];
            int deValue = (value/3)-7;
            pass[i] =  (char) deValue;
        }
        String decryptedPassword = new String(pass);
        System.out.println("decrypting password ==> "+decryptedPassword);
        this.password=new String(pass);
        int dePin = (int)ois.readInt();
        this.pin = dePin-1000;
    }

}
