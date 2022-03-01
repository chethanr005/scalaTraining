package com.kishor.assignment1.employee;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncreaseSalaryContainer that = (IncreaseSalaryContainer) o;
        return Objects.equals(name, that.name) && Objects.equals(increaseSal, that.increaseSal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, increaseSal);
    }

    @Override
    public String toString() {
        return '{' + name + " = " + increaseSal +
                '}';
    }
}
