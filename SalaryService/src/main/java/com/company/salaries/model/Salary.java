package com.company.salaries.model;

import java.math.BigDecimal;

public class Salary {
    int _SalaryId;
    int _EmployeeId;
    BigDecimal _BaseSalary;

    public Salary() {}

    public Salary(int salaryId, int employeeId, BigDecimal baseSalary) {
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
