package com.kishor.assignment1.Student;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class gradeLevelContainer {
    public void gradeCount (List<Student> students) {
        Map<Integer,Long> mapList = students.stream().map(s -> s.getGradeLevel())
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        mapList.forEach((k,v) -> System.out.println("Grade "+k+": "+v+" times"));
    }
}
