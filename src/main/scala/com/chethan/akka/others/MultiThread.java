package com.chethan.akka.others;

/**
 * Created by Chethan on Mar 27, 2023.
 */

public class MultiThread {


    public static void main(String[] args) throws InterruptedException {

        BankAccountDemo bankAccountDemo = new BankAccountDemo(50000);
        Runnable runnable1 = () -> {
            for (int i = 0; i < 5; i++) {
                bankAccountDemo.withdraw(1000);
                //  System.out.println(bankAccountDemo.withdraw(1000));
            }
        };

        Runnable runnable2 = () -> {
            for (int i = 0; i < 5; i++) {
                bankAccountDemo.withdraw(9999);
                //  System.out.println(bankAccountDemo.withdraw(1000));
            }
        };


        Runnable runnable3 = () -> {
            for (int i = 0; i < 5; i++) {
                BankAccountDemo.dummyMethod("thread1");
            }
        };

        Runnable runnable4 = () -> {
            for (int i = 0; i < 5; i++) {
                BankAccountDemo.dummyMethod("thread2");
            }
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        Thread thread3 = new Thread(runnable3);
        Thread thread4 = new Thread(runnable4);

//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();

        Runnable runnable5 = () -> {
            try {
                Thread.sleep(10000);
                System.out.println("joined successfully");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Thread thread5 = new Thread(runnable5);
        thread5.start();
        thread5.join();
        System.out.println("done executing");

    }
}


class BankAccountDemo {

    int a;

    BankAccountDemo(int balance) {
        this.a = balance;
    }

    void dum() {


    }

    synchronized int withdraw(int amount) {
        //this.balance = this.balance - amount;
        try {
            Thread.sleep(1000);
            System.out.println(amount);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 10;
    }

    synchronized static void dummyMethod(String anything) {
        try {
            Thread.sleep(1000);
            System.out.println(anything);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
