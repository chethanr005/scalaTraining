package com.kishor.assignment1.Student;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GradeLevelContainer {
    public Map<Integer,Long> gradeCount (List<Student> student) {
        return student.stream().map(s -> s.getGradeLevel())
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    }
}
