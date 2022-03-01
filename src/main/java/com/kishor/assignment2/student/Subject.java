package com.kishor.assignment2.student;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kishor on Feb 02, 2022.
 */
public class Subject {
    int    subjectId;
    String subjectName;
    public List<Integer> ids;

    Subject(int subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public static List<Integer> getIdList(List<Subject> subject) {
        return subject.stream().map(Subject::getSubjectId).collect(Collectors.toList());
    }

    public static int giveId(String subject) {
        return StudentImplementation.subjects.stream().filter(f -> f.subjectName == subject).map(m -> m.subjectId).findAny().get();
    }
}
