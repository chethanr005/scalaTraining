package com.kishor.assignment1.Student;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class activityContainer {
    public void activityCount (List<Student> students) {
        Map<String,Long> maplist = students.stream()
                .map(student -> student.getActivities())
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        maplist.forEach((k,v) -> System.out.println(k+": "+v));
    }
}
