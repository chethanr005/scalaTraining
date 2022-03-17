package com.kishor.assignment5.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

/**
 * Created by Kishor on Mar 11, 2022.
 */

public class GroupByDepartmentContainer {
    public List<String> names;
    public String       dept;

    @JsonCreator
    public GroupByDepartmentContainer(@JsonProperty List<String> names, @JsonProperty String dept) {
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
