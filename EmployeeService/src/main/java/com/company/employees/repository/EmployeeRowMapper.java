package com.company.employees.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.company.employees.model.Employee;

public class EmployeeRowMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (rs == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.set_EmployeeId(rs.getInt("employee_id"));
        employee.set_Name(rs.getString("name"));
        employee.set_Email(rs.getString("email"));
        employee.set_JobTitle(rs.getString("job_title"));
        employee.set_DepartmentId(rs.getInt("department_id"));
        return employee;
    }
}
