package com.company.employees.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.company.employees.exception.DatabaseException;
import com.company.employees.irepository.IEmployeeRepository;
import com.company.employees.model.Employee;

@Repository(value = "EmployeeRepositoryImpl")
public class EmployeeRepositoryImpl implements IEmployeeRepository {

    private final JdbcTemplate _JdbcTemplate;

    @Autowired
    public EmployeeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this._JdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Employee> GetAllEmployees() {
        try {
            return _JdbcTemplate.query("select * from employee", new EmployeeRowMapper());
        } catch (Exception e) {
            throw new DatabaseException("Error fetching employees: " + e.getMessage());
        }
    }

    @Override
    public Employee GetEmployeeById(int employeeId) {
        try {
            return _JdbcTemplate.queryForObject("select * from employee where employee_id = ?", new EmployeeRowMapper(), employeeId);
        } catch (Exception e) {
            throw new DatabaseException("Error fetching employee by ID: " + e.getMessage());
        }
    }

    @Override
    public Employee GetEmployeeByEmail(String email) {
        try {
            email = email.toLowerCase();
            return _JdbcTemplate.queryForObject("select * from employee where email = ?", new EmployeeRowMapper(), email);
        } catch (Exception e) {
            throw new DatabaseException("Error fetching employee by email: " + e.getMessage());
        }
    }

    @Override
    public Employee AddEmployee(Employee employee) {
        try {
            String sql = "INSERT INTO employee (name, email, job_title, department_id) VALUES (?, ?, ?, ?)";
            _JdbcTemplate.update(
                sql,
                employee.getName().toLowerCase(),
                employee.getEmail().toLowerCase(),
                employee.getJobTitle(),
                employee.getDepartmentId()
            );
            String selectSql = "SELECT * FROM employee WHERE email = ?";
            return _JdbcTemplate.queryForObject(selectSql, new EmployeeRowMapper(), employee.getEmail().toLowerCase());
        } catch (Exception e) {
            throw new DatabaseException("Error adding employee: " + e.getMessage());
        }
    }

    @Override
    public Integer GetEmployeeCount() {
        try {
            String sql = "SELECT COUNT(*) FROM employee";
            return _JdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            throw new DatabaseException("Error fetching employee count: " + e.getMessage());
        }
    }

    @Override
    public void DeleteEmployeeByEmail(String email) {
        try {
            email = email.toLowerCase();
            _JdbcTemplate.update("DELETE FROM employee WHERE email = ?", email);
        } catch (Exception e) {
            throw new DatabaseException("Error deleting employee: " + e.getMessage());
        }
    }

    @Override
    public List<Employee> GetEmployeesPaginated(int page, int size) {
        try {
            int offset = page * size;
            String sql = "SELECT * FROM employee LIMIT ? OFFSET ?";
            return _JdbcTemplate.query(sql, new EmployeeRowMapper(), size, offset);
        } catch (Exception e) {
            throw new DatabaseException("Error fetching paginated employees: " + e.getMessage());
        }
    }

    @Override
    public List<Employee> GetEmployeesRange(int start, int end) {
        try {
            int count = end - start + 1;
            String sql = "SELECT * FROM employee LIMIT ? OFFSET ?";
            return _JdbcTemplate.query(sql, new EmployeeRowMapper(), count, start);
        } catch (Exception e) {
            throw new DatabaseException("Error fetching employee range: " + e.getMessage());
        }
    }
}
