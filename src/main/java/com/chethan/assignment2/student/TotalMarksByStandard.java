package com.chethan.assignment2.student;

/**
 * Created by Chethan on Feb 02, 2022.
 */

/**
 * 2. Total marks scored by each standard
 **/
public class TotalMarksByStandard {
    private final int standard;
    private final int total;

    TotalMarksByStandard(int standard, int total) {
        this.standard = standard;
        this.total = total;
    }

    @Override
    public String toString() {
        return "standard=" + standard + ", " + "total=" + total;
    }
}
