package com.MysqlJDBC;

import java.util.Date;
import java.util.Enumeration;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Wrapper;

public class MySQL_JDBCDemo {
	public static void main(String[] args) {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL.false";
		String username = "root";
		String password = "Ranjit@11";
		Connection con;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loadded");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("cannot find the driver in the classpath!", e);
		}
		listDrivers();
		try {
			System.out.println("connection to database:" + jdbcURL);
			con = (Connection) DriverManager.getConnection(jdbcURL, username, password);
			System.out.println("connection is successfull" + con);

			int id;
			String name;
			Double salaey;
			Date start_date;
			Statement stmt;
			int rs1;
			ResultSet rs;

			PreparedStatement preparedStmt;
			stmt = (Statement) con.createStatement();
			rs = stmt.executeQuery("select * from employee_payroll");
			String query = "update employee_payroll set salary=? where name=?";
			preparedStmt = (PreparedStatement) con.prepareStatement(query);
			preparedStmt.setFloat(1, (float) 51000.0);
			preparedStmt.setString(2, "Rakesh");

			rs1 = preparedStmt.executeUpdate();

			rs1 = stmt.executeUpdate("update employee_payroll set salary=50000 where name = 'Anirban'");
			System.out.println("data updated " + rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void listDrivers() {
		Enumeration<Driver> driverList = DriverManager.getDrivers();
		while (driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println(" " + driverClass.getClass().getName());
		}
	}
}
