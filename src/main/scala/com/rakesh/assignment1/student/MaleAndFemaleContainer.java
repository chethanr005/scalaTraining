package com.rakesh.assignment1.student;

import java.util.List;
import java.util.stream.Collectors;

//1. get no of male and female students , Return result in class MaleAndFemalContainer(int males, int females)

public class MaleAndFemaleContainer {
    public long maleCount(List<Student> StudentList)
    {
        return StudentList.stream().filter((s)->{
            return s.getGender().equals("male");
        }).count();
    }
    public long femaleCount(List<Student> StudentList)
    {
        return StudentList.stream().filter((s)->{
            return s.getGender().equals("female");
        }).count();
    }

    //2. Add Prefix to each student's name ,  Mr. or Ms. and return

    public List<String> studentNames(List<Student> StudentList)
    {
        List<String> names=StudentList.stream().map((s)->updateName(s.getGender(),s.getName()))
                .collect(Collectors.toList());
                return names;
    }
    private String updateName(String gender,String name)
    {
        if(gender.equals("male")) {
            name = "Mr." + name;
            return name;
        }
        else
        {
            name="Ms."+name;
            return name;
        }
    }
}
