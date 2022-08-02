//package com.chethan.assignment1.employee;
//
//import java.time.LocalDate;
//import java.time.Period;
//import java.util.List;
//
//
////Promote employees based on experience
//public class PromoteEmployees {
//
//    void seniorEmp(List<Employee> l) {
//        l.stream().filter(f -> Period.between(f.getJoiningDate(), LocalDate.now()).getYears() >= 8).forEach(i -> i.setJobLevel("senior"));
//    }
//}
