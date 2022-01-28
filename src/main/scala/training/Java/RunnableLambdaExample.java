package training.Java;

import java.util.Comparator;

public class RunnableLambdaExample {
    public static void main(String[] args) {
        /***
         * Imperative method
         */
        Runnable run =new Runnable() {
            @Override
            public void run() {
                System.out.println("Imperative Runnable");
            }
        };
        ///////////////////////////////////////////////////////////
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);    //if it return 0 -> o1=o2
                                                        // 1 -> o1>o2
                                                        // -1 -> o1<o2
            }
        };
        ///////////////////////////////////////////////////////////
        /***
         * Declarative Method
         */
        Runnable run1 = ()->System.out.println("Declarative Method");
        Comparator<Integer> comparator1 = (a,b) -> a.compareTo(b);
        //////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////
        new Thread(run).start();
        new Thread(run1).start();
        //////////////////////////////////////////////////////////
        System.out.println("Result of Comparator using first method :"+comparator.compare(5,2));
        System.out.println("Result of Comparator using second method :"+comparator1.compare(5,2));
        //////////////////////////////////////////////////////////

    }
}
