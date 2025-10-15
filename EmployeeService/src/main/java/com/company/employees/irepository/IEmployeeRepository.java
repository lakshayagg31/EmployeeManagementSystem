package com.company.employees.irepository;

import java.util.List;

import com.company.employees.model.Employee;

public interface IEmployeeRepository {
    List<Employee> GetAllEmployees();
    Employee GetEmployeeById(int employeeId);
    Employee GetEmployeeByEmail(String email);
    Employee AddEmployee(Employee employee);
    Integer GetEmployeeCount();
    void DeleteEmployeeByEmail(String email);
    List<Employee> GetEmployeesPaginated(int page, int size);
    List<Employee> GetEmployeesRange(int start, int end);

}
