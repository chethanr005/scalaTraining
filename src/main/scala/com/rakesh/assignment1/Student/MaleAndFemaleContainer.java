package com.rakesh.assignment1.Student;

import java.util.List;
import java.util.stream.Collectors;

public class MaleAndFemaleContainer {
    public long MaleCount(List<Student> StudentList)
    {
        return StudentList.stream().filter((s)->{
            return s.getGender().equals("male");
        }).count();
    }
    public long FemaleCount(List<Student> StudentList)
    {
        return StudentList.stream().filter((s)->{
            return s.getGender().equals("female");
        }).count();
    }
    public List<String> StudentNames(List<Student> StudentList)
    {
        List<String> names=StudentList.stream().map((s)->UpdateName(s.getGender(),s.getName()))
                .collect(Collectors.toList());
                return names;
    }
    private String UpdateName(String gender,String name)
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
