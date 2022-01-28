package Java;

public interface IFace {



    default void Demo(){

        System.out.println("this is a default method");
    }


    static void Test(){

        System.out.println("this is a static method");

    }



}
