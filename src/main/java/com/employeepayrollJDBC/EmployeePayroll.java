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

    public List<EmployeePayrollData> readtable() {
        String sql_query = "Select * from employee_payroll;";
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
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

    public long updatetable(double salary, int id) {
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

    public List<String> operationsOnTable() {
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

    public void insert_Values_Into_Tables(int payroll_id, String name, String date, double salary, String gender) throws SQLException {
        Connection connection = this.getConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into employee_payroll(name, start, salary, gender) values (?,?,?,?);");
            preparedStatement.setNString(1, name);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setDouble(3, salary);
            preparedStatement.setNString(4, gender);
            int resultSet = preparedStatement.executeUpdate();

            PreparedStatement preparedStatement1 = connection.prepareStatement("insert into payroll_details(payroll_id, basePay, deductions, taxablePay, tax, netPay) values(?,?,?,?,?,?);");
            preparedStatement1.setInt(1, payroll_id);
            preparedStatement1.setDouble(2, salary/20);
            preparedStatement1.setDouble(3, salary/10);
            preparedStatement1.setDouble(4, salary/8);
            preparedStatement1.setDouble(5, salary/60);
            preparedStatement1.setDouble(6, salary/30);
            int resultSet1 = preparedStatement1.executeUpdate();
            connection.commit();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            connection.rollback();
        }
        finally{
            connection.close();
        }
    }

    public int insert_into_payroll_details(int payroll_id, double basePay, double deductions, double taxablePay, double tax, double netPay) {
        try{
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into payroll_details(payroll_id, basePay, deductions, taxablePay, tax, netPay) values(?,?,?,?,?,?);");

            preparedStatement.setInt(1, payroll_id);
            preparedStatement.setDouble(2, basePay);
            preparedStatement.setDouble(3, deductions);
            preparedStatement.setDouble(4,taxablePay);
            preparedStatement.setDouble(5, tax);
            preparedStatement.setDouble(6, netPay);
            int resultSet = preparedStatement.executeUpdate();
            return resultSet;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public void delete_record_from_employee_payroll(String name) throws SQLException {
        Connection connection = this.getConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from employee_payroll where name = ?;");
            preparedStatement.setString(1, name);
            int resultSet = preparedStatement.executeUpdate();
            connection.commit();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            connection.rollback();
        }
    }
}