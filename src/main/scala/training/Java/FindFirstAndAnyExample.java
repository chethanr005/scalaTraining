package training.Java;
import java.util.Optional;
public class FindFirstAndAnyExample {
    public static Optional<Student> findAny() {
        return StudentDataBase.getAllStudents().stream()
                .filter(student -> student.getGpa()>=3.9)
                .findAny();
    }
    public static Optional<Student> findFirst() {
        return StudentDataBase.getAllStudents().stream()
                .filter(student -> student.getGpa()>=3.9)
                .findFirst();
    }
    public static void main(String[] args) {
        Optional<Student> s = findAny();
        if(s.isPresent())
            System.out.println("findAny:    "+s.get());
        else
            System.out.println("Its empty");

        Optional<Student> s1 = findFirst();
        if(s1.isPresent())
            System.out.println("findFirst:  "+s1.get());
        else
            System.out.println("Its empty");
    }
}
//Both returns optional type
//fa() - returns first encountered element in the stream
//ff() - returns first element in the stream
