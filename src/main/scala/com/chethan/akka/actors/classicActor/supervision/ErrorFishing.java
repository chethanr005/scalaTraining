package com.chethan.akka.actors.classicActor.supervision;

/**
 * Created by $Chethan on Dec 29, 2023.
 */

public class ErrorFishing {

    public static void main(String[] args) {



        try{
          throw new Throwable();

        }catch (Throwable e){
            System.out.println("caught the fish");
        }
    }
}
