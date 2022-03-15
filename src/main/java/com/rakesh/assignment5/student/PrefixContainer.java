package com.rakesh.assignment5.student;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Rakesh on Mar 10, 2022.
 */

public class PrefixContainer {
    List<String> nameList;

    @JsonCreator
    public PrefixContainer(@JsonProperty("nameList") List<String> nameList) {
        this.nameList = nameList;
    }

    @Override
    public boolean equals(Object obj) {
        PrefixContainer check = (PrefixContainer) obj;
        return this.nameList.equals(check.nameList);
    }

    @Override
    public String toString() {
        return "nameList : " + nameList;
    }
}
