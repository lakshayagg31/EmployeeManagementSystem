package com.company.departments.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.company.departments.irepository.IDepartmentRepository;
import com.company.departments.model.Department;

@Repository(value = "DepartmentRepositoryImpl")
public class DepartmentRepositoryImpl implements IDepartmentRepository {
    JdbcTemplate _JdbcTemplate;

    @Autowired
    public DepartmentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this._JdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Department> GetAllDepartments() {
        List<Department> departments;
        try {
            departments = _JdbcTemplate.query("select * from department", new DepartmentRowMapper());
        } catch (Exception e) {
            System.out.println("Error fetching departments: " + e.getMessage());
            departments = new ArrayList<>();
        }
        return departments;
    }

    @Override
    public Department GetDepartmentById(int departmentId) {
        try {
            String query = "select * from department where department_id = ?";
            return _JdbcTemplate.queryForObject(query, new DepartmentRowMapper(), departmentId);
        } catch (Exception e) {
            System.out.println("Error fetching department by ID: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String GetDepartmentNameById(int departmentId) {
        try {
            return _JdbcTemplate.queryForObject("select department_name from department where department_id = ?",
                    String.class, departmentId);
        } catch (Exception e) {
            System.out.println("Error fetching department department_name by ID: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Integer GetDepartmentIdByName(String departmentName) {
        try {
            departmentName = departmentName.toLowerCase();
            return _JdbcTemplate.queryForObject("select department_id from department where department_name = ?",
                    Integer.class, departmentName);
        } catch (Exception e) {
            System.out.println("Error fetching department ID by department_name: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Department GetDepartmentByName(String departmentName) {
        try {
            departmentName = departmentName.toLowerCase();
            String query = "select * from department where department_name = ?";
            return _JdbcTemplate.queryForObject(query, new DepartmentRowMapper(), departmentName);
        } catch (Exception e) {
            System.out.println("Error fetching department by department_name: " + e.getMessage());
            return null;
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
            System.out.println("Error adding department: " + e.getMessage());
            throw e; // Propagate exception for logging!
        }
    }



    @Override
    public Integer GetDepartmentCount() {
        try {
            String sql = "SELECT COUNT(*) FROM department";
            return _JdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            System.out.println("Error fetching department count: " + e.getMessage());
            return 0;
        }
    }
}