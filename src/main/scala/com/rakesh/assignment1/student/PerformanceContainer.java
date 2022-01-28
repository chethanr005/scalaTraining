package com.rakesh.assignment1.student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//5. group students based on GPA with below criteria, Return result in class PerformanceContainer(String level, int students)

public class PerformanceContainer {
    private long averageStudent(List<Student> studentList)
    {
        return studentList.stream().filter(s->s.getGpa()>=4.1 && s.getGpa()<=7.1).count();
    }
    private long excellentStudent(List<Student> studentList)
    {
        return studentList.stream().filter(s->s.getGpa()>7.1).count();
    }
    private long poorStudent(List<Student> studentList)
    {
        return studentList.stream().filter(s->s.getGpa()<4.1).count();
    }
    public  Map<String, Long> performanceList(List<Student> studentList)
    {
        Map<String,Long> pl=new HashMap<>();
        pl.put("Average",averageStudent(studentList));
        pl.put("Excellent",excellentStudent(studentList));
        pl.put("Poor",poorStudent(studentList));

        return pl;
    }
}
