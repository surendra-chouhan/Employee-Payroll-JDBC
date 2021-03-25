package com.employeepayrollJDBC;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class EmployeePayrollTest {

    @Test
    public void givenSelectStatement_usingPreparedStatement_shouldReturnTrue() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readData();
        Assert.assertEquals(3, employeePayrollDataList.size());
    }

    @Test
    public void givenUpdateStatement_usingPreparedStatement_shouldReturnTrue() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        long result = employeePayroll.updateData();
        Assert.assertEquals(1, result);
    }

    @Test
    public void givenStatement_shouldReturn_employeeDetails_between_givenDateRange() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.retrieve_employee_starting_between_daterange();
        Assert.assertEquals(2, employeePayrollDataList.size());
    }
}
