package com.employeepayrollJDBC;

import java.sql.*;
import java.util.Enumeration;

public class EmployeePayrollTest {
    public static void main(String[] args) {
        String jdbcurl = "jdbc:mysql://localhost:3306/employeepayroll?useSSL=false";
        String username = "root";
        String password = "surendra123";
        Connection con;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver Loaded");
        }
        catch(ClassNotFoundException e){
            throw new IllegalStateException("Class not found", e);
        }
        listdrivers();
        try{
            System.out.println("Connecting to database : " + jdbcurl);
            con = DriverManager.getConnection(jdbcurl, username, password);
            System.out.println("Connection Successful : " + con);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void listdrivers(){
        Enumeration<Driver> driverlist = DriverManager.getDrivers();
        while(driverlist.hasMoreElements()){
            Driver driverClass = (Driver) driverlist.nextElement();
            System.out.println(" " + driverClass.getClass().getName());
        }
    }
}
