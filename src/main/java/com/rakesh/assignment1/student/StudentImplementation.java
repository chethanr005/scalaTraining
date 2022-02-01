package com.rakesh.assignment1.student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Rakesh on Jan 31, 2022.
 */

public class StudentImplementation {
    public static List<Student> list =StudentDataBase.getAllStudents();

    //1. get no of male and female students , Return result in class MaleAndFemalContainer(int males, int females)
    public static MaleAndFemaleContainer maleAndFemaleCounts(List<Student> studentList)
    {
        long males=studentList.stream().filter(s->s.getGender().equals("male")).count();
        long females=studentList.stream().filter(s->s.getGender().equals("female")).count();
        return new MaleAndFemaleContainer(males,females);

    }

    //2. Add Prefix to each student's name ,  Mr. or Ms. and return
    public static List<String> getNames(List<Student> studentList)
    {
        return studentList.stream().map((s) -> updateName(s.getGender(), s.getName())).sorted().collect(Collectors.toList());
    }

    private static String updateName(String gender, String name) {
        if(gender.equals("male")) {
            name = "Mr." + name;
        } else {
            name = "Ms." + name;
        }
        return name;
    }

    //3. get no of students according to Grade level , Return result in class GradeLevelContainer(int gradeLevel, int students)
    public static GradeLevelContainer countBasedOnGradeLevel(List<Student> studentList,int gradeLevel)
    {
        long count= studentList.stream().filter(s->s.getGradeLevel()==gradeLevel).count();
        return new GradeLevelContainer(count);
    }
    public static GradeLevelContainer getAllGradeCount(List<Student> studentList)
    {
        Map<Integer,Long> result= studentList.stream().collect(Collectors.groupingBy(Student::getGradeLevel,Collectors.counting()));
        return  new GradeLevelContainer(result);
    }


    //4. get no of students participating in each type of activity , Return result in class ActivityContainer(String activity, int students)
    public static ActivityContainer countBasedOnActivity(List<Student> studentList,String activity)
    {
        long count=studentList.stream().filter(s->s.getActivities().contains(activity)).count();
        return new ActivityContainer(count);
    }
    public static ActivityContainer ActivityGroupMap(List<Student> studentList)
    {
        List<String> activities=studentList.stream().map(s->s.getActivities()).flatMap(s->s.stream()).distinct().collect(Collectors.toList());
        Map<String,Long> result = new HashMap<>();
        for(int i=0;i<activities.size();i++)
        {
            int k=i;
            long c=0;
            c=studentList.stream().filter(s->s.getActivities().contains(activities.get(k))).count();
            result.put(activities.get(k),c);
        }
        return new ActivityContainer(result);
    }


    //5. group students based on GPA with below criteria, Return result in class PerformanceContainer(String level, int students)
    public static PerformanceContainer countByLevel(List<Student> studentList,String level)
    {
        long count=0l;
        if(level.equals("Average"))
        {
            count=studentList.stream().filter(s->s.getGpa()>=4.1 && s.getGpa()<=7.1).count();
            return new PerformanceContainer(count);
        }
        else if(level.equals("Excellent"))
        {
            count=studentList.stream().filter(s->s.getGpa()>7.1).count();
            return new PerformanceContainer(count);
        }
        else
        {
            count=studentList.stream().filter(s->s.getGpa()<4.1).count();
            return new PerformanceContainer(count);
        }
    }
    public static PerformanceContainer getGpaLevelMap(List<Student>studentList)
    {
        Map<String,Long> result=new HashMap<>();
        result.put("Average",studentList.stream().filter(s->s.getGpa()>=4.1 && s.getGpa()<=7.1).count());
        result.put("Excellent",studentList.stream().filter(s->s.getGpa()>7.1).count());
        result.put("Poor",studentList.stream().filter(s->s.getGpa()<4.1).count());
        return new PerformanceContainer(result);
    }
}
