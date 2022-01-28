package Seminar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class ParallelExample {
    public static long checkEfficient(Supplier<Integer>task, int n) {
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= n; i++) {
            task.get();
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static long CheckDiff(Supplier<Optional<Integer>>Task,int n1)
    {
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= n1; i++) {
            Task.get();
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static int sumSequentStream() {
        return IntStream.rangeClosed(1, 1000).sum();
    }

    public static Optional<Integer> ListStream()
    {
        List<Integer> li=new ArrayList<>();
        for(int i=1;i<=10000;i++)
        {
            li.add(i);
        }
        return li.stream().reduce(Integer::sum);
    }

    public static int sumParaStream() {
        return IntStream.rangeClosed(1, 1000).parallel().sum();
    }

    public static void main(String[] args) {
        //Efficient Stream
        System.out.println("Sequential Stream(IntStream) : " + checkEfficient(ParallelExample::sumSequentStream, 10));
        System.out.println("Parallel Stream : " + checkEfficient(ParallelExample::sumParaStream, 10));
        System.out.println("Stream (List<Integers>) : " + CheckDiff(ParallelExample::ListStream, 10));

        System.out.println("Processor in this system : "+ Runtime.getRuntime().availableProcessors());
    }
}
