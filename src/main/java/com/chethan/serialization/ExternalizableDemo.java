package com.chethan.serialization;

import java.io.*;

/**
 * Created by Chethan on Apr 14, 2023.
 */

public class ExternalizableDemo implements Externalizable {

    String name;
    String pass;
    int    id;

    public ExternalizableDemo() {

    }

    ExternalizableDemo(String name, String pass,int id) {
        this.name = name;
        this.pass = pass;
        this.id = id;
    }



    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(pass);
        out.writeObject(id);
    }


    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
       name = (String) in.readObject();
        pass = (String) in.readObject();
        id = (int)in.readObject();
    }
    private static final Long serialVersionUID = 145465L;

    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\chethan\\Desktop\\External.ser";

//        FileOutputStream   fileOutputStream   = new FileOutputStream(filePath);
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//        ExternalizableDemo externalizableDemo = new ExternalizableDemo("empty", "full", 10);
//        objectOutputStream.writeObject(externalizableDemo);
//        objectOutputStream.close();
//        fileOutputStream.close();

        //Deserialization
        FileInputStream   fileInputStream   = new FileInputStream(filePath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ExternalizableDemo            object            = (ExternalizableDemo) objectInputStream.readObject();
        System.out.println(object.id);

        fileInputStream.close();
        objectInputStream.close();
    }
}
