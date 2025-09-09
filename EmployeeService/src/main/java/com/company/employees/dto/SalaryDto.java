package com.company.employees.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SalaryDto {
    @JsonProperty("salaryid")     
    private int salaryId;

    @JsonProperty("employeeid")   
    private int employeeId;

    @JsonProperty("basesalary")   
    private Double baseSalary;

    // Getters and Setters
    public int getSalaryId() {
        return salaryId;
    }
    public void setSalaryId(int salaryId) {
        this.salaryId = salaryId;
    }

    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }
    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }
}
