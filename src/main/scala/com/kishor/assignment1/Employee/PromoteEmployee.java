package com.kishor.assignment1.Employee;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

public class PromoteEmployee {
    public void promote (List<Employee> employees) {
        List<Employee> emp = employees.stream().filter(f -> (Period.between(f.getJoiningDate(), LocalDate.now()).getYears())>8).collect(Collectors.toList());
        for(emp)
    }
}
