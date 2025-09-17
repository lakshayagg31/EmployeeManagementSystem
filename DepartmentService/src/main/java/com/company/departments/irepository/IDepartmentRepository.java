package com.company.departments.irepository;

import java.util.List;

import com.company.departments.model.Department;

public interface IDepartmentRepository {
    List<Department> GetAllDepartments();
    Department GetDepartmentById(int departmentId);
    Department GetDepartmentByName(String departmentName);
    Department AddDepartment(Department department);
    Integer GetDepartmentCount();
    String GetDepartmentNameById(int departmentId);
    Integer GetDepartmentIdByName(String departmentName);
}