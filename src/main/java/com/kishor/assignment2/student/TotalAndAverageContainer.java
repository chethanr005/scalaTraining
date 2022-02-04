package com.kishor.assignment2.student;

/**
 * Created by Kishor on Feb 02, 2022.
 */

public class TotalAndAverageContainer {
    int   total;
    float average;

    TotalAndAverageContainer(int total, float average) {
        this.total = total;
        this.average = average;
    }

    @Override
    public String toString() {
        return "  total=" + total +
                ", average=" + average
                ;
    }
}
