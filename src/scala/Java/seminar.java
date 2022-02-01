package Java;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class seminar {

    public static void FactoryMethods()
    {
        /*
         Stream.of()
         */
        System.out.println("of() function");
        Stream<Integer> si= Stream.of(1,34,5,72);
        si.forEach(System.out::println);
        System.out.println("----------------------");
        /*
        Stream.iterate(seed,unary-operator)
         */
        System.out.println("iterate() function");
        Stream.iterate(2,(i)->i+2)
                .limit(10)
                .forEach(System.out::println);
        System.out.println("----------------------");
        /*
        Stream.generate(Supplier<>)
         */
        System.out.println("generate() function");
        Stream.generate(()->"SG")
                .limit(3)
                .forEach(System.out::println);
        System.out.println("----------------------");
    }

    public static void NumericStream()
    {
        //range and rangeClosed

        //IntStream
        System.out.println("IntStream.range()");
        IntStream.range(1,10).forEach(System.out::println);
        System.out.println("---------------");

        System.out.println("IntStream.rangeClosed()");
        IntStream.rangeClosed(1,10).forEach(System.out::println);
        System.out.println("---------------");

        //DoubleStream
        System.out.println("IntStream.range().asDoubleStream()");
        IntStream.range(1,10).asDoubleStream().forEach(System.out::println);
        System.out.println("---------------");

        System.out.println("IntStream.rangeClosed().asDoubleStream()");
        IntStream.rangeClosed(1,10).asDoubleStream().forEach(System.out::println);
        System.out.println("---------------");

        /*
        boxing() and unboxing()
         */
        List<Integer> li=new ArrayList<>();
        li.add(1);
        li.add(2);
        li.add(3);
        li.add(4);
        li.add(5);
        int sum=li.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Sum : "+sum);
    }


    public static void main(String[] args) {
        //FactoryMethods();
        NumericStream();
    }
}
