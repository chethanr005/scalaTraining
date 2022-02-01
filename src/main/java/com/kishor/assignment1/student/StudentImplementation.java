package com.kishor.assignment1.student;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Kishor on Jan 31, 2022.
 */

public class StudentImplementation {
    public static MaleAndFemalContainer maleAndFemaleCount(List<Student> students) {
        long maleCount   = students.stream().filter(f -> f.getGender().equals("male")).count();
        long femaleCount = students.stream().filter(f -> f.getGender().equals("female")).count();
        return new MaleAndFemalContainer(maleCount, femaleCount);
    }

    public static List<String> addPrefixToStudents(List<Student> students) {
        List<String> male   = students.stream().filter(m -> m.getGender().equals("male")).map(m -> "Mr. " + m.getName()).collect(Collectors.toList());
        List<String> female = students.stream().filter(m -> m.getGender().equals("female")).map(m -> "Ms. " + m.getName()).collect(Collectors.toList());
        male.addAll(female);
        return male;
    }

    public static GradeLevelContainer gradeLevelContainer(long gradeLevel, List<Student> students) {
        long noOfStudents = students.stream().filter(f -> f.getGradeLevel() == gradeLevel).count();
        return new GradeLevelContainer(noOfStudents);
    }

    public static GradeLevelContainer gradeLevelContainers(List<Student> students) {
        Map<Integer, Long> gradeCount = students.stream().map(s -> s.getGradeLevel()).collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        return new GradeLevelContainer(gradeCount);
    }

    public static List<ActivityContainer> activityContainers(List<Student> students) {
        Map<String, Long> activityCount = students.stream().map(student -> student.getActivities()).flatMap(List::stream).collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        return Arrays.asList(new ActivityContainer(activityCount.keySet(), activityCount.values()));
    }

    public static PerformanceContainer performanceContainer(List<Student> students) {
        Long              a              = students.stream().filter(f -> f.getGpa() >= 0.0 && f.getGpa() <= 4.0).count();
        Long              b              = students.stream().filter(f -> f.getGpa() >= 4.1 && f.getGpa() <= 7.0).count();
        Long              c              = students.stream().filter(f -> f.getGpa() >= 7.1).count();
        Map<String, Long> performerGroup = new HashMap<String, Long>();
        performerGroup.put("poor", a);
        performerGroup.put("Average", b);
        performerGroup.put("Excellent", c);
        return new PerformanceContainer(performerGroup);
    }

    public static PerformanceContainer performanceContainers(List<Student> students, String s) {
        Map<String, Long> res = performanceContainer(students).students;
        Long              r   = res.get(s);
        return new PerformanceContainer(r);
    }
}
