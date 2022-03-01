package com.rakesh.assignment4.student;

import java.util.List;

/**
 * Created by Rakesh on Feb 25, 2022.
 */

public class PrefixContainer {
    List<String> nameList;

    public PrefixContainer(List<String> nameList) {
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
