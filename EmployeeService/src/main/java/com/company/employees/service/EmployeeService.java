package com.company.employees.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.employees.dto.EmployeeDto;
import com.company.employees.irepository.IEmployeeRepository;
import com.company.employees.model.Employee;

@Service(value = "EmployeeService")
public class EmployeeService {
    IEmployeeRepository _EmployeeRepository;
    ModelMapper _ModelMapper;

    @Autowired
    public EmployeeService(IEmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this._EmployeeRepository = employeeRepository;
        this._ModelMapper = modelMapper;
    }

    public List<EmployeeDto> GetAllEmployees() {
        List<Employee> employees = _EmployeeRepository.GetAllEmployees();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee emp : employees) {
            employeeDtos.add(_ModelMapper.map(emp, EmployeeDto.class));
        }
        return employeeDtos;
    }

    public EmployeeDto GetEmployeeById(int employeeId) {
        Employee employee = _EmployeeRepository.GetEmployeeById(employeeId);
        if (employee == null) {
            return null;
        }
        return _ModelMapper.map(employee, EmployeeDto.class);
    }

    public EmployeeDto GetEmployeeByEmail(String email) {
        Employee employee = _EmployeeRepository.GetEmployeeByEmail(email);
        if (employee == null) {
            return null;
        }
        return _ModelMapper.map(employee, EmployeeDto.class);
    }

    public EmployeeDto AddEmployee(EmployeeDto employeeDto) {
        Employee employee = _ModelMapper.map(employeeDto, Employee.class);
        _EmployeeRepository.AddEmployee(employee);
        return employeeDto;
    }

    public void DeleteEmployeeByEmail(String email) {
        _EmployeeRepository.DeleteEmployeeByEmail(email);
    }
}
