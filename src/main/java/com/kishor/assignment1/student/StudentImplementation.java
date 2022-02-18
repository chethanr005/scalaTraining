package com.kishor.assignment1.student;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kishor on Jan 31, 2022.
 */

public class StudentImplementation {

    public static MaleAndFemaleContainer maleAndFemaleCount(List<Student> students) {
        long maleCount   = students.stream().filter(f -> f.getGender().equals("male")).count();
        long femaleCount = students.stream().filter(f -> f.getGender().equals("female")).count();
        return new MaleAndFemaleContainer(maleCount, femaleCount);
    }

    public static List<String> addPrefixToStudents(List<Student> students) {
        List<String> male   = students.stream().filter(m -> m.getGender().equals("male")).map(m -> "Mr. " + m.getName()).collect(Collectors.toList());
        List<String> female = students.stream().filter(m -> m.getGender().equals("female")).map(m -> "Ms. " + m.getName()).collect(Collectors.toList());
        male.addAll(female);
        return male;
    }

    public static List<GradeLevelContainer> getAllGradeLevel(List<Student> students) {
        List<Integer> distinctGrade = students.stream().map(m -> m.getGradeLevel()).distinct().collect(Collectors.toList());
        return distinctGrade.stream().
                            map(m -> {
                                Long count = students.stream().filter(f1 -> f1.getGradeLevel() == m).count();
                                return new GradeLevelContainer(m, count);
                            }).collect(Collectors.toList());
    }

    public static List<ActivityContainer> activityContainers(List<Student> students) {
        List<String> distinctActivities = students.stream().map(m -> m.getActivities().stream().collect(Collectors.toList())).flatMap(f -> f.stream()).distinct().collect(Collectors.toList());
        return distinctActivities.stream().map(m1 -> {
            Long count = students.stream().map(m2 -> m2.getActivities()).flatMap(f -> f.stream()).filter(f -> f.equals(m1)).count();
            return new ActivityContainer(m1, count);
        }).collect(Collectors.toList());
    }

    public static List<PerformanceContainer> getPerformanceOfStudents(List<Student> students) {
        Long poor      = students.stream().filter(f -> f.getGpa() >= 0.0 && f.getGpa() <= 4.0).count();
        Long average   = students.stream().filter(f -> f.getGpa() >= 4.1 && f.getGpa() <= 7.0).count();
        Long excellent = students.stream().filter(f -> f.getGpa() >= 7.1).count();
        return Arrays.asList(new PerformanceContainer("poor", poor),
                new PerformanceContainer("average", average),
                new PerformanceContainer("excellent", excellent));
    }
}
