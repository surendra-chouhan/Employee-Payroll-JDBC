package com.employeepayrollJDBC;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class EmployeePayrollTest {

    EmployeePayroll employeePayroll;

    @Before
    public void set() {
        employeePayroll = new EmployeePayroll();
    }

    @Test
    public void givenSelectStatement_usingPreparedStatement_shouldReturnTrue() {
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readData();
        Assert.assertEquals(3, employeePayrollDataList.size());
    }

    @Test
    public void givenUpdateStatement_usingPreparedStatement_shouldReturnTrue() {
        long result = employeePayroll.updateData(350000, 3);
        Assert.assertEquals(1, result);
    }

    @Test
    public void givenStatement_shouldReturn_employeeDetails_between_givenDateRange() {
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.retrieve_employee_starting_between_daterange("2019-01-01");
        Assert.assertEquals(2, employeePayrollDataList.size());
    }

    @Test
    public void get_sum_from_employeePayroll_table() {
        List<String> list = employeePayroll.dataManipulation();
        Assert.assertEquals(12, list.size());
    }
}
