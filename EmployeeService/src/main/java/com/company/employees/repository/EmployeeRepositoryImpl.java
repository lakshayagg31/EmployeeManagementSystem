package com.company.employees.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.company.employees.irepository.IEmployeeRepository;
import com.company.employees.model.Employee;

@Repository(value = "EmployeeRepositoryImpl")
public class EmployeeRepositoryImpl implements IEmployeeRepository {
    JdbcTemplate _JdbcTemplate;

    @Autowired
    public EmployeeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this._JdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Employee> GetAllEmployees() {
        List<Employee> employees;
        try {
            employees = _JdbcTemplate.query("select * from employee", new EmployeeRowMapper());
        } catch (Exception e) {
            System.out.println("Error fetching employees: " + e.getMessage());
            employees = new ArrayList<>();
        }
        return employees;
    }

    @Override
    public Employee GetEmployeeById(int employeeId) {
        try {
            return _JdbcTemplate.queryForObject("select * from employee where employee_id = ?", new EmployeeRowMapper(), employeeId);
        } catch (Exception e) {
            System.out.println("Error fetching employee by ID: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Employee GetEmployeeByEmail(String email) {
        try {
            email = email.toLowerCase();
            return _JdbcTemplate.queryForObject("select * from employee where email = ?", new EmployeeRowMapper(), email);
        } catch (Exception e) {
            System.out.println("Error fetching employee by email: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Employee AddEmployee(Employee employee) {
        try {
            String sql = "INSERT INTO employee (name, email, job_title, department_id) VALUES (?, ?, ?, ?)";
            _JdbcTemplate.update(
                sql,
                employee.getName().toLowerCase(),  // Assuming case-insensitive.
                employee.getEmail().toLowerCase(),
                employee.getJobTitle(),
                employee.getDepartmentId()
            );
            // After insert, fetch saved employee by email (unique)
            String selectSql = "SELECT * FROM employee WHERE email = ?";
            return _JdbcTemplate.queryForObject(selectSql, new EmployeeRowMapper(), employee.getEmail().toLowerCase());
        } catch (Exception e) {
            System.out.println("Error adding employee: " + e.getMessage());
            throw e;  // Propagate for logging and visibility
        }
    }


    @Override
    public Integer GetEmployeeCount() {
        try {
            String sql = "SELECT COUNT(*) FROM employee";
            return _JdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            System.out.println("Error fetching employee count: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public void DeleteEmployeeByEmail(String email) {
        try {
            email = email.toLowerCase();
            _JdbcTemplate.update("DELETE FROM employee WHERE email = ?", email);
        } catch (Exception e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
    }
}
