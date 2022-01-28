package com.kishor.assignment1.student;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 1. get no of male and female students , Return result in class MaleAndFemaleContainer(int males, int females)
 * 2. Add Prefix to each student's name ,  Mr. or Ms. and return
 */
public class MaleAndFemalContainer {
    public long maleCount(List<Student> male) {
        return male.stream()
                .map(Student::getGender)
                .filter(student -> student.equals("male"))
                .count();
    }
    public long femaleCount(List<Student> female) {
        return female.stream()
                .map(Student::getGender)
                .filter(student -> student.equals("female"))
                .count();
    }
    public List<String> studentNames(List<Student> students) {
        return students.stream()
                .map(s->prefix(s))
                .collect(Collectors.toList());
    }
    private String prefix(Student student) {
        if (student.getGender() == "male") {
            return "Mr. " + student.getName();
        }
        else {
            return "Ms. " + student.getName();
        }
    }
}