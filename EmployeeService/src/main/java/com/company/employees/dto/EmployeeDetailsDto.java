package com.company.employees.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;

public class EmployeeDetailsDto {
    @JsonProperty("employeeid")
    int _EmployeeId;

    @JsonProperty("name")
    String _Name;

    @JsonProperty("email")
    @Email(message = "Please provide a valid email address")
    String _Email;

    @JsonProperty("jobtitle")
    String _JobTitle;

    @JsonProperty("departmentid")
    int _DepartmentId;

    @JsonProperty("departmentname")
    String _DepartmentName;

    @JsonProperty("basesalary")
    Double _BaseSalary;

    public EmployeeDetailsDto() {}

    public EmployeeDetailsDto(int employeeId, String name, String email, String jobTitle, int departmentId, String departmentName, Double baseSalary) {
        _EmployeeId = employeeId;
        _Name = name;
        _Email = email;
        _JobTitle = jobTitle;
        _DepartmentId = departmentId;
        _DepartmentName = departmentName;
        _BaseSalary = baseSalary;
    }

    public int get_EmployeeId() { return _EmployeeId; }
    public void set_EmployeeId(int employeeId) { _EmployeeId = employeeId; }
    public String get_Name() { return _Name; }
    public void set_Name(String name) { _Name = name; }
    public String get_Email() { return _Email; }
    public void set_Email(String email) { _Email = email; }
    public String get_JobTitle() { return _JobTitle; }
    public void set_JobTitle(String jobTitle) { _JobTitle = jobTitle; }
    public int get_DepartmentId() { return _DepartmentId; }
    public void set_DepartmentId(int departmentId) { _DepartmentId = departmentId; }
    public String get_DepartmentName() { return _DepartmentName; }
    public void set_DepartmentName(String departmentName) { _DepartmentName = departmentName; }
    public Double get_BaseSalary() { return _BaseSalary; }
    public void set_BaseSalary(Double baseSalary) { _BaseSalary = baseSalary; }
}
