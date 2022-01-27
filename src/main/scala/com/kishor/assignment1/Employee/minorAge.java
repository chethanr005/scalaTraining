package com.kishor.assignment1.Employee;

import java.time.LocalDate;
import java.time.Period;

import static java.lang.System.exit;

public class minorAge {
    public static void add(String name, String department, double salary, String gender, LocalDate joiningDate, LocalDate dob, String jobLevel) {
        LocalDate today = LocalDate.now();
        LocalDate bday = LocalDate.of(dob.getYear(),dob.getMonth(),dob.getDayOfMonth());
        Period p = Period.between(bday,today);
        if(p.getYears()>21)
        {

        }
        else{
            System.out.println("Age is "+p.getYears()+" and less than 21. And Cannot be added to Database");
            exit(0);
        }
    }

    public static void main(String[] args) {
        add("John","IT Development",35000.0,"male", LocalDate.of(2021,8,11),LocalDate.of(1998,5,12),"Junior");
    }
}
