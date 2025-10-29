package com.company.employees.dto;

public class EmployeeResponseDto {

    private EmployeeDto employee; // Data on success
    private String status;        // Status message

    public EmployeeResponseDto() {}

    public EmployeeResponseDto(EmployeeDto employee, String status) {
        this.employee = employee;
        this.status = status;
    }

    public EmployeeDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDto employee) {
        this.employee = employee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
