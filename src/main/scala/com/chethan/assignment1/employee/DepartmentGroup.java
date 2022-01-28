package com.chethan.assignment1.employee;

import java.util.*;
import java.util.stream.Collectors;


//Group employees by department
public class DepartmentGroup {

    Map<String, List<String>> employeesInDept(List<Employee> l) {

        List<String> l1 = l.stream().map(i -> i.getDepartment()).distinct().collect(Collectors.toList());
        Map<String, List<String>> m1 = new TreeMap<String, List<String>>();
        l1.stream().forEach(i ->{m1.put(i ,l.stream().filter(k-> i==k.getDepartment()).map(j -> j.getName()).collect(Collectors.toList()));});
        return m1;
    }

}
