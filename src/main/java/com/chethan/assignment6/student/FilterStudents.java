package com.chethan.assignment6.student;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Chethan on Mar 22, 2022.
 */

public class FilterStudents {
    public String  performance = null;
    public String  gender      = null;
    public Integer gradeLevel  = null;
    public String  activities  = null;

    @JsonCreator
    public FilterStudents(@JsonProperty("per") String performance, @JsonProperty("gen") String gender, @JsonProperty("grade") Integer gradeLevel, @JsonProperty("act") String activities) {
        this.performance = performance;
        this.gender = gender;
        this.gradeLevel = gradeLevel;
        this.activities = activities;
    }
}
