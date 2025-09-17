package com.company.employees.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;

public class EmployeeDto {
    @JsonProperty("employeeid")
    private Integer employeeId;  // Integer for nullable, assigned by DB

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    @Email(message = "Please provide a valid email address")
    private String email;

    @JsonProperty("jobtitle")
    private String jobTitle;

    @JsonProperty("departmentid")
    private Integer departmentId;

    public EmployeeDto() {}

    public EmployeeDto(Integer employeeId, String name, String email, String jobTitle, Integer departmentId) {
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
