package com.chethan.assignment1.employee;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;


//Promote employees based on experience
public class PromoteEmployees {

    void seniorEmp(List<Employee> l) {
         l.stream().filter(f -> Period.between(f.getJoiningDate(), LocalDate.now()).getYears()>=8).forEach(i -> i.setJobLevel("senior"));
    }
}
