package com.kishor.assignment1.employee;

import java.util.List;
import java.util.Map;

/**
 * 3. group employees by department
 */
public class GroupByDepartmentContainer {
    public Map<String, List<String>> grpByDept;

    GroupByDepartmentContainer(Map<String, List<String>> grpByDept) {
        this.grpByDept = grpByDept;
    }

}
