package training.Java;

import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ConsumerExample {
    static Consumer<Student> consumer1 = (student) -> System.out.println(student);
    static Consumer<Student> consumer2 = (student) -> System.out.println(student.getName());
    static Consumer<Student> consumer3 = (student) -> System.out.println(student.getActivities());
    static BiConsumer<String, List<String>> biConsumer = (name,activities) -> System.out.println(name+" : "+activities);

    public static void printStudents(){
        List<Student> l1 = StudentDataBase.getAllStudents();
        l1.forEach(consumer1.andThen(consumer2).andThen(consumer3));
    }
    public static void printStudentActivities() {
        List<Student> l1 = StudentDataBase.getAllStudents();
        l1.forEach((s)-> {
            if(s.getGradeLevel()>=3 && s.getGpa()>3.9)
                consumer2.andThen(consumer3).accept(s);
        });
    }
    public static void nameAndActivities() {
        List<Student> l1 = StudentDataBase.getAllStudents();
        l1.forEach(student -> biConsumer.accept(student.getName(),student.activities));
    }
    public static void main(String[] args) {
        Consumer<String> consumer = (s) -> System.out.println(s.toUpperCase());

        consumer.accept("reactore india pvt ltd");
        //printStudents();
        printStudentActivities();
        nameAndActivities();
    }
}
