package com.chethan.assignment1.employee;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class PromoteEmployees {

    void SeniorEmp(List<Employee> l) {
         l.stream().filter(f -> Period.between(f.getJoiningDate(), LocalDate.now()).getYears()>=8).forEach(i -> i.setJobLevel("senior"));
    }
}
