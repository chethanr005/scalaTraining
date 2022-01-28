package com.kishor.assignment1.Student;
import java.util.*;
import java.util.stream.Collectors;

public class ActivityContainer {
    public Map<String,Long> activityCount (List<Student> students) {
        return students.stream()
                .map(student -> student.getActivities())
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    }
}
