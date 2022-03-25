package com.chethan.assignment6.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;

/**
 * Created by Chethan on Mar 21, 2022.
 */

public class FilterEmployees {
    public String    department = null;
    public Double    salary     = null;
    public String    gender     = null;
    public String    jobLevel   = null;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate startDate  = null;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate endDate    = null;


    @JsonCreator
    public FilterEmployees(@JsonProperty("dep") String department,
                           @JsonProperty("sal") Double salary,
                           @JsonProperty("gen") String gender,
                           @JsonProperty("job") String jobLevel,
                           @JsonProperty("start") LocalDate startDate,
                           @JsonProperty("end") LocalDate endDate) {
        this.department = department;
        this.salary = salary;
        this.gender = gender;
        this.jobLevel = jobLevel;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "department= " + department + ", salary= " + salary + ", gender= " + gender + ", jobLevel= " + jobLevel + ", startDate= " + startDate + ", endDate= " + endDate;
    }
}
