package com.rakesh.assignment1.employee;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeePromotion {
    public Map<String,String > PromoteEmployee(List<Employee> employeeList)
    {
        Map<String,String> result=new HashMap<>();
       List<Employee> filteredemp=employeeList.stream().filter(s->getyears(s.getJoiningDate())>8).collect(Collectors.toList());
        for (Employee employee : filteredemp) {
            updatepost(employee);
            result.put(employee.getName(),employee.getJobLevel());
        }
        return result;
    }

    private int getyears(LocalDate joingdate)
    {
        Period p=Period.between(joingdate,LocalDate.now());
        return p.getYears();
    }

    private void updatepost(Employee emp)
    {
        emp.setJobLevel("Senior");
    }
}
