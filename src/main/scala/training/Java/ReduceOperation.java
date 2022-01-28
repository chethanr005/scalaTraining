package training.Java;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
public class ReduceOperation {
    public static int mul (List<Integer> integerList){
        return integerList.stream()                                     //a=1,b=1 (from stream) -> 1 is returned
                .reduce(1,(a,b) -> a*b);                         //a=1,b=3  -> 3
    }                                                                   //a=3,b=5 -> 15
    public static Optional<Student> getHighestGPA() {                   //a=15,b=7 -> 105
     return StudentDataBase.getAllStudents().stream()
             .reduce((stu1,stu2) -> {           //suppose if we did not provide initial value, we use this way.
                 if(stu1.getGpa()>stu2.getGpa())
                     return stu1;
                 else
                     return stu2;
             });
    }
    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1,3,5,7);
        System.out.println(mul(integers));
        Optional<Student> s = getHighestGPA();
        if(s.isPresent())
            System.out.println(s.get());
        else
            System.out.println("Its empty");
    }
}
//it is terminal operation(last operation) nd used to reduce the content of stream to a single value.
//accepts (initial value, Binary operator)