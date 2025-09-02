package com.company.employees.model;

public class Employee {
    int _EmployeeId;
    String _Name;
    String _Email;
    String _JobTitle;
    int _DepartmentId;

    public Employee() {}

    public Employee(int employeeId, String name, String email, String jobTitle, int departmentId) {
        this._EmployeeId = employeeId;
        this._Name = name;
        this._Email = email;
        this._JobTitle = jobTitle;
        this._DepartmentId = departmentId;
    }

    // getters and setters
    public int get_EmployeeId() {
        return _EmployeeId;
    }

    public void set_EmployeeId(int employeeId) {
        this._EmployeeId = employeeId;
    }

    public String get_Name() {
        return _Name;
    }

    public void set_Name(String name) {
        this._Name = name;
    }

    public String get_Email() {
        return _Email;
    }

    public void set_Email(String email) {
        this._Email = email;
    }

    public String get_JobTitle() {
        return _JobTitle;
    }

    public void set_JobTitle(String jobTitle) {
        this._JobTitle = jobTitle;
    }

    public int get_DepartmentId() {
        return _DepartmentId;
    }

    public void set_DepartmentId(int departmentId) {
        this._DepartmentId = departmentId;
    }
}
