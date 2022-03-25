package com.kishor.assignment5.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Created by Kishor on Mar 11, 2022.
 */

public class IncreaseSalaryContainer {
    public String name;
    public Double increaseSal;

    @JsonCreator
    IncreaseSalaryContainer(@JsonProperty String name, @JsonProperty Double increaseSal) {
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
