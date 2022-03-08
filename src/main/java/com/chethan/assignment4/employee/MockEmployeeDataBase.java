package com.chethan.assignment4.employee;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Chethan on Feb 25, 2022.
 */

public class MockEmployeeDataBase {

    public CompletableFuture<NoOfEmpByDeptContainer> getActualNoOfEmpByDeptContainer() {
        return CompletableFuture.supplyAsync(() -> new NoOfEmpByDeptContainer("ee", 4));
    }

    public CompletableFuture<List<GroupEmployeesByDepartment>> getExpectedGroupEmpByDeptList() {
        return CompletableFuture.supplyAsync(() -> Arrays.asList(
                new GroupEmployeesByDepartment("cs", Arrays.asList(
                        new Employee(1, "marry", "cs", 260000.0, "female", "2020-10-15", "2000-04-15", "junior")
                        , new Employee(10, "swift", "cs", 500000.0, "female", "2020-07-12", "1996-05-02", "junior")
                        , new Employee(12, "mary", "cs", 260000.0, "female", "2021-10-15", "2000-04-15", "junior")
                        , new Employee(13, "hailey", "cs", 420000.0, "female", "2016-11-09", "1994-02-21", "mid")
                        , new Employee(4, "tony", "cs", 250000.0, "male", "2010-04-04", "1989-08-23", "mid")
                        , new Employee(5, "kail", "cs", 450000.0, "female", "2012-12-28", "1990-09-10", "mid")))
                ,
                new GroupEmployeesByDepartment("ee", Arrays.asList(
                        new Employee(2, "rose", "ee", 250000.0, "female", "2015-12-25", "1993-06-21", "mid")
                        , new Employee(6, "kate", "ee", 450000.0, "female", "2015-10-01", "1994-01-26", "junior")
                        , new Employee(7, "joe", "ee", 250000.0, "male", "2021-12-01", "2000-10-25", "junior")
                        , new Employee(11, "cooper", "ee", 470000.0, "male", "2020-10-18", "1996-05-05", "junior")))
                ,
                new GroupEmployeesByDepartment("ec", Arrays.asList(
                         new Employee(3, "julie", "ec", 350000.0, "female", "2014-07-24", "1993-03-10", "mid")
                        , new Employee(8, "anthony", "ec", 380000.0, "male", "2019-05-12", "1995-03-21", "junior")
                        ,new Employee(9, "andrew", "ec", 300000.0, "male", "2021-06-26", "1997-07-19", "junior")))));
    }

    public CompletableFuture<List<GroupEmployeesByDepartment>> getExpectedMockitoGroupEmpByDeptList() {
        return CompletableFuture.supplyAsync(() -> Arrays.asList(
                new GroupEmployeesByDepartment("cs", Arrays.asList(
                        new Employee(1, "marry", "cs", 260000.0, "female", "2020-10-15", "2000-04-15", "junior")
                        , new Employee(4, "tony", "cs", 250000.0, "male", "2010-04-04", "1989-08-23", "mid")
                        , new Employee(5, "kail", "cs", 450000.0, "female", "2012-12-28", "1990-09-10", "mid")
                        , new Employee(10, "swift", "cs", 500000.0, "female", "2020-07-12", "1996-05-02", "junior")
                        , new Employee(12, "mary", "cs", 260000.0, "female", "2021-10-15", "2000-04-15", "junior")
                        , new Employee(13, "hailey", "cs", 420000.0, "female", "2016-11-09", "1994-02-21", "mid")))
                ,
                new GroupEmployeesByDepartment("ee", Arrays.asList(
                        new Employee(2, "rose", "ee", 250000.0, "female", "2015-12-25", "1993-06-21", "mid")
                        , new Employee(6, "kate", "ee", 450000.0, "female", "2015-10-01", "1994-01-26", "junior")
                        , new Employee(7, "joe", "ee", 250000.0, "male", "2021-12-01", "2000-10-25", "junior")
                        , new Employee(11, "cooper", "ee", 470000.0, "male", "2020-10-18", "1996-05-05", "junior")))
                ,
                new GroupEmployeesByDepartment("ec", Arrays.asList(
                        new Employee(3, "julie", "ec", 350000.0, "female", "2014-07-24", "1993-03-10", "mid")
                        , new Employee(8, "anthony", "ec", 380000.0, "male", "2019-05-12", "1995-03-21", "junior")
                        ,new Employee(9, "andrew", "ec", 300000.0, "male", "2021-06-26", "1997-07-19", "junior")))));
    }

    public CompletableFuture<List<Employee>> getExpectedIncreasedSalaryEmployeesList(){
        return CompletableFuture.supplyAsync(()-> Arrays.asList(
                 new Employee(3, "julie" ,"ec", 402500.0, "female" ,"2014-07-24", "1993-03-10", "mid")
                ,new Employee(8, "anthony" ,"ec", 437000.0 ,"male", "2019-05-12", "1995-03-21", "junior")
                ,new Employee(9, "andrew"  ,"ec", 345000.0 ,"male", "2021-06-26", "1997-07-19", "junior")
        ));
    }
    public CompletableFuture<List<Employee>> getExpectedMockitoIncreasedSalaryEmployessList(){
        return CompletableFuture.supplyAsync(()-> Arrays.asList(
                new Employee(3, "julie" ,"ec", 402500.0, "female" ,"2014-07-24", "1993-03-10", "mid")
                ,new Employee(8, "anthony" ,"ec", 437000.0 ,"male", "2019-05-12", "1995-03-21", "junior")
                ,new Employee(9, "andrew"  ,"ec", 345000.0 ,"male", "2021-06-26", "1997-07-19", "junior")
        ));
    }


    public CompletableFuture<List<Employee>> getActualPromotedEmployee(){
        return CompletableFuture.supplyAsync(()-> Arrays.asList(
                new Employee( 4, "tony", "cs", 250000.0, "male"   ,"2010-04-04", "1989-08-23", "senior")
                ,new Employee(5, "kail", "cs", 450000.0, "female" ,"2012-12-28", "1990-09-10", "senior")
        ));

    }
}
