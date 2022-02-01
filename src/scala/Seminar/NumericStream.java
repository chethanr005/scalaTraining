package Seminar;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NumericStream {

        public static void numericStream()
        {

            List<Integer>list= Arrays.asList(1,2,3,4,5,6,7,8,9,10);
            Optional<Integer> Sum=list.stream().reduce(Integer::sum);
            System.out.println(Sum);

            //range and rangeClosed
            //IntStream
            System.out.println("IntStream.range()");
            IntStream.range(1,10).forEach(System.out::println);
            System.out.println(IntStream.range(1,10).count());
            System.out.println("---------------");

            System.out.println("IntStream.rangeClosed()");
            IntStream.rangeClosed(1,10).forEach(System.out::println);
            System.out.println("---------------");

            System.out.println();
            //DoubleStream
            System.out.println("IntStream.range().asDoubleStream()");
            IntStream.range(1,10).asDoubleStream().forEach(System.out::println);
            System.out.println("---------------");

            System.out.println("IntStream.rangeClosed().asDoubleStream()");
            IntStream.rangeClosed(1,10).asDoubleStream().forEach(System.out::println);
            System.out.println("---------------");

        /*
        boxing() and unboxing()
         */
            //boxing
            List<Integer> li2=IntStream.rangeClosed(1,10).boxed()
                    .collect(Collectors.toList());
            System.out.println(li2);

            //unboxing()

            //mapToInt()
            List<Integer> li=new ArrayList<>();
            li.add(1);
            li.add(2);
            li.add(3);
            li.add(4);
            li.add(5);
            int sum=li.stream().mapToInt(Integer::intValue).sum();
            System.out.println("Int Sum : "+sum);

            //mapToDouble
            double sum1=li.stream().mapToDouble((i)->i).sum();
            System.out.println("Double Sum : "+sum1);

            //mapToObject
            List<Integer> l=IntStream.rangeClosed(1,10).mapToObj(
                    (i)->new Integer(i))
                    .collect(Collectors.toList());
            System.out.println("mapToObject : "+l);

            //mapToLong

        }


        public static void main(String[] args) {
            //FactoryMethods();
            numericStream();
        }

}
