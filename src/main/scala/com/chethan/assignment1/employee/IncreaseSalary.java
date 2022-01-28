package com.chethan.assignment1.employee;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class IncreaseSalary {

    void salInc(List<Employee> l, String a, int exp, int hike){

        l.stream().filter( f-> f.getDepartment()==a).filter( f1-> Period.between(f1.getJoiningDate(), LocalDate.now()).getYears()>exp)
                .forEach( m-> m.setSalary(m.getSalary()+m.getSalary()*(hike/100)));
    }


}
