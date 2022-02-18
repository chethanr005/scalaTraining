package com.rakesh.assignment3.employee;

import java.util.List;
import java.util.Map;

public class GroupEmployeeContainer {
    public Map<String, List<String>> groupedEmployee;

    public GroupEmployeeContainer(Map<String, List<String>> groupedEmployee) {
        this.groupedEmployee = groupedEmployee;
    }
}
