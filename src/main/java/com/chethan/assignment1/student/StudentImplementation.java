package com.chethan.assignment1.student;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Chethan on Jan 31, 2022.
 */

public class StudentImplementation {

    public static List<Student> studentsList = Arrays.asList(new Student("rose", 7.5, "female", 10, Arrays.asList("swimming", "cycling")),
            new Student("julie", 7.0, "female", 9, Arrays.asList("hiking", "running")),
            new Student("tony", 3.6, "male", 8, Arrays.asList("travelling", "swimming", "walking")),
            new Student("kail", 6.1, "female", 10, Arrays.asList("hiking", "walking")),
            new Student("kate", 6.9, "female", 10, Arrays.asList("hiking", "cycling", "walking")),
            new Student("anthony", 8.1, "male", 10, Arrays.asList("swimming")),
            new Student("andrew", 5.4, "male", 8, Arrays.asList("cycling", "running")),
            new Student("swift", 9.1, "female", 9, Arrays.asList("hiking", "travelling", "walking")),
            new Student("cooper", 8.7, "male", 10, Arrays.asList("travelling", "cycling", "hiking", "walking")),
            new Student("hailey", 5.0, "female", 9, Arrays.asList("walking", "cycling")));


    //No of male and female student implementation
    public static MaleAndFemaleContainer getMaleAndFemale(List<Student> studentList) {
        return new MaleAndFemaleContainer(studentList.stream().filter(i -> i.getGender() == "male").count(),
                studentList.stream().filter(i -> i.getGender() == "female").count());
    }

    //Adds Prefix to student names
    public static List<String> getPrefixStudentsName(List<Student> studentList) {
        return studentList.stream().map(i -> {
            if (i.getGender() == "male") return "Mr." + i.getName();
            else return "Ms." + i.getName();
        }).collect(Collectors.toList());
    }

    // No of Students according to Grade level - 1
    public static GradeLevelContainer getGradeLevel(List<Student> studentList, int gradeLevel) {
        long NoOfStudents = studentList.stream().filter(i -> i.getGradeLevel() == gradeLevel).distinct().count();
        return new GradeLevelContainer(gradeLevel, NoOfStudents);
    }

//    // No of Students according to Grade level - 2
//    public static List<GradeLevelContainer> getGradeLevel2(List<Student> studentList){
//            List<Integer> distinctGrade= studentList.stream().map(i -> i.getGradeLevel()).distinct().collect(Collectors.toList());
//        System.out.println(distinctGrade);
//            List<GradeLevelContainer> gradeLevelList=distinctGrade.stream().map(i ->
//                 new GradeLevelContainer(i,studentList.stream().filter(k -> i == k.getGradeLevel()).count()))
//                                                                  .collect(Collectors.toList());
//            return gradeLevelList;
//        }

    // No of Students according to Grade level - 3
    public static Map<Integer, Long> getGradeLevel3(List<Student> studentList) {
        return studentList.stream().map(i -> i.getGradeLevel()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    //No of students participating in each type of activity-1
    public static ActivityContainer getActivityContainer(List<Student> studentList, String activity) {
        List<String> activityList = studentList.stream().map(i -> i.getActivities()).flatMap(k -> k.stream()).distinct().collect(Collectors.toList());
        long         studentCount = studentList.stream().filter((k -> k.getActivities().contains(activity))).count();
        return new ActivityContainer(activity, studentCount);
    }

    //No of students participating in each type of activity-2
    public static Map<String, Long> getActivityContainer2(List<Student> studentList) {
        Map<String, Long> m1               = new TreeMap<String, Long>();
        List<String>      distinctActivity = studentList.stream().map(i -> i.getActivities()).flatMap(k -> k.stream()).distinct().collect(Collectors.toList());
        distinctActivity.stream().forEach(i -> {
            m1.put(i, studentList.stream().filter(k -> k.getActivities().contains(i)).count());
        });
        return m1;
    }


    //Group students based on GPA
    public static PerformanceContainer getPerformanceContainer(List<Student> studentList, String gpa) {
        if (gpa == "poor") return new PerformanceContainer(gpa, studentList.stream().filter(i -> 0 <= i.getGpa() && i.getGpa() <= 4.0).count());
        else if (gpa == "average") return new PerformanceContainer(gpa, studentList.stream().filter(i -> 4.1 <= i.getGpa() && i.getGpa() <= 7.0).count());
        else return new PerformanceContainer(gpa, studentList.stream().filter(i -> 7.1 <= i.getGpa()).count());
    }

    //Group students based on GPA-2
    public static Map<String, Long> getPerformanceContainer2(List<Student> studentList) {
        Map<String, Long> performanceGroup = new TreeMap<String, Long>();
        performanceGroup.put("Average", studentList.stream().filter(i -> 4.1 <= i.getGpa() && i.getGpa() <= 7.0).count());
        performanceGroup.put("Excellent", studentList.stream().filter(i -> 7.1 <= i.getGpa()).count());
        performanceGroup.put("Poor", studentList.stream().filter(i -> 0 <= i.getGpa() && i.getGpa() <= 4.0).count());
        return performanceGroup;
    }
}