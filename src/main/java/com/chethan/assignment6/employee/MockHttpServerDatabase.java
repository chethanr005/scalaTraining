package com.chethan.assignment6.employee;

/**
 * Created by Chethan on Mar 14, 2022.
 */

public class MockHttpServerDatabase {

    public String getExpectedEmployee10() {
        return "{\"department\":\"cs\",\"dob\":\"1996-05-02\",\"gender\":\"female\",\"jobLevel\":\"junior\",\"joiningDate\":\"2020-07-12\",\"name\":\"swift\",\"salary\":500000.0,\"id\":10}";
    }

    public String getExpectedAllEmployees() {
        return "[{\"department\":\"cs\",\"dob\":\"2000-04-15\",\"gender\":\"female\",\"jobLevel\":\"junior\",\"joiningDate\":\"2020-10-15\",\"name\":\"marry\",\"salary\":260000.0,\"id\":1},{\"department\":\"ee\",\"dob\":\"1993-06-21\",\"gender\":\"female\",\"jobLevel\":\"mid\",\"joiningDate\":\"2015-12-25\",\"name\":\"rose\",\"salary\":250000.0,\"id\":2},{\"department\":\"ec\",\"dob\":\"1993-03-10\",\"gender\":\"female\",\"jobLevel\":\"mid\",\"joiningDate\":\"2014-07-24\",\"name\":\"julie\",\"salary\":350000.0,\"id\":3},{\"department\":\"cs\",\"dob\":\"1989-08-23\",\"gender\":\"male\",\"jobLevel\":\"mid\",\"joiningDate\":\"2010-04-04\",\"name\":\"tony\",\"salary\":250000.0,\"id\":4},{\"department\":\"cs\",\"dob\":\"1990-09-10\",\"gender\":\"female\",\"jobLevel\":\"mid\",\"joiningDate\":\"2012-12-28\",\"name\":\"kail\",\"salary\":450000.0,\"id\":5},{\"department\":\"ee\",\"dob\":\"1994-01-26\",\"gender\":\"female\",\"jobLevel\":\"junior\",\"joiningDate\":\"2015-10-01\",\"name\":\"kate\",\"salary\":450000.0,\"id\":6},{\"department\":\"ee\",\"dob\":\"2000-10-25\",\"gender\":\"male\",\"jobLevel\":\"junior\",\"joiningDate\":\"2021-12-01\",\"name\":\"joe\",\"salary\":250000.0,\"id\":7},{\"department\":\"ec\",\"dob\":\"1995-03-21\",\"gender\":\"male\",\"jobLevel\":\"junior\",\"joiningDate\":\"2019-05-12\",\"name\":\"anthony\",\"salary\":380000.0,\"id\":8},{\"department\":\"ec\",\"dob\":\"1997-07-19\",\"gender\":\"male\",\"jobLevel\":\"junior\",\"joiningDate\":\"2021-06-26\",\"name\":\"andrew\",\"salary\":300000.0,\"id\":9},{\"department\":\"cs\",\"dob\":\"1996-05-02\",\"gender\":\"female\",\"jobLevel\":\"junior\",\"joiningDate\":\"2020-07-12\",\"name\":\"swift\",\"salary\":500000.0,\"id\":10},{\"department\":\"ee\",\"dob\":\"1996-05-05\",\"gender\":\"male\",\"jobLevel\":\"junior\",\"joiningDate\":\"2020-10-18\",\"name\":\"cooper\",\"salary\":470000.0,\"id\":11},{\"department\":\"cs\",\"dob\":\"2000-04-15\",\"gender\":\"female\",\"jobLevel\":\"junior\",\"joiningDate\":\"2021-10-15\",\"name\":\"mary\",\"salary\":260000.0,\"id\":12},{\"department\":\"cs\",\"dob\":\"1994-02-21\",\"gender\":\"female\",\"jobLevel\":\"mid\",\"joiningDate\":\"2016-11-09\",\"name\":\"hailey\",\"salary\":420000.0,\"id\":13}]";
    }

    public String getExpectedNoOfEmpByDeptContainer() {
        return "{\"dept\":\"cs\",\"employees\":6}";

    }

    public String getExpectedGroupEmployeesByDepartment() {
        return "[{\"dept\":\"cs\",\"employees\":[{\"department\":\"cs\",\"dob\":\"2000-04-15\",\"gender\":\"female\",\"jobLevel\":\"junior\",\"joiningDate\":\"2020-10-15\",\"name\":\"marry\",\"salary\":260000.0,\"id\":1},{\"department\":\"cs\",\"dob\":\"1989-08-23\",\"gender\":\"male\",\"jobLevel\":\"mid\",\"joiningDate\":\"2010-04-04\",\"name\":\"tony\",\"salary\":250000.0,\"id\":4},{\"department\":\"cs\",\"dob\":\"1990-09-10\",\"gender\":\"female\",\"jobLevel\":\"mid\",\"joiningDate\":\"2012-12-28\",\"name\":\"kail\",\"salary\":450000.0,\"id\":5},{\"department\":\"cs\",\"dob\":\"1996-05-02\",\"gender\":\"female\",\"jobLevel\":\"junior\",\"joiningDate\":\"2020-07-12\",\"name\":\"swift\",\"salary\":500000.0,\"id\":10},{\"department\":\"cs\",\"dob\":\"2000-04-15\",\"gender\":\"female\",\"jobLevel\":\"junior\",\"joiningDate\":\"2021-10-15\",\"name\":\"mary\",\"salary\":260000.0,\"id\":12},{\"department\":\"cs\",\"dob\":\"1994-02-21\",\"gender\":\"female\",\"jobLevel\":\"mid\",\"joiningDate\":\"2016-11-09\",\"name\":\"hailey\",\"salary\":420000.0,\"id\":13}]},{\"dept\":\"ee\",\"employees\":[{\"department\":\"ee\",\"dob\":\"1993-06-21\",\"gender\":\"female\",\"jobLevel\":\"mid\",\"joiningDate\":\"2015-12-25\",\"name\":\"rose\",\"salary\":250000.0,\"id\":2},{\"department\":\"ee\",\"dob\":\"1994-01-26\",\"gender\":\"female\",\"jobLevel\":\"junior\",\"joiningDate\":\"2015-10-01\",\"name\":\"kate\",\"salary\":450000.0,\"id\":6},{\"department\":\"ee\",\"dob\":\"2000-10-25\",\"gender\":\"male\",\"jobLevel\":\"junior\",\"joiningDate\":\"2021-12-01\",\"name\":\"joe\",\"salary\":250000.0,\"id\":7},{\"department\":\"ee\",\"dob\":\"1996-05-05\",\"gender\":\"male\",\"jobLevel\":\"junior\",\"joiningDate\":\"2020-10-18\",\"name\":\"cooper\",\"salary\":470000.0,\"id\":11}]},{\"dept\":\"ec\",\"employees\":[{\"department\":\"ec\",\"dob\":\"1993-03-10\",\"gender\":\"female\",\"jobLevel\":\"mid\",\"joiningDate\":\"2014-07-24\",\"name\":\"julie\",\"salary\":350000.0,\"id\":3},{\"department\":\"ec\",\"dob\":\"1995-03-21\",\"gender\":\"male\",\"jobLevel\":\"junior\",\"joiningDate\":\"2019-05-12\",\"name\":\"anthony\",\"salary\":380000.0,\"id\":8},{\"department\":\"ec\",\"dob\":\"1997-07-19\",\"gender\":\"male\",\"jobLevel\":\"junior\",\"joiningDate\":\"2021-06-26\",\"name\":\"andrew\",\"salary\":300000.0,\"id\":9}]}]";
    }

    public String getExpectedIncreasedSalaryEmployees() {
        return "[{\"department\":\"ec\",\"dob\":\"1993-03-10\",\"gender\":\"female\",\"jobLevel\":\"mid\",\"joiningDate\":\"2014-07-24\",\"name\":\"julie\",\"salary\":350000.0,\"id\":3},{\"department\":\"ec\",\"dob\":\"1997-07-19\",\"gender\":\"male\",\"jobLevel\":\"junior\",\"joiningDate\":\"2021-06-26\",\"name\":\"andrew\",\"salary\":300000.0,\"id\":9},{\"department\":\"ec\",\"dob\":\"1995-03-21\",\"gender\":\"male\",\"jobLevel\":\"junior\",\"joiningDate\":\"2019-05-12\",\"name\":\"anthony\",\"salary\":380000.0,\"id\":8}]";
    }

    public String getExpectedPromotedEmployees() {
        return "[{\"department\":\"cs\",\"dob\":\"1989-08-23\",\"gender\":\"male\",\"jobLevel\":\"senior\",\"joiningDate\":\"2010-04-04\",\"name\":\"tony\",\"salary\":250000.0,\"id\":4},{\"department\":\"cs\",\"dob\":\"1990-09-10\",\"gender\":\"female\",\"jobLevel\":\"senior\",\"joiningDate\":\"2012-12-28\",\"name\":\"kail\",\"salary\":450000.0,\"id\":5}]";
    }

    public String updateEmployee() {
        return "{\"department\":\"ee\",\"dob\":\"2000-10-25\",\"gender\":\"male\",\"jobLevel\":\"junior\",\"joiningDate\":\"2021-12-01\",\"name\":\"me\",\"salary\":250000.0,\"id\":7}";
    }

    public String addEmployee() {
        return "{\"department\":\"ee\",\"dob\":\"2000-10-25\",\"gender\":\"male\",\"jobLevel\":\"junior\",\"joiningDate\":\"2021-12-01\",\"name\":\"me\",\"salary\":250000.0,\"id\":15}";
    }

    public String getFilteredEmployee(){
        return "[{\"department\":\"ee\",\"dob\":\"1994-01-26\",\"gender\":\"female\",\"jobLevel\":\"junior\",\"joiningDate\":\"2015-10-01\",\"name\":\"kate\",\"salary\":450000.0,\"id\":6}]";
    }
}
