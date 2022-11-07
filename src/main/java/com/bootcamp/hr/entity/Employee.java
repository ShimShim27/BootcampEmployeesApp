package com.bootcamp.hr.entity;

public class Employee {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private float salary;
	private String jobId;
	private int departmentId;
	private String departmentName;

	public Employee() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Override
	public int hashCode() {
		return this.id * 31;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Employee)) {
			return false;
		}
		Employee otherEmployee = (Employee) obj;
		return this.id == otherEmployee.getId() && this.email.equals(otherEmployee.getEmail());
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s,%s", this.id, this.firstName, this.lastName, this.email, this.salary);
	}
}
