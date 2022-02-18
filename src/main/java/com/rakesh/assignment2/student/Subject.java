package com.rakesh.assignment2.student;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rakesh on Feb 02, 2022.
 */

public class Subject {


    int    id;
    String name;
    public static List<Integer> idList;


    public int getId() {
        return id;
    }

    public Subject(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public static List<Integer> getIdList(List<Subject> subjectList) {
        idList = subjectList.stream().map(Subject::getId).collect(Collectors.toList());
        return idList;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
