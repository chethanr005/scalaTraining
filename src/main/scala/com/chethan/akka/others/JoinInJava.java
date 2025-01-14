package com.chethan.akka.others;

import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Chethan on Mar 28, 2023.
 */

public class JoinInJava {

    static Thread main;

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            try {
                main.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < 10; i++) {

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("executing child thread" + i);
            }
        };

//        Thread thread = new Thread(runnable);
//        thread.start();
//        //   thread.join();
//        main = Thread.currentThread();
//        Thread.sleep(2000);
//        System.out.println("Main thread completed");


        new Thread(JoinInJava::dummy, "t1").start();

        new ReentrantLock();
        Thread.yield();
        Future.apply(() -> {return 10;}, ExecutionContext.fromExecutor(Executors.newCachedThreadPool()));
    }


    static void dummy() {
        System.out.println("!@#$%^&*()_");
    }

}
