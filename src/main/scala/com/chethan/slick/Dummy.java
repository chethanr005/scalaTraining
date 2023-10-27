package com.chethan.slick;

import com.chethan.mongo.MongoConnection;

import java.nio.file.Paths;

/**
 * Created by Chethan on Dec 28, 2022.
 */

public class Dummy extends A implements DummyInterface {

    Dummy() {
        super();
    }

    void simple() {
        super.aaa();
    }

    public static void main(String[] args) {

        DummyInterface a = new DummyInterface() {
            @Override
            public void aaa() {
                DummyInterface.super.aaa();
            }
        };

        MongoConnection mongoConnection1=a.mongoConnection;

        Paths.get("","","");

    }


    @Override
    public void aaa() {
        DummyInterface.super.
                aaa();
    }


}

abstract class A {
    void aaa() {

    }

}

class B {
    void aaa() {

    }


}


interface DummyInterface {
    MongoConnection mongoConnection=null;


    static void m1() {

    }

    default void aaa() {

    }

}
