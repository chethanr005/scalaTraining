package training.Java;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

public class SuplierExample {
    static Comparator<Integer> comparator = (a,b) -> a.compareTo(b);
    static BinaryOperator<Integer> bin = BinaryOperator.maxBy(comparator);
    public static void main(String[] args) {
        Supplier<Student> sup = () -> {
          return new Student("Kish",2,9.6,"male", Arrays.asList("swimming","cricket","pubg"));
        };
        System.out.println("================"+sup.get());
        System.out.println(bin.apply(5,2));
    }
}
