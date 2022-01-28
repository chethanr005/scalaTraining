package Java;

import javax.management.Query;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test {



    public static void main(String[] args) {
        //Test t=new Test();

        //declarative style
       // int sum= IntStream.rangeClosed(0,100).sum();
        //System.out.println(sum);


        //List<Integer> list1= Arrays.asList(1,4,9,0,5,6,5,5,6,1,2,3,2,3,4,5,6,7,8,9);

        //declarative style
       // List<Integer> uniqueList= list1.stream().distinct().collect(Collectors.toList());
       // System.out.println(uniqueList);

        //imperative vs functional
        //lambda expressions
        //functional interface



        //Runnable
        Runnable r1 = ( ) -> {
            for(int i=0; i<10; i++)
                System.out.println("Runnable");
        };
//
//        Thread tr1= new Thread(r1);
//                tr1.start();


        //Comparator
       Comparator<Integer> c1= (a, b) ->  a.compareTo(b);

        System.out.println(c1.compare(7,7));

//
//        List<Integer> l1=Arrays.asList(4,8,2,7,9,6,1,3,5);
//       System.out.println(l1);
//
//
//       Comparator<Integer> c2= (a,b) -> (a<b)?-1:(a>b)?1:0;
//        Collections.sort(l1, c2);
//       System.out.println(l1);


        //Predicate
       //Predicate<Integer> p1=  i -> i%2==0;

        //System.out.println( p1.test(10));

        //Function
        //Function<Integer, Double> f2= i -> Math.sqrt(i);
      //  System.out.println( f2.apply(49));

        //Consumer
//        Consumer<Integer> c3= i -> {
//            for (int a=1; a<=10; a++)
//            System.out.println(i+" * "+a+" = "+ i*a);
//        };
//         c3.accept(7);


        //Supplier
//        Supplier<LocalDateTime> s1= () -> LocalDateTime.now();
//        System.out.println(s1.get());

        //For two Input
        // 1. BiPredicate <String, Integer>
        // 2. BiFunction <Integer, Integer, Long>
        // 3. BiConsumer <Object, Object>

        //Primitive version of  Function Interfacing

        // Primitive Predicate Functional Interfacing
        // 1. IntPredicate
        // 2. DoublePredicate
        // 3. LongPredicate


//        IntPredicate i3= i -> i%2==1;
//
//       System.out.println(i3.test(11));



        // Primitive Function Functional Interfacing
        // 1. IntFunction => takes int as input but can return any type
        // 2. DoubleFunction => takes double as input but can return any type
        // 3. LongFunction => takes long as input but can return any type

        // 4. ToIntFunction => takes any input but returns int
        // 5. ToDoubleFunction => takes any input but returns double
        // 6. ToLongFunction => takes any input but returns long

        // 7. ToIntBiFunction => takes any 2 input and returns int
        // 8. ToDoubleBiFunction => takes any 2 input and returns double
        // 9. ToLongBiFunction => takes ant 2 input and returns long

        // 10. IntToDouble
        // 11. IntToLong
        // 12. DoubleToInt
        // 13. DoubleToLong
        // 14. LongToInt
        // 15. LongToDouble



        //Primitve Consumer Functional InterFacing
        // 1. BooleanSupplier => returns Boolean
        // 2. IntSupplier => returns int
        // 3. LongSupplier => returns long
        // 4. DoubleSupplier => returns double




        // Unary Operator
        // 1. IntUnaryOperator => takes and returns Int
        // 2. LongUnaryOperator => takes and returns Long
        // 3. DoubleUnaryOperator => takes and returns Double

        // Binary Operator
        // 1. BinaryOperator <String>
        // 2. IntBinaryOperator
        // 3. LongBinaryOperator
        // 4. DoubleBinaryOperator















    }


}
