package com.chethan.assignment2.student;

/**
 * Created by Chethan on Feb 02, 2022.
 */

/**
 * 1. Get total and average marks of all students
 **/
public class TotalAverageContainer {
    private final int    total;
    private final double average;

    TotalAverageContainer(int total, double average) {
        this.total = total;
        this.average = average;
    }

    //Getters
    public int getTotal() {
        return total;
    }

    public double getAverage() {
        return average;
    }

    @Override
    public String toString() {
        return "total=" + total + ", " + "average=" + average;
    }
}
