package com.chethan.assignment5.student;

/**
 * Created by Chethan on Feb 24, 2022.
 */

public class MaleAndFemaleContainer {
    private final long males;
    private final long females;


    MaleAndFemaleContainer(long males, long females) {
        this.males = males;
        this.females = females;
    }

    //Getters
    public long getMales() {
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

}
