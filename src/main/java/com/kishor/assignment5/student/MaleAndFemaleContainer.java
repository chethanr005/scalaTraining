package com.kishor.assignment5.student;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Created by Kishor on Mar 11, 2022.
 */

public class MaleAndFemaleContainer {
    private long males;
    private long females;

    @JsonCreator
    MaleAndFemaleContainer(@JsonProperty long males, @JsonProperty long females) {
        this.males = males;
        this.females = females;
    }

    public long getmales() {
        return males;
    }

    public long getFemales() {
        return females;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaleAndFemaleContainer that = (MaleAndFemaleContainer) o;
        return males == that.males && females == that.females;
    }

    @Override
    public int hashCode() {
        return Objects.hash(males, females);
    }

    @Override
    public String toString() {
        return "{" +
                "Males = " + males +
                ", Females = " + females +
                '}';
    }
}
