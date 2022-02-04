package com.kishor.assignment2.student;

import java.util.Optional;

/**
 * Created by Kishor on Feb 02, 2022.
 */
public class MarkDetails {
    int               id;
    Optional<Integer> marks;

    MarkDetails(int id, Optional<Integer> marks) {
        if (Subject.getIdList(StudentImplementation.subjects).contains(id)) {
            this.id = id;
            this.marks = marks;
        }
    }

    public int getMarks() {
        if (marks.isPresent())
            return marks.get();
        else
            return 0;
    }

    public int getId() {
        return id;
    }
}
