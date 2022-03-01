package com.rakesh.assignment2.student;

import java.util.List;
import java.util.Optional;

/**
 * Created by Rakesh on Feb 02, 2022.
 */

public class MarkDetailsContainer {

    int               subjectId;
    Optional<Integer> marks;

    public MarkDetailsContainer(int subjectId, Optional<Integer> marks) {
        List<Integer> ids = Subject.getIdList(StudentImplementation.subjectGroup());
        if (ids.contains(subjectId)) {
            this.subjectId = subjectId;
            this.marks = marks;
        } else {
            throw new RuntimeException("Subject ID not present..");
        }
    }

    public int getSubjectId() {
        return subjectId;
    }

    public int getMarks() {
        if (marks.isPresent())
            return marks.get();
        else
            return 0;
    }

    @Override
    public String toString() {
        return "ID-" + subjectId + ", Marks-" + marks;
    }
}
