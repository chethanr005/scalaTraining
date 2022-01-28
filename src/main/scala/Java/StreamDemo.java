package Java;

import scala.Int;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class StreamDemo {

    public static long checkefficient(Supplier<Integer> task,int n)
    {
        long starttime= System.currentTimeMillis();
        for(int i=1;i<=n;i++)
        {
            task.get();
        }
        long endtime=System.currentTimeMillis();
        return endtime-starttime;
    }

    public static int sumSequentialStream()
    {
        return IntStream.rangeClosed(1,200000).sum();
    }
    public static int sumParallelStream()
    {
        return IntStream.rangeClosed(1,200000).parallel().sum();
    }

    public static void main(String[] args) {
        List<Integer> li= Arrays.asList(1,2,3,3,2,1);
        //Sum of element List
        Optional<Integer> lisum=li.stream().reduce( Integer::sum);
        int sum=(lisum.isPresent())? lisum.get() : 0;
        System.out.println(sum);


        //Efficient Stream
        System.out.println("Sequential Stream : "+checkefficient(StreamDemo::sumSequentialStream,10));
        System.out.println("Parallel Stream : "+checkefficient(StreamDemo::sumParallelStream,10));
        System.out.println("Available Process in System : "+Runtime.getRuntime().availableProcessors());

        /*
        * --- ofNullable ---
        */
        Optional<String> da=Optional.ofNullable((5%2==0)?null:"Dark");
        System.out.println(da);
        /*
        *
        */

        /*
         * --- of ---
         */
        Optional<String> da2=Optional.of("Alpha");
        Optional<String> da3=Optional.ofNullable(null);
        System.out.println(da2);
        System.out.println(da3);
        /*
         *
         */


//        String s="Hello";
//        String s1=null;
//        String s2=s+ null;
//        System.out.println(s2);
//        int a45=45;
//        String s23= ""+a45;
//        String s45="45";
//        int b45=Integer.parseInt(s45);

    }
}
