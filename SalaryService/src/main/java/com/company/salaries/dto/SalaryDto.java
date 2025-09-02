package com.company.salaries.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SalaryDto {
    @JsonProperty("salaryid")
    int _SalaryId;

    @JsonProperty("employeeid")
    int _EmployeeId;

    @JsonProperty("basesalary")
    BigDecimal _BaseSalary;

    public SalaryDto() {}

    public SalaryDto(int salaryId, int employeeId, BigDecimal baseSalary) {
        this._SalaryId = salaryId;
        this._EmployeeId = employeeId;
        this._BaseSalary = baseSalary;
    }

    // getters and setters
    public int get_SalaryId() {
        return _SalaryId;
    }

    public void set_SalaryId(int salaryId) {
        this._SalaryId = salaryId;
    }

    public int get_EmployeeId() {
        return _EmployeeId;
    }

    public void set_EmployeeId(int employeeId) {
        this._EmployeeId = employeeId;
    }

    public BigDecimal get_BaseSalary() {
        return _BaseSalary;
    }

    public void set_BaseSalary(BigDecimal baseSalary) {
        this._BaseSalary = baseSalary;
    }
}
