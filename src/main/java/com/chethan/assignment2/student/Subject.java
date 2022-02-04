package com.chethan.assignment2.student;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Chethan on Feb 02, 2022.
 */

public class Subject {

    private final int    id;
    private final String name;

    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //Getters
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public static List<Integer> getIdList(List<Subject> subjectList) {
        return subjectList.stream().map(m -> m.getId()).collect(Collectors.toList());
    }

    public static List<String> getSubjectList(List<Subject> subjectList) {
        return subjectList.stream().map(m -> m.getName()).collect(Collectors.toList());
    }

}
