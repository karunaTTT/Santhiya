package com.exterro.jdbc_assignment;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Db_Connection {

	private static String url = "jdbc:sqlserver://ERDD020;databaseName=carPooling";
	private static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String username = "sa";
	private static String password = "exterro-123";
	private static Connection con;

	public Connection getConnection() {
		try {
			Class.forName(driverName);
			try {
				con = DriverManager.getConnection(url, username, password);
			} catch (SQLException ex) {
				// log an exception. fro example:
				System.out.println("Failed to create the database connection.");
			}
		} catch (ClassNotFoundException ex) {
			// log an exception. for example:
			System.out.println("Driver not found.");
		}
		return con;
	}


}
