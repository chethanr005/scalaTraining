package com.rakesh.assignment5.employee;

/**
 * Created by Rakesh on Mar 14, 2022.
 */

public class MockApiResult {
    public String getAllEmployee() {
        return "[{\"department\":\"IT Development\",\"dob\":\"1998-05-12\",\"empID\":1001,\"gender\":\"male\",\"jobLevel\":\"Junior\",\"joiningDate\":\"2021-08-11\",\"name\":\"John\",\"salary\":35000.0},{\"department\":\"IT Development\",\"dob\":\"1997-04-20\",\"empID\":1002,\"gender\":\"male\",\"jobLevel\":\"Junior\",\"joiningDate\":\"2020-08-11\",\"name\":\"Sunil\",\"salary\":35000.0}," +
                "{\"department\":\"Business Analysis\",\"dob\":\"1995-08-17\",\"empID\":1003,\"gender\":\"male\",\"jobLevel\":\"Mid-Term\",\"joiningDate\":\"2020-08-11\",\"name\":\"Rohit\",\"salary\":45000.0},{\"department\":\"Administration\",\"dob\":\"1994-02-21\",\"empID\":1004,\"gender\":\"male\",\"jobLevel\":\"Junior\",\"joiningDate\":\"2010-08-11\",\"name\":\"Edwin\",\"salary\":55000.0}]";
    }

    public String getByID() {
        return "{\"department\":\"IT Development\",\"dob\":\"1998-05-12\",\"empID\":1001,\"gender\":\"male\",\"jobLevel\":\"Junior\",\"joiningDate\":\"2021-08-11\",\"name\":\"John\",\"salary\":35000.0}";
    }

    public String employeeGroup() {
        return "[{\"department\":\"Administration\",\"employeeList\":[{\"department\":\"Administration\",\"dob\":\"1994-02-21\",\"empID\":1004,\"gender\":\"male\",\"jobLevel\":\"Junior\",\"joiningDate\":\"2010-08-11\",\"name\":\"Edwin\",\"salary\":55000.0}]}," +
                "{\"department\":\"Business Analysis\",\"employeeList\":[{\"department\":\"Business Analysis\",\"dob\":\"1995-08-17\",\"empID\":1003,\"gender\":\"male\",\"jobLevel\":\"Mid-Term\",\"joiningDate\":\"2020-08-11\",\"name\":\"Rohit\",\"salary\":45000.0}]}," +
                "{\"department\":\"IT Development\",\"employeeList\":[{\"department\":\"IT Development\",\"dob\":\"1998-05-12\",\"empID\":1001,\"gender\":\"male\",\"jobLevel\":\"Junior\",\"joiningDate\":\"2021-08-11\",\"name\":\"John\",\"salary\":35000.0},{\"department\":\"IT Development\",\"dob\":\"1997-04-20\",\"empID\":1002,\"gender\":\"male\",\"jobLevel\":\"Junior\",\"joiningDate\":\"2020-08-11\",\"name\":\"Sunil\",\"salary\":35000.0}]}]";
    }

    public String promotedEmployee() {
        return "[{\"department\":\"Administration\",\"dob\":\"1994-02-21\",\"empID\":1004,\"gender\":\"male\",\"jobLevel\":\"Senior\",\"joiningDate\":\"2010-08-11\",\"name\":\"Edwin\",\"salary\":55000.0}]";
    }

    public String hikedEmployee() {
        return "[{\"department\":\"Administration\",\"dob\":\"1994-02-21\",\"empID\":1004,\"gender\":\"male\",\"jobLevel\":\"Junior\",\"joiningDate\":\"2010-08-11\",\"name\":\"Edwin\",\"salary\":55000.0}]";
    }

}
