package com.kishor.assignment4.employee;

import java.util.List;
import java.util.Objects;

/**
 * Created by Kishor on Feb 28, 2022.
 */

public class GroupByDepartmentContainer {
    List<String> names;
    String       dept;

    public GroupByDepartmentContainer(List<String> names, String dept) {
        this.names = names;
        this.dept = dept;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupByDepartmentContainer that = (GroupByDepartmentContainer) o;
        return names.equals(that.names) && dept.equals(that.dept);
    }

    @Override
    public int hashCode() {
        return Objects.hash(names, dept);
    }

    @Override
    public String toString() {
        return dept + " " + names;
    }
}
