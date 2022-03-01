package com.rakesh.assignment2.student;

/**
 * Created by Rakesh on Feb 03, 2022.
 */

public class TotalMarksByStandardContainer {
    int standard;
    int total;

    public TotalMarksByStandardContainer(int standard, int total) {
        this.standard = standard;
        this.total = total;
    }


    @Override
    public String toString() {
        return "{ standard:" + standard +
                " total:" + total + " }";
    }
}
