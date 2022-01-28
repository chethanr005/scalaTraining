package training.Java;

import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

public class MapExample {
    public static List<Double> nameList(){
        List<Double> studentNames = StudentDataBase.getAllStudents().stream()
                .map(Student -> Student.getGpa()+2)
                //.map(MapExample::xyz)
                .collect(toList());
        return studentNames;
    }
   static Double abc(Student student)
   {
        return student.getGpa()+2.0;
   }
   static Double xyz(Double a)
   {
       return a*2;
   }
    public static void main(String[] args) {
        System.out.println(nameList());
    }
}
//An Intermediate operation , it'll invoke on a stream instance and after processing they give stream instance as o/p
//used only for transformation. One I/p => one o/p