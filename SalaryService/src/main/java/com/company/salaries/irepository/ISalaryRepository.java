package com.company.salaries.irepository;

import java.util.List;

import com.company.salaries.model.Salary;

public interface ISalaryRepository {
    List<Salary> GetAllSalaries();
    Salary GetSalaryByEmployeeId(int employeeId);
    Salary AddSalary(Salary salary);
    Salary UpdateSalary(int employeeId, Salary salary);
    void DeleteSalaryByEmployeeId(int employeeId);
}
