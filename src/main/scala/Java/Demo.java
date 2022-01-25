package Java;

import java.util.function.Function;

public class Demo  implements FInterface{

    @Override
    public int sumOfSquare(int a, int b) {

        System.out.println("Functional Interfacing");
        return   a*a+b*b;
    }


    public static void main(String[] args) {


        FInterface f=  (a, b) -> a*a+b*b;

        System.out.println( f.sumOfSquare(2,3));


//
        Cons c2= new Cons();

        Runnable r2 = Cons::new;
        r2.run();

        Function<Integer, Integer> f1= c2 :: cube;
        f1.apply(2);


    }
}
