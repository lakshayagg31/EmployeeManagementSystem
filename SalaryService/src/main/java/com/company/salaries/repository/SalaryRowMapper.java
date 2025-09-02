package com.company.salaries.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.company.salaries.model.Salary;

public class SalaryRowMapper implements RowMapper<Salary> {
    @Override
    public Salary mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (rs == null) {
            return null;
        }
        int salaryId = rs.getInt("salary_id");
        int employeeId = rs.getInt("employee_id");
        java.math.BigDecimal baseSalary = rs.getBigDecimal("base_salary");
        return new Salary(salaryId, employeeId, baseSalary);
    }
}
