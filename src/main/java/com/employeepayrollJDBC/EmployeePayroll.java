package com.employeepayrollJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeePayroll {
    private Connection getConnection() throws SQLException {
        String jdbcurl = "jdbc:mysql://localhost:3306/employeepayroll?useSSL=false";
        String username = "root";
        String password = "surendra123";
        Connection con;

        System.out.println("Connecting to database : " + jdbcurl);
        con = DriverManager.getConnection(jdbcurl, username, password);
        System.out.println("Connection Successful : " + con);
        return con;
    }

    public List<EmployeePayrollData> readData(){
        String sql_query = "Select * from employee_payroll;";
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try{
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql_query);
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                Date start = resultSet.getDate(3);
                double salary = resultSet.getDouble(4);
                String gender = resultSet.getString(5);
                System.out.println("\n");
                System.out.println("Id : " + id);
                System.out.println("Name : " + name);
                System.out.println("Start Date : " + start);
                System.out.println("Salary : " + salary );
                System.out.println("Gender : " + gender);

                EmployeePayrollData employeePayroll = new EmployeePayrollData(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getDate(3),resultSet.getDouble(4), resultSet.getString(5));
                employeePayrollList.add(employeePayroll);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayrollList;
    }

    public void updateData(){
        String query = "Update employee_payroll set salary='800000' where id = '3'";
        try{
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            long resultset = statement.executeLargeUpdate(query);
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}