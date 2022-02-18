package com.chethan.assignment2.student;

import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * Created by Chethan on Feb 02, 2022.
 */

public class MarkDetails {
    private final int               subjectId;
    private final Optional<Integer> marks;

    public MarkDetails(int subjectId, Optional<Integer> marks) throws Exception {
        if (Subject.getIdList(StudentImplementation.getSubject()).contains(subjectId)) {
            this.subjectId = subjectId;
            this.marks = marks;
        } else throw new FileNotFoundException("Subject Not Found!!!!!!!!!!!");
    }

    //Getters
    public int getMarks() {
        if (marks.isPresent())
            return marks.get();
        else
            return 00;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public Optional<Integer> getActualValue() {
        return marks;
    }

}
