package training.Java;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SkipAndLimitExample {
    public static Optional<Integer> skip (List<Integer> integers) {
        return integers.stream()
                .skip(2)
                .limit(2)
                .reduce((x,y) -> x+y);
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(6,7,8,9,10);
        Optional<Integer> s = skip(list);
        if(s.isPresent())
            System.out.println(s.get());
        else
            System.out.println("Its empty");
    }
}
