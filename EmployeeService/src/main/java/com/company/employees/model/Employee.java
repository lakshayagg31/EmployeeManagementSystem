package com.company.employees.model;

public class Employee {
    private Integer employeeId;
    private String name;
    private String email;
    private String jobTitle;
    private Integer departmentId;

    public Employee() {}

    public Employee(Integer employeeId, String name, String email, String jobTitle, Integer departmentId) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.jobTitle = jobTitle;
        this.departmentId = departmentId;
    }

    // getters and setters
    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public Integer getDepartmentId() { return departmentId; }
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }
}
