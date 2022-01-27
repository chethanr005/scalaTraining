package com.kishor.assignment1.Student;

import java.util.List;
import java.util.stream.Collectors;

public class maleAndFemalContainer {
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
