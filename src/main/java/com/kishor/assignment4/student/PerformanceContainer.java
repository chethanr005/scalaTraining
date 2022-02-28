package com.kishor.assignment4.student;

import java.util.List;

/**
 * Created by Kishor on Feb 25, 2022.
 */

public class PerformanceContainer {
    public String        level;
    public List<Student> students;

    PerformanceContainer(String level, List<Student> students) {
        this.level = level;
        this.students = students;
    }

    public String getlevel() {
        return level;
    }

    public List<Student> getstudents() {
        return students;
    }


    public boolean equals(Object obj) {
        PerformanceContainer check = (PerformanceContainer) obj;
        return this.students.equals(check.students) && this.level.equals(check.level);
    }

    @Override
    public String toString() {
        return "{" +
                "Level=" + level +
                ", students=" + students +
                '}';
    }
}
