package com.company.departments.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.company.departments.model.Department;

public class DepartmentRowMapper implements RowMapper<Department> {
    @Override
    public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
        if(rs == null) {
            return null;
        }
        int departmentId = rs.getInt("department_id");
        String departmentName = rs.getString("name");
        int headId = rs.getInt("head_id");
        return new Department(departmentId, departmentName, headId);
    }
}
