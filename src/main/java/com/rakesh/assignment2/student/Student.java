package com.rakesh.assignment2.student;

import java.util.List;
import java.util.Optional;

/**
 * Created by Rakesh on Feb 02, 2022.
 */

public class Student {
    String                     name;
    String                     registrationCode;
    int                        standard;
    List<MarkDetailsContainer> markDetails;

    public String getName() {
        return name;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public int getStandard() {
        return standard;
    }

    public List<MarkDetailsContainer> getMarkDetails() {
        return markDetails;
    }

    public Student(String name, String registrationCode, int standard, List<MarkDetailsContainer> markDetails) {
        this.name = name;
        this.registrationCode = registrationCode;
        this.standard = standard;
        this.markDetails = markDetails;
    }

    public static long getTotal(Student std) {
        Optional<Integer> total = std.getMarkDetails().stream().map(MarkDetailsContainer::getMarks).reduce(Integer::sum);
        return total.get();
    }

    @Override
    public String toString() {
        return "name:'" + name + '\'' +
                ", registrationCode:'" + registrationCode + '\'' +
                ", standard:" + standard +
                ", markDetails:" + markDetails;
    }
}
