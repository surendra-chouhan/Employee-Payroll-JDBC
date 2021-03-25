package com.employeepayrollJDBC;

import java.sql.*;
import java.util.ArrayList;
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

    public List<EmployeePayrollData> readData() {
        String sql_query = "Select * from employee_payroll;";
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                Date start = resultSet.getDate(3);
                double salary = resultSet.getDouble(4);
                String gender = resultSet.getString(5);
                EmployeePayrollData employeePayroll = new EmployeePayrollData(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getDate(3), resultSet.getDouble(4), resultSet.getString(5));
                employeePayrollList.add(employeePayroll);
            }
            System.out.println(employeePayrollList.toString());
            connection.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayrollList;
    }

    public long updateData(double salary, int id) {
        String query = "Update employee_payroll set salary= ? where id = ?;";
        try {
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, salary);
            preparedStatement.setInt(2, id);
            long result = preparedStatement.executeUpdate();
            return result;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public List<EmployeePayrollData> retrieve_employee_starting_between_daterange(String date) {
        String sql_query = "Select * from employee_payroll where start >= ?";
        List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query);
            preparedStatement.setDate(1, java.sql.Date.valueOf(date));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                Date start = resultSet.getDate(3);
                double salary = resultSet.getDouble(4);
                String gender = resultSet.getString(5);

                EmployeePayrollData employeePayroll = new EmployeePayrollData(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getDate(3), resultSet.getDouble(4), resultSet.getString(5));
                employeePayrollDataList.add(employeePayroll);
            }
            System.out.println(employeePayrollDataList.toString());
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayrollDataList;
    }

    public List<String> dataManipulation() {
        List<String> list = new ArrayList<>();
        String query = "select gender,sum(salary), avg(salary), min(salary), max(salary), count(salary) from employee_payroll GROUP BY gender;";
        try {
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                int index = 1;
                System.out.println("Gender : " + result.getString(1));
                System.out.println("Salary : " + result.getString(2));
                for(int i = 0; i < 13; i++){
                    if(index < 7){
                        list.add(i, result.getString(index));
                        index++;
                    }
                }
                System.out.println(list);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
}