package Java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class demo {
    static List<Character> li= Arrays.asList('A','B','C','D');

    public static List<Character> loop(int n, List<Character> li) {
        int len = li.size();
        List<Character> res=new ArrayList<Character>();
        for (Character in : li) {
            int i = n;
            while (i != 0) {
               // System.out.print(in);
                res.add(in);
                i--;
            }
           // System.out.print(" ");
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println(loop(6,li));
    }
}
