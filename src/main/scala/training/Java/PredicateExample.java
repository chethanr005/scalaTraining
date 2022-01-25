package training.Java;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class PredicateExample {
    public static void main(String[] args) {
        Predicate <Integer> predicate = (p) -> p%2==0;
        BiPredicate<Integer,Integer> greater = (a,b) -> a>b;

        System.out.println(predicate.test(5));
        System.out.println(greater.test(5,2));
    }
}
