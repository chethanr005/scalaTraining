package com.kishor.assignment1.Student;
import java.util.List;
import java.util.stream.Collectors;
public class performanceContainer {
    public static void performerGroup () {
        List<Double> mapList = StudentDataBase.getAllStudents().stream().map(s -> s.getGpa())
                .collect(Collectors.toList());
        mapList.forEach((k) -> {
            if(k>=0 && k<=4.0)
                System.out.println("GPA "+k+"\t Poor");
            else if(k>=4.1 && k<=7.0)
                System.out.println("GPA "+k+"\t Average");
            else
                System.out.println("GPA "+k+"\t Excellent");
        });
    }

    public static void main(String[] args) {
        performerGroup();
    }
}
