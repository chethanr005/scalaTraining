package com.rakesh.assignment4.student;

/**
 * Created by Rakesh on Feb 24, 2022.
 */

public class MaleAndFemaleContainer {
    public long maleCount;
    public long femaleCount;

    public MaleAndFemaleContainer(long maleCount, long femaleCount) {
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
    }

    @Override
    public boolean equals(Object object) {
        MaleAndFemaleContainer check = (MaleAndFemaleContainer) object;
        return this.maleCount == check.maleCount && this.femaleCount == check.femaleCount;
    }

}
