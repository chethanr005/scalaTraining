package com.kishor.assignment1.student;

/**
 * 1. get no of male and female students , Return result in class MaleAndFemaleContainer(int males, int females)
 * 2. Add Prefix to each student's name ,  Mr. or Ms. and return
 */
public class MaleAndFemaleContainer {
    private long males;
    private long females;

    MaleAndFemaleContainer(long males, long females) {
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
    public String toString() {
        return "{" +
                "Males = " + males +
                ", Females = " + females +
                '}';
    }
}