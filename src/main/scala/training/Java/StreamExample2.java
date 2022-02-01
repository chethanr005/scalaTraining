package training.Java;
import java.util.List;
import static java.util.stream.Collectors.toList;
public class StreamExample2 {
    public static void main(String[] args) {
        //StreamExample2 s1 = new StreamExample2();
        System.out.println(printStudentActivitis());
        System.out.println(Countofactivities());
    }
    public static List<String> printStudentActivitis(){
        List<String> studentActivities = StudentDataBase.getAllStudents().stream()
                .map(Student::getActivities)
                .flatMap(List::stream)
                .distinct()
                .sorted()                                                   //here count cannot be collected because it will return int or long type.
                .collect(toList());
        return studentActivities;
    }
    public static Long Countofactivities(){
        Long count = StudentDataBase.getAllStudents().stream()
                .map(Student::getActivities)
                .flatMap(List::stream)
                .distinct()
                .count();
                return count;
    }
}
