package training.Java;

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class NumericStreamAggregateExample {
    public static void main(String[] args) {
        int sum = IntStream.rangeClosed(1,50).sum();
        System.out.println(sum);
        OptionalInt optionalInt = IntStream.rangeClosed(1,50).max();
        System.out.println(optionalInt.isPresent() ? optionalInt.getAsInt():0);
    }
}
