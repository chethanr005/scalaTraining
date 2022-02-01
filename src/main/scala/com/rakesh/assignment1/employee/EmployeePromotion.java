package com.rakesh.assignment1.employee;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//5. promote employees having 8 years experience to Senior position

public class EmployeePromotion {
    public Map<String,String > promoteEmployee(List<Employee> employeeList)
    {
        Map<String,String> result=new HashMap<>();
       List<Employee> filteredEmp=employeeList.stream().filter(s->getYear(s.getJoiningDate())>8).collect(Collectors.toList());
        for (Employee employee : filteredEmp) {
            updatePosition(employee);
            result.put(employee.getName(),employee.getJobLevel());
        }
        return result;
    }

    private int getYear(LocalDate joiningDate)
    {
        Period p=Period.between(joiningDate,LocalDate.now());
        return p.getYears();
    }

    private void updatePosition(Employee emp)
    {
        emp.setJobLevel("Senior");
    }
}
