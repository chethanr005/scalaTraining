package com.kishor.assignment1.employee;

import java.util.List;

/**
 * 3. group employees by department
 */
public class GroupByDepartmentContainer {
    List<String> names;
    String       dept;

    public GroupByDepartmentContainer(List<String> names, String dept) {
        this.names = names;
        this.dept = dept;
    }

    @Override
    public String toString() {
        return names + " " + dept;
    }
}