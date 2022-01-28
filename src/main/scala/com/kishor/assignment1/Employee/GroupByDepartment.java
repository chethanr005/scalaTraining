package com.kishor.assignment1.Employee;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class GroupByDepartment {
    public void depart(List<Employee> employees){
        Map<String, List<Employee>> grpByDept = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment));
        BiConsumer<String,List<Employee>> biConsumer = (k,v) -> System.out.println("\n"+ k +" ====> "+v +"\n");
        grpByDept.forEach(biConsumer);
    }
}
