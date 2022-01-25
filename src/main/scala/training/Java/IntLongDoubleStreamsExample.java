package training.Java;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class IntLongDoubleStreamsExample {
    public static IntStream intStream(int n) {
        return IntStream.rangeClosed(1,n);
    }
    public static LongStream longStream() {
        return LongStream.rangeClosed(1,50);
    }
    public static DoubleStream doubleStream() {
        return IntStream.rangeClosed(1,50).asDoubleStream();
    }
    public static void main(String[] args) {
        intStream(50).forEach(v -> System.out.print("\n"+v+","));
        longStream().forEach(v -> System.out.print("\n"+v+","));
        doubleStream().forEach(v -> System.out.println("\n"+v+","));
    }
}
