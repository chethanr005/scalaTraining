package com.kishor.assignment1.Employee;
/**
 * 4. Increase salary of employees for given Department , not necessary that there will be hike !
 */

import java.util.Optional;
public class IncreaseSalary {
    public Optional<String> hike(String s){
        boolean dept = EmployeeDatabase.getEmployee().stream().anyMatch(a-> a.getDepartment()==s);
        return null;
    }
}
