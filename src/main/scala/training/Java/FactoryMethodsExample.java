package training.Java;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FactoryMethodsExample {
    public static void main(String[] args) {
        Stream<String> stringStream = Stream.of("abc","def","hij");
        stringStream.forEach(System.out::println);

        Stream.iterate(0,x-> x+2)
                .limit(11)
                .forEach(System.out::println);
        Supplier<Integer> integerSupplier = new Random()::nextInt;
        Stream.generate(integerSupplier).limit(5).forEach(System.out::println);

    }
}

//of() - it is finite and creates streams by values passing to it,
//iterate() - creates infinite streams (T seed, Unary operator <T>)
//generate() - creates infinite streams (accepts Supplier)