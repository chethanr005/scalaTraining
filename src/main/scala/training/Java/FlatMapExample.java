package training.Java;
import java.util.List;
import static java.util.stream.Collectors.toList;

public class FlatMapExample {
    public static List<String> nameSet(){
     return StudentDataBase.getAllStudents().stream()
             .map(Student::getActivities)
             .flatMap(List::stream)
             .collect(toList());
    }
    public static void main(String[] args) {
        System.out.println(nameSet());
    }
}
//flat map produces stream of stream values
//one to many
//performs both transformation and flattening