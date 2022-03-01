package com.kishor.assignment2.student;

/**
 * Created by Kishor on Feb 03, 2022.
 */

public class HighestMarksScoredContainer {
    int    standard;
    String name;

    public HighestMarksScoredContainer(String name, int standard) {
        this.standard = standard;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    @Override
    public String toString() {
        return "standard=" + standard +
                ", name='" + name + '\'';
    }
}
