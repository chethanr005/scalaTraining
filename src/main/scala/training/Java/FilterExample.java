package training.Java;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class FilterExample {
    public static List<Student> filterExample() {
        return StudentDataBase.getAllStudents().stream()
                .filter(student -> student.getGender().equals("male"))
                .collect(toList());
    }
    public static void main(String[] args) {
        //System.out.println(filterExample());
        filterExample().forEach(System.out::println);
    }
}
