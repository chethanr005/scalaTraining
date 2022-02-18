package com.kishor.assignment1.employee;

/**
 * 4. Increase salary of employees for given Department , not necessary that there will be hike !
 */

public class IncreaseSalaryContainer {
    String name;
    Double increaseSal;

    IncreaseSalaryContainer(String name, Double increaseSal) {
        this.name = name;
        this.increaseSal = increaseSal;
    }

    @Override
    public String toString() {
        return '{' + name + " = " + increaseSal +
                '}';
    }
}
