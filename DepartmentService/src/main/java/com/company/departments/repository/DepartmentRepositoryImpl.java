package com.company.departments.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.company.departments.exception.DatabaseException;
import com.company.departments.irepository.IDepartmentRepository;
import com.company.departments.model.Department;

@Repository(value = "DepartmentRepositoryImpl")
public class DepartmentRepositoryImpl implements IDepartmentRepository {

    private final JdbcTemplate _JdbcTemplate;

    @Autowired
    public DepartmentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this._JdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Department> GetAllDepartments() {
        try {
            return _JdbcTemplate.query("select * from department", new DepartmentRowMapper());
        } catch (Exception e) {
            throw new DatabaseException("Error fetching departments: " + e.getMessage());
        }
    }

    @Override
    public Department GetDepartmentById(int departmentId) {
        try {
            String query = "select * from department where department_id = ?";
            return _JdbcTemplate.queryForObject(query, new DepartmentRowMapper(), departmentId);
        } catch (Exception e) {
            throw new DatabaseException("Error fetching department by ID: " + e.getMessage());
        }
    }

    @Override
    public String GetDepartmentNameById(int departmentId) {
        try {
            return _JdbcTemplate.queryForObject("select department_name from department where department_id = ?",
                    String.class, departmentId);
        } catch (Exception e) {
            throw new DatabaseException("Error fetching department name by ID: " + e.getMessage());
        }
    }

    @Override
    public Integer GetDepartmentIdByName(String departmentName) {
        try {
            departmentName = departmentName.toLowerCase();
            return _JdbcTemplate.queryForObject("select department_id from department where department_name = ?",
                    Integer.class, departmentName);
        } catch (Exception e) {
            throw new DatabaseException("Error fetching department ID by name: " + e.getMessage());
        }
    }

    @Override
    public Department GetDepartmentByName(String departmentName) {
        try {
            departmentName = departmentName.toLowerCase();
            String query = "select * from department where department_name = ?";
            return _JdbcTemplate.queryForObject(query, new DepartmentRowMapper(), departmentName);
        } catch (Exception e) {
            throw new DatabaseException("Error fetching department by name: " + e.getMessage());
        }
    }

    @Override
    public Department AddDepartment(Department department) {
        try {
            String sql = "INSERT INTO department (department_name, head_id) VALUES (?, ?)";
            _JdbcTemplate.update(sql, department.getDepartmentName(), department.getHeadId());
            String selectSql = "SELECT * FROM department WHERE department_name = ?";
            return _JdbcTemplate.queryForObject(selectSql, new DepartmentRowMapper(), department.getDepartmentName());
        } catch (Exception e) {
            throw new DatabaseException("Error adding department: " + e.getMessage());
        }
    }

    @Override
    public Integer GetDepartmentCount() {
        try {
            String sql = "SELECT COUNT(*) FROM department";
            return _JdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            throw new DatabaseException("Error fetching department count: " + e.getMessage());
        }
    }

    @Override
    public List<Department> GetDepartmentsPaginated(int page, int size) {
        try {
            int offset = page * size;
            String sql = "SELECT * FROM department LIMIT ? OFFSET ?";
            return _JdbcTemplate.query(sql, new DepartmentRowMapper(), size, offset);
        } catch (Exception e) {
            throw new DatabaseException("Error fetching paginated departments: " + e.getMessage());
        }
    }

    @Override
    public List<Department> GetDepartmentsRange(int start, int end) {
        try {
            int count = end - start + 1;
            String sql = "SELECT * FROM department LIMIT ? OFFSET ?";
            return _JdbcTemplate.query(sql, new DepartmentRowMapper(), count, start);
        } catch (Exception e) {
            throw new DatabaseException("Error fetching department range: " + e.getMessage());
        }
    }
}
