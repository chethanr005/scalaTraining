package com.chethan.assignment3.student;


import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Chethan on Feb 14, 2022.
 */

public class StudentImplementation {


    // 1. No of male and female student implementation
    public static MaleAndFemaleContainer getMaleAndFemale(List<Student> studentList) {
        return new MaleAndFemaleContainer(studentList.stream().filter(i -> i.getGender().equals("male")).count(),
                studentList.stream().filter(i -> i.getGender().equals("female")).count());
    }

    // 2. Adds Prefix to student names
    public static List<String> getPrefixStudentsName(List<Student> studentList) {
        return studentList.stream().map(i -> {
            if (i.getGender().equals("male")) return "Mr." + i.getName();
            else return "Ms." + i.getName();
        }).collect(Collectors.toList());
    }

    // 3. No of Students according to Grade level - 1
    public static GradeLevelContainer getGradeLevel(List<Student> studentList, int gradeLevel) {
        long NoOfStudents = studentList.stream().filter(i -> i.getGradeLevel() == gradeLevel).distinct().count();
        return new GradeLevelContainer(gradeLevel, NoOfStudents);
    }

    // 3. No of Students according to Grade level - 2
    public static Map<Integer, Long> getGradeLevel2(List<Student> studentList) {
        return studentList.stream().map(i -> i.getGradeLevel()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }


    // 4. No of students participating in each type of activity-1
    public static ActivityContainer getActivityContainer(List<Student> studentList, String activity) {
        List<String> activityList = studentList.stream().flatMap(k -> k.getActivities().stream()).distinct().collect(Collectors.toList());
        long         studentCount = studentList.stream().filter((k -> k.getActivities().contains(activity))).count();
        return new ActivityContainer(activity, studentCount);
    }

    // 4. No of students participating in each type of activity-2
    public static Map<String, Long> getActivityContainer2(List<Student> studentList) {
        Map<String, Long> m1               = new TreeMap<String, Long>();
        List<String>      distinctActivity = studentList.stream().flatMap(k -> k.getActivities().stream()).distinct().collect(Collectors.toList());
        distinctActivity.stream().forEach(i -> {
            m1.put(i, studentList.stream().filter(k -> k.getActivities().contains(i)).count());
        });
        return m1;
    }

    // 5. Group students based on GPA
    public static PerformanceContainer getPerformanceContainer(List<Student> studentList, String gpa) {
        if (gpa == "poor") return new PerformanceContainer(gpa, studentList.stream().filter(i -> 0 <= i.getGpa() && i.getGpa() <= 4.0).count());
        else if (gpa == "average") return new PerformanceContainer(gpa, studentList.stream().filter(i -> 4.1 <= i.getGpa() && i.getGpa() <= 7.0).count());
        else return new PerformanceContainer(gpa, studentList.stream().filter(i -> 7.1 <= i.getGpa()).count());
    }

    // 5. Group students based on GPA-2
    public static Map<String, Long> getPerformanceContainer2(List<Student> studentList) {
        Map<String, Long> performanceGroup = new TreeMap<String, Long>();
        performanceGroup.put("Average", studentList.stream().filter(i -> 4.1 <= i.getGpa() && i.getGpa() <= 7.0).count());
        performanceGroup.put("Excellent", studentList.stream().filter(i -> 7.1 <= i.getGpa()).count());
        performanceGroup.put("Poor", studentList.stream().filter(i -> 0 <= i.getGpa() && i.getGpa() <= 4.0).count());
        return performanceGroup;
    }

}
