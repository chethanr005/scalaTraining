package com.kishor.assignment2.student;

/**
 * Created by Kishor on Feb 02, 2022.
 */

public class TotalAndAverageContainer {
    String name;
    int    total;
    float  average;

    TotalAndAverageContainer(String name, int total, float average) {
        this.total = total;
        this.name = name;
        this.average = average;
    }

    @Override
    public String toString() {
        return "{" +
                "name = '" + name + '\'' +
                ", total = " + total +
                ", average = " + average +
                '}';
    }
}
