package training.Java;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CustomizedSorting {
    public static void main(String[] args) {
        //sortByName().forEach(System.out::println);
        sortByGpadesc().forEach(System.out::println);
    }
    public static List<Student> sortByName() {
        List<Student> sortedstudentList = StudentDataBase.getAllStudents().stream()
                .sorted(Comparator.comparing(Student::getName))
                 .collect(toList());
        return sortedstudentList;
    }
    public static List<Student> sortByGpadesc() {
        List<Student> sortedstudentList = StudentDataBase.getAllStudents().stream()
                .distinct()
                .sorted(Comparator.comparing(Student::getGpa).reversed())
                .collect(toList());
        return sortedstudentList;
    }
}
