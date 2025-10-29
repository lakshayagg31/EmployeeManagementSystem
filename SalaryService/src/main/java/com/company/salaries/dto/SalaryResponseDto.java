package com.company.salaries.dto;

public class SalaryResponseDto {

    private SalaryDto salary;  // Actual salary data on success
    private String status;     // Status message (Success or error description)

    public SalaryResponseDto() {}

    public SalaryResponseDto(SalaryDto salary, String status) {
        this.salary = salary;
        this.status = status;
    }

    public SalaryDto getSalary() {
        return salary;
    }

    public void setSalary(SalaryDto salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
