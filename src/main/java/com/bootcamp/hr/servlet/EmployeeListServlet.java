package com.bootcamp.hr.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bootcamp.hr.dao.EmployeeDAO;
import com.bootcamp.hr.entity.Employee;
import com.bootcamp.hr.jdbc.DatabaseConnectionManager;

public class EmployeeListServlet extends HttpServlet {
	private static final long serialVersionUID = -51595649370746076L;
	private DatabaseConnectionManager connectionManager;
	private EmployeeDAO employeeDAO;

	@Override
	public void init() throws ServletException {
		connectionManager = new DatabaseConnectionManager();
		employeeDAO = new EmployeeDAO(connectionManager);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		response.setContentType("text/html");

		List<Employee> employees = employeeDAO.findAll();

		out.print("<html>");
		out.print("<head>");
		out.print("<title>Employee List</title>");
		out.print("</head>");
		out.print("<body>");
		out.print("<h1>Employee List</h1>");
		out.print("<hr/>");
		out.print("<table>");
		out.print("<thead>");

		out.print(generateTableHtmlRowString("ID", "First Name", "Last Name", "Email", "Salary",
				"Department ID", "Department Name", "Job ID"));

		out.print("</thead>");
		out.print("<tbody>");
		for (Employee employee : employees) {
			String rowData = generateTableHtmlRowString(employee.getId(), employee.getFirstName(),
					employee.getLastName(), employee.getEmail(), employee.getSalary(),
					employee.getDepartmentId(), employee.getDepartmentName(), employee.getJobId());
			out.print(rowData);

		}
		out.print("</tbody>");
		out.print("</table>");
		out.print("</body>");
		out.print("</html>");
		out.flush();
		out.close();
	}

	private String generateTableHtmlRowString(Object... data) {
		StringBuilder sb = new StringBuilder();
		sb.append("<tr>");
		for (Object d : data) {
			sb.append(String.format("<td>%s</td>", d));
		}
		sb.append("</tr>");
		return sb.toString();
	}
}
