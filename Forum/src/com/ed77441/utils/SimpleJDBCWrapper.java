package com.ed77441.utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SimpleJDBCWrapper implements AutoCloseable {
	
	String driver, dbURL, dbUsername, dbPassword;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet resultSet = null;
	
	public SimpleJDBCWrapper(String driver, String dbURL, String dbUsername, String dbPassword) {
		this.driver = driver;
		this.dbURL = dbURL;
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
	}
	
	public ResultSet read(String query, Object [] parameters) throws Exception {
		prepareForConnection();
		setQueryStringAndParameter(query, parameters);
		return executeQuery();
	}
	
	public int update (String query, Object [] parameters) throws Exception {
		prepareForConnection();
		setQueryStringAndParameter(query, parameters);
		return executeUpdate();
	}
	
	public ResultSet getGeneratedKeys() throws Exception {
		return pstmt.getGeneratedKeys();
	}
	
	private void prepareForConnection() throws Exception {
		Class.forName(driver);
		connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
	}
	
	private void setQueryStringAndParameter(String query, Object [] parameters) throws Exception {
		pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		
		
		if (parameters != null) {
			int n = 1;
			for (Object parameter: parameters) {
				if (parameter instanceof String) {
					
					pstmt.setString(n, (String) parameter);
				}
				else if (parameter instanceof Integer) {
					pstmt.setInt(n, (Integer) parameter);
				}
				else if (parameter instanceof Double) {
					pstmt.setDouble(n, (Double) parameter);
	
				}
				else if (parameter instanceof Float) {
					pstmt.setFloat(n, (Float) parameter);
				}
				else if (parameter instanceof Long) {
					pstmt.setLong(n, (Long) parameter);
				}
				n++;
			}
		}		
	}
	
	private int executeUpdate() throws Exception {
		return pstmt.executeUpdate();
	}
	
	private ResultSet executeQuery() throws Exception {
		return pstmt.executeQuery();
	}
	
	@Override
	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
