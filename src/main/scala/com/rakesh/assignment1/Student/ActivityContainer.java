package com.rakesh.assignment1.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActivityContainer {
    public Map<String, Long> ActivityCount(List<Student> studentList)
    {
        Map<String,Long>gc= new HashMap<>();
        List<String> al= StudentDataBase.getAllStudents().stream()
                .map(s->s.getActivities())
                .flatMap(s->s.stream())
                .distinct().collect(Collectors.toList());

        for(int i=0;i<al.size();i++)
        {
            int k=i;
            long c=0;
            c=StudentDataBase.getAllStudents().stream()
                    .filter(s->s.getActivities().contains(al.get(k))).count();
            gc.put(al.get(k),c);
        }
        return gc;
    }
}
