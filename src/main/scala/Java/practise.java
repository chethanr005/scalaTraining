package Java;

import java.util.function.*;

public class practise {

//    public boolean prime(int n)
//    {
//        int c=0;
//        int m=n/2;
//        for(int i=1;i<=m;i++)
//        {
//            if(n%i==0)
//            {
//                c=c+1;
//            }
//        }
//        if(c==2)
//            return false;
//        else
//            return true;
//    }

    public static void main(String[] args) {
//        practise obj=new practise();
//        System.out.println(obj.prime(27));

        String s="hello";
        //Consumer
        Consumer<Integer> c = (a) -> System.out.println(-a);
        c.accept(30);
        BiConsumer<Integer, Integer> biC1 = (a, b) -> System.out.println("Sum : " + (a + b));

        //BiConsumer
        BiConsumer<Integer, Integer> biC2 = (a, b) -> System.out.println("Multiply : " + (a * b));
        biC1.accept(40,20);//BiConsumer

        biC1.andThen(biC2).accept(20, 10);//andThen BiConsumer

        //Predicate
        Predicate<Integer> p1=(a)-> a % 2 == 0;
        Predicate<Integer> p2=(a)-> a % 5 == 0;
        System.out.println("Predicate "+p1.test(6));//Predicate
        System.out.println("Predicate and "+p1.and(p2).test(6));//Predicate and
        System.out.println("Predicate or "+p1.or(p2).test(6));//Predicate or
        System.out.println("Predicate or negate "+p1.or(p2).negate().test(6));
        System.out.println("Predicate and negate "+p1.and(p2).negate().test(6));//Predicate negate

        //BiPredicate
        BiPredicate<Integer,Integer> bip1=(a, b)-> a < b;
        BiPredicate<Integer,Integer> bip2=(a, b)-> a % b==0;
        System.out.println("Is 20 less than 50 :"+bip1.test(20,50)); // BiPredicate
        System.out.println("Is BiPredicate and :"+bip1.and(bip2).test(10,50)); // BiPredicate and
        // BiPredicate or
        // BiPredicate negate()

        //Function
        Function<Integer,Boolean> isPositiveNo=(a)->a >= 0;
        Function<String,String> at=(a)-> a.concat("AndThen ");
        Function<String,String> comp=(a)->a.concat("Compose ");
        System.out.println(isPositiveNo.apply(-1));
        //Function has andThen And compose
        System.out.println(at.andThen(comp).apply("DA "));//andThen
        System.out.println(at.compose(comp).apply("DA "));//Compose

    }
}
