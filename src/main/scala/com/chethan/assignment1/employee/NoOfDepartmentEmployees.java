package com.chethan.assignment1.employee;

import com.chethan.assignment1.student.Student;
import java.util.*;
import java.util.stream.Collectors;


public class NoOfDepartmentEmployees {

    Map<String, Long> employeesInDept(List<Employee> l) {

        List<String> l1 = l.stream().map(i -> i.getDepartment()).distinct().collect(Collectors.toList());
        Map<String, Long> m1 = new TreeMap<String, Long>();
        l1.stream().forEach(i ->{m1.put(i ,l.stream().filter(k-> i==k.getDepartment()).count());});
        return m1;
    }
}
