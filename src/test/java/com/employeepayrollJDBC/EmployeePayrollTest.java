package com.employeepayrollJDBC;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class EmployeePayrollTest {

    @Test
    public void givenSelectStatement_shouldReturnTrue() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readData();
        Assert.assertEquals(3, employeePayrollDataList.size());
    }

    @Test
    public void givenUpdateStatementforEmployeePayrollTable_shouldReturnTrue() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        employeePayroll.updateData();
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readData();
        Assert.assertEquals(3, employeePayrollDataList.size());
    }

    @Test
    public void givenUpdateStatement_usingPreparedStatement_forEmployeePayrollTable_shouldReturnTrue() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        employeePayroll.updateData_using_PreparedStatement();
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readData();
        Assert.assertEquals(3, employeePayrollDataList.size());
    }
}
