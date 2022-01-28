package training.Java;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class StreamsExmple {
    public static void main(String[] args) {
        Map<String, List<String>> studentMap = StudentDataBase.getAllStudents().stream()
                .filter(student -> student.getGradeLevel()>=3)
                .peek(student -> System.out.println("after first filter \t: "+student))
                .filter(student -> student.getGpa()>=3.9)
                .peek(student -> System.out.println("after second filter \t: "+student))
                .collect(Collectors.toMap(Student::getName,Student::getActivities));
        ArrayList<String> abc = new ArrayList<String>();
        abc.add("abc");
        abc.add("def");
        abc.add("ghi");
        abc.add("jkl");
        ///////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////
        System.out.println(studentMap);
        Stream<String> streams = abc.stream();
        streams.forEach(System.out::println);//we can only use this stream once
        System.out.println(nameList());
        System.out.println(nameSet());
    }

    /***
     * Maps and flat maps Example
     * @Kishor
     */
    public static List<String> nameList(){
        List<String> Student_namees = StudentDataBase.getAllStudents().stream()
                .map(Student::getName)
//              .map(Student::getActivities)
//              .flatMap(List::stream)
                .collect(toList());
            return Student_namees;
    }
    /***
     * Distinct example
     */
    public static Set<String> nameSet(){
        Set<String> StudentSet = StudentDataBase.getAllStudents().stream()
                .map(Student::getName)
//              .map(Student::getActivities)
//              .flatMap(List::stream)
                .collect(toSet());
        return StudentSet;
    }
}
