package com.company.employees.dto;

public class EmployeeDetailsResponseDto {

    private EmployeeDetailsDto employeeDetails;
    private String status;

    public EmployeeDetailsResponseDto() {}

    public EmployeeDetailsResponseDto(EmployeeDetailsDto employeeDetails, String status) {
        this.employeeDetails = employeeDetails;
        this.status = status;
    }

    public EmployeeDetailsDto getEmployeeDetails() {
        return employeeDetails;
    }

    public void setEmployeeDetails(EmployeeDetailsDto employeeDetails) {
        this.employeeDetails = employeeDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
