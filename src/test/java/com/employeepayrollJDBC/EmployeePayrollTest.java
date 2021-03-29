package com.employeepayrollJDBC;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class EmployeePayrollTest {

    EmployeePayroll employeePayroll;

    @Before
    public void set() {
        employeePayroll = new EmployeePayroll();
    }

    @Test
    public void givenSelectStatement_usingPreparedStatement_shouldReturnTrue() {
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readtable();
        Assert.assertEquals(3, employeePayrollDataList.size());
    }

    @Test
    public void givenUpdateStatement_usingPreparedStatement_shouldReturnTrue() {
        long result = employeePayroll.updatetable(350000, 3);
        Assert.assertEquals(1, result);
    }

    @Test
    public void givenStatement_shouldReturn_employeeDetails_between_givenDateRange() {
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.retrieve_employee_starting_between_daterange("2019-01-01");
        Assert.assertEquals(2, employeePayrollDataList.size());
    }

    @Test
    public void get_sum_from_employeePayroll_table() {
        List<String> list = employeePayroll.operationsOnTable();
        Assert.assertEquals(12, list.size());
    }

    @Test
    public void insert_newEmployee_in_employeePayroll_table() throws SQLException {
        employeePayroll.insert_Values_Into_Tables(4,"Peter", "2020-03-07", 500000, "M");
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readtable();
        Assert.assertEquals(4, employeePayrollDataList.size());
    }


    @Test
    public void insert_into_payroll_details() {
        int payroll_id =1;
        double basePay = 6000;
        double deductions = 20000;
        double taxablePay = 60000;
        double tax = 80000;
        double netPay = 3000;
        int result = employeePayroll.insert_into_payroll_details(payroll_id, basePay, deductions, taxablePay, tax, netPay);
        Assert.assertEquals(1, result);
    }

    @Test
    public void add_new_employee_in_employee_details() throws SQLException {
        employeePayroll.insert_Values_Into_Tables( 5,"Karen", "2019-08-09", 800000, "F");
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readtable();
        Assert.assertEquals(5, employeePayrollDataList.size());
    }

    @Test
    public void delete_employee_from_employee_table() throws SQLException {
        employeePayroll.delete_record_from_employee_payroll("Karen");
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readtable();
        Assert.assertEquals(4, employeePayrollDataList.size());

    }
}
