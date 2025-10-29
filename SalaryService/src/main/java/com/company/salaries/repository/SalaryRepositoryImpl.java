package com.company.salaries.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.company.salaries.exception.DatabaseException;
import com.company.salaries.irepository.ISalaryRepository;
import com.company.salaries.model.Salary;

@Repository(value = "SalaryRepositoryImpl")
public class SalaryRepositoryImpl implements ISalaryRepository {

    private final JdbcTemplate _JdbcTemplate;

    @Autowired
    public SalaryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this._JdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Salary> GetAllSalaries() {
        try {
            return _JdbcTemplate.query("select * from salary", new SalaryRowMapper());
        } catch (Exception e) {
            throw new DatabaseException("Error fetching salaries: " + e.getMessage());
        }
    }

    @Override
    public Salary GetSalaryByEmployeeId(int employeeId) {
        try {
            return _JdbcTemplate.queryForObject(
                "select * from salary where employee_id = ?",
                new SalaryRowMapper(), employeeId);
        } catch (Exception e) {
            throw new DatabaseException("Error fetching salary by employee ID: " + e.getMessage());
        }
    }

    @Override
    public Salary AddSalary(Salary salary) {
        try {
            String sql = "INSERT INTO salary (employee_id, base_salary) VALUES (?, ?)";
            _JdbcTemplate.update(sql, salary.get_EmployeeId(), salary.get_BaseSalary());
            return salary;
        } catch (Exception e) {
            throw new DatabaseException("Error adding salary: " + e.getMessage());
        }
    }

    @Override
    public Salary UpdateSalary(int employeeId, Salary salary) {
        try {
            String sql = "UPDATE salary SET base_salary = ? WHERE employee_id = ?";
            _JdbcTemplate.update(sql, salary.get_BaseSalary(), employeeId);
            return salary;
        } catch (Exception e) {
            throw new DatabaseException("Error updating salary: " + e.getMessage());
        }
    }

    @Override
    public void DeleteSalaryByEmployeeId(int employeeId) {
        try {
            String sql = "DELETE FROM salary WHERE employee_id = ?";
            _JdbcTemplate.update(sql, employeeId);
        } catch (Exception e) {
            throw new DatabaseException("Error deleting salary: " + e.getMessage());
        }
    }

    @Override
    public List<Salary> GetSalariesPaginated(int page, int size) {
        try {
            int offset = page * size;
            String sql = "SELECT * FROM salary LIMIT ? OFFSET ?";
            return _JdbcTemplate.query(sql, new SalaryRowMapper(), size, offset);
        } catch (Exception e) {
            throw new DatabaseException("Error fetching paginated salaries: " + e.getMessage());
        }
    }

    @Override
    public List<Salary> GetSalariesRange(int start, int end) {
        try {
            int count = end - start + 1;
            String sql = "SELECT * FROM salary LIMIT ? OFFSET ?";
            return _JdbcTemplate.query(sql, new SalaryRowMapper(), count, start);
        } catch (Exception e) {
            throw new DatabaseException("Error fetching salary range: " + e.getMessage());
        }
    }

    @Override
    public Integer GetSalaryCount() {
        try {
            String sql = "SELECT COUNT(*) FROM salary";
            return _JdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            throw new DatabaseException("Error fetching salary count: " + e.getMessage());
        }
    }
}
