package com.chethan.assignment1.student;

//No of male and female student container
public class MaleAndFemaleContainer {

    private long males;
    private long females;

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
}
