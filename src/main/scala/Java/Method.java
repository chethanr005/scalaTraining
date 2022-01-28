package Java;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Method implements dark{
     static List<Integer> li= Arrays.asList(1,5,2,16,4,27,7);

    public static void main(String[] args) {
//        li.sort(Comparator.naturalOrder());
//        System.out.println(li);
//        li.sort(Comparator.reverseOrder());
//        System.out.println(li); Can override default method.

        dark da=new Method();
        System.out.println("Is Prime : "+da.IsPrime(29));
        System.out.println("Is Even : "+dark.IsEven(21));
    }

    @Override
    public void display() {
        System.out.println("Abstract Method");
    }
}
