package com.rakesh.assignment2.student;

/**
 * Created by Rakesh on Feb 02, 2022.
 */

public class TotalAndAverageContainer {
    long   total;
    double average;

    public TotalAndAverageContainer(long total, double average) {
        this.total = total;
        this.average = average;
    }

    @Override
    public String toString() {
        return "{ total:" + total +
                ", average:" + average;
    }
}
