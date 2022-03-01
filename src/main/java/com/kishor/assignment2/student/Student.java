package com.kishor.assignment2.student;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Kishor on Feb 02, 2022.
 */

public class Student {
    String            name;
    String            registrationCode;
    int               standard;
    List<MarkDetails> markDetails;

    Student(String name, String registrationCode, int standard, List<MarkDetails> markDetails) {
        this.name = name;
        this.registrationCode = registrationCode;
        this.standard = standard;
        this.markDetails = markDetails;
    }

    public String getName() {
        return name;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public int getStandard() {
        return standard;
    }

    public List<MarkDetails> getMarkDetails() {
        return markDetails;
    }

    public List<Integer> getMarksList() {
        return getMarkDetails().stream().map(m -> m.getMarks()).collect(Collectors.toList());
    }

    public List<Boolean> ifAbsent() {
        return markDetails.stream().map(f -> {
            return f.marks.equals(Optional.ofNullable(null));
        }).collect(Collectors.toList());
    }

    public static List<Integer> getDistinctStandard(List<Student> students) {
        return students.stream().map(m1 -> m1.getStandard()).distinct().collect(Collectors.toList());
    }

    public static Integer getMarksList(Student students, Integer subId) {
        return students.getMarkDetails().stream().filter(f -> f.getId() == subId).map(m -> m.getMarks()).findAny().get();
    }


    public static Integer getSpecificSubjectMarks(Student student, Integer subId) {
        return student.getMarkDetails().stream().filter(f -> f.getId() == subId).map(m -> m.getMarks()).findAny().get();
    }

}
