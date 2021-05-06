package com.uniken.corporate.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.uniken.corporate.bean.Corporate;

public class CorporateDao {
	
	private String jdbcURL = "jdbc:mysql://localhost:3306/corporatemanagement?SSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "root";
	private String jdbcDriver = "com.mysql.jdbc.Driver"; 
	
	private static final String INSERT_USERS_SQL = "INSERT INTO corporate" + "  (name, accountNumber) VALUES "
			+ " (?, ?, ?);";

	private static final String SELECT_USER_BY_ID = "select id,name,accountNumber from corporate where id =?";
	private static final String SELECT_ALL_USERS = "select * from corporate";
	private static final String DELETE_USERS_SQL = "delete from corporate where id = ?;";
	private static final String UPDATE_USERS_SQL = "update corporate set name = ?,accountNumber= ? where id = ?;";
	
	public CorporateDao() {
	
	}
	
	protected Connection getConnection() {
		Connection connection = null;
		
		try {
			Class.forName(jdbcDriver);
			connection = DriverManager.getConnection(jdbcURL , jdbcUsername, jdbcPassword);
			
		}  catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}  catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return connection;
	}
	
	//insert user
	public void insertCorporate(Corporate corporate) throws SQLException {
		System.out.println(INSERT_USERS_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
			preparedStatement.setString(1, corporate.getName());
			preparedStatement.setString(2, corporate.getAccountNumber());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}
	
	//select user By id
	public Corporate selectCorporate(int id) {
		Corporate corporate = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String name = rs.getString("name");
				String accountNumber = rs.getString("accountNumber");
				
				corporate = new Corporate(id, name, accountNumber);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return corporate;
	}
	
	
	//select all users
	public List<Corporate> selectAllCorporates() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Corporate> corporate = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String accountNumber = rs.getString("accountNumber");
				
				corporate.add(new Corporate(id, name, accountNumber ));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return corporate;
	}
	
	
	//update user
	public boolean updateCorporate(Corporate corporate) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
			System.out.println("updated Corporate:"+statement);
			statement.setString(1, corporate.getName());
			statement.setString(2, corporate.getAccountNumber());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
	
	
	//delete user
	public boolean deleteCorporate(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	
	//SQL Exaception method
	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}
