package com.bootcamp.hr.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bootcamp.hr.entity.Employee;
import com.bootcamp.hr.jdbc.DatabaseConnectionManager;

public class EmployeeDAO {
	public static final String TABLE_NAME = "employees";

	private static final String SELECT_ALL_EMPLOYEES = String.format(
			"SELECT employee_id, first_name, last_name, email, salary, job_id, department_id, department_name FROM %s NATURAL JOIN %s",
			TABLE_NAME, DepartmentDao.TABLE_NAME);
	private static final String SELECT_EMPLOYEE_BY_ID = SELECT_ALL_EMPLOYEES + " WHERE employee_id = ?";
	private static final String SELECT_EMPLOYEES_BY_SALARY = SELECT_ALL_EMPLOYEES + " WHERE salary BETWEEN ? AND ?";
	private static final String UPDATE_EMPLOYEE_SALARY = String.format("UPDATE %s SET salary = ? WHERE employee_id = ?",
			TABLE_NAME);

	private static final int EMPLOYEE_ID_COLUMN = 1;
	private static final int FIRST_NAME_COLUMN = 2;
	private static final int LAST_NAME_COLUMN = 3;
	private static final int EMAIL_COLUMN = 4;
	private static final int SALARY_COLUMN = 5;
	private static final int JOB_ID_COLUMN = 6;
	private static final int DEPARTMENT_ID_COLUMN = 7;
	private static final int DEPARTMENT_NAME_COLUMN = 8;

	private final DatabaseConnectionManager databaseConnectionManager;
	private PreparedStatement statement;
	private ResultSet resultSet;

	public EmployeeDAO(DatabaseConnectionManager databaseConnectionManager) {
		this.databaseConnectionManager = databaseConnectionManager;
	}

	public List<Employee> findAll() {
		List<Employee> employees = new ArrayList<>();
		try {
			statement = databaseConnectionManager.getConnection().prepareStatement(SELECT_ALL_EMPLOYEES);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Employee employee = toEmployee(resultSet);
				employees.add(employee);
			}
		} catch (SQLException e) {
			this.logSQLException(e);

		} finally {
			this.close();

		}
		return employees;
	}

	public Employee findById(int id) {
		Employee employee = null;
		try {
			statement = databaseConnectionManager.getConnection().prepareStatement(SELECT_EMPLOYEE_BY_ID);
			statement.setInt(1, id);

			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				employee = toEmployee(resultSet);
			}
		} catch (SQLException e) {
			this.logSQLException(e);

		} finally {
			this.close();

		}

		return employee;
	}

	public List<Employee> findBySalary(float minimumSalary, float maximumSalary) {
		List<Employee> employees = new ArrayList<>();
		try {
			statement = databaseConnectionManager.getConnection().prepareStatement(SELECT_EMPLOYEES_BY_SALARY);
			statement.setFloat(1, minimumSalary);
			statement.setFloat(2, maximumSalary);

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Employee employee = toEmployee(resultSet);
				employees.add(employee);
			}
		} catch (SQLException e) {
			this.logSQLException(e);

		} finally {
			this.close();

		}
		return employees;
	}

	public void updateSalary(int id, float newSalary) {
		try {
			statement = databaseConnectionManager.getConnection().prepareStatement(UPDATE_EMPLOYEE_SALARY);
			statement.setFloat(1, newSalary);
			statement.setInt(2, id);

			int numberRowsAffected = statement.executeUpdate();
			if (numberRowsAffected > 1) {
				throw new RuntimeException("Number of rows affected is greater than 1.");
			}
		} catch (SQLException e) {
			this.logSQLException(e);

		} finally {
			this.close();

		}
	}

	private void close() {
		try {
			if (statement != null) {
				statement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
			databaseConnectionManager.closeConnection();
		} catch (SQLException e) {
			logSQLException(e);
		}
	}

	private void logSQLException(SQLException e) {
		System.out.println(String.format("sql_error=%s, error_code=%s", e.getMessage(), e.getErrorCode()));
	}

	private Employee toEmployee(ResultSet row) throws SQLException {
		Employee employee = new Employee();
		employee.setId(row.getInt(EMPLOYEE_ID_COLUMN));
		employee.setFirstName(row.getString(FIRST_NAME_COLUMN));
		employee.setLastName(row.getString(LAST_NAME_COLUMN));
		employee.setEmail(row.getString(EMAIL_COLUMN));
		employee.setSalary(row.getFloat(SALARY_COLUMN));
		employee.setJobId(row.getString(JOB_ID_COLUMN));
		employee.setDepartmentId(row.getInt(DEPARTMENT_ID_COLUMN));
		employee.setDepartmentName(row.getString(DEPARTMENT_NAME_COLUMN));
		return employee;
	}
}
