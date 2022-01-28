package com.kishor.assignment1.Employee;

import java.util.Optional;
public class IncreaseSalary {
    public Optional<String> hike(String s){
        boolean dept = EmployeeDatabase.getEmployee().stream().anyMatch(a-> a.getDepartment()==s);

    }
}
