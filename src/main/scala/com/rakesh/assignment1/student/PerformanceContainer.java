package com.rakesh.assignment1.student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerformanceContainer {
    private long AverageStudent(List<Student> studentList)
    {
        return studentList.stream().filter(s->s.getGpa()>=4.1 && s.getGpa()<=7.1).count();
    }
    private long ExcellentStudent(List<Student> studentList)
    {
        return studentList.stream().filter(s->s.getGpa()>7.1).count();
    }
    private long PoorStudent(List<Student> studentList)
    {
        return studentList.stream().filter(s->s.getGpa()<4.1).count();
    }
    public  Map<String, Long> PerformanceList(List<Student> studentList)
    {
        Map<String,Long> pl=new HashMap<String,Long>();
        pl.put("Average",AverageStudent(studentList));
        pl.put("Excellent",ExcellentStudent(studentList));
        pl.put("Poor",PoorStudent(studentList));

        return pl;
    }
}
