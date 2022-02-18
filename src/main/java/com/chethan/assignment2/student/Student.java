package com.chethan.assignment2.student;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Chethan on Feb 02, 2022.
 */

public class Student {

    private final String name;
    private final String registrationCode;
    private final int    standard;
    private final List<MarkDetails> markDetails;

    Student(String name, String registrationCode, int standard, List<MarkDetails> markDetails) {
        this.name = name;
        this.registrationCode = registrationCode;
        this.standard = standard;
        this.markDetails = markDetails;
    }

    //Getters
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


    public List<Integer> getMarksList(Student student) {
        return student.getMarkDetails().stream().map(m -> m.getMarks()).collect(Collectors.toList());
    }

    public int getStudentTotal(Student student) {
        return getMarksList(student).stream().reduce(0, (a, b) -> a + b);
    }

    public int getStudentAverage(Student student) {
        return getMarksList(student).stream().reduce(0, (a, b) -> (a + b)) / 3;
    }

    public static int getTotalByStandard(List<Student> studentList, int standard) {

        List<Integer> listOfTotalByStandard = studentList.stream().filter(f -> f.getStandard() == standard).map(m -> m.getStudentTotal(m)).collect(Collectors.toList());
        return listOfTotalByStandard.stream().reduce(0, (a, b) -> a + b);
    }

    public List<Boolean> ifAbsent(Student student) {
        return student.getMarkDetails().stream().map(m -> m.getActualValue().equals(Optional.empty())).collect(Collectors.toList());
    }

    public int getSpecificSubjectMarks(Student student, int subjectID) {
        return student.getMarkDetails().stream().filter(f1 -> f1.getSubjectId() == subjectID).map(m1 -> m1.getMarks()).findAny().get();
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", registrationCode='" + registrationCode + '\'' +
                ", standard=" + standard +
                ", markDetails=" + markDetails +
                '}';
    }

    public static List<Integer> getDistinctStandard(List<Student> studentList) {
        return studentList.stream().map(m -> m.getStandard()).distinct().collect(Collectors.toList());
    }

    public int getMarksById(Student student, int subjectId) {
        return student.getMarkDetails().stream().filter(f -> f.getSubjectId() == subjectId).map(m -> m.getMarks()).findAny().get();
    }

    public static int getMaxMarks(List<Integer> intList) {
        return intList.stream().max((a, b) -> a.compareTo(b)).get();
    }

    public static List<Boolean> dummy(Student student){
        return Arrays.asList(true,false);
    }
}
