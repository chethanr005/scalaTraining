package Seminar;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
//Terminal Operators
public class Terminators {
    public static void main(String[] args) {
        List<Integer> li=Arrays.asList(1,2,3,4,5,7,8);


        //foreach()
        li.stream().forEach(System.out::println);

        //reduce()
        System.out.println("Sum of List : "+li.stream().reduce(Integer::sum));

        //collect()
        List<String> names=Arrays.asList("Abc","Bca","Cab");
        //joining() or joining( , , );
        System.out.println("Joining : "+names.stream().collect(Collectors.joining(" ","[","]")));

        //counting()
        System.out.println("Counting : "+names.stream().collect(Collectors.counting()));

        //mapping()
        List<Student> studentlist=StudentDataBase.getAllStudents();
        System.out.println(studentlist.stream().collect(Collectors
                        .mapping(Student::getName,Collectors.toSet())));

        //maxBy()
        System.out.println("maxBy : "+studentlist.stream().collect(Collectors
                .maxBy(Comparator.comparing(Student::getGpa))).get());

        //minBy()
        System.out.println("minBy : "+studentlist.stream().collect(Collectors
                .minBy(Comparator.comparing(Student::getGpa))).get());

        //groupingBy()
        Map<String , List<Integer>> group=li.stream().collect(Collectors.groupingBy(s->s%2==0?"Even":"Odd"));
        System.out.println(group);

        //partitioningBy()
        Predicate<Integer> res=(i)->i%2==0;
        Map<Boolean,List<Integer>> pb=li.stream().collect(Collectors.partitioningBy(res));
        System.out.println(pb);

    }
}
