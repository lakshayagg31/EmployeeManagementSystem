package com.company.employees.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.employees.dto.EmployeeDetailsDto;
import com.company.employees.dto.EmployeeDto;
import com.company.employees.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    EmployeeService _EmployeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this._EmployeeService = employeeService;
    }

    @GetMapping(path = "/")
    public List<EmployeeDto> GetAllEmployees() {
        return _EmployeeService.GetAllEmployees();
    }

    
    @GetMapping(path = "/employee/{employeeId}")
    public EmployeeDetailsDto GetEmployeeById(@PathVariable("employeeId") int employeeId) {
        return _EmployeeService.GetEmployeeDetailsById(employeeId);
    }

    @GetMapping(path = "/employee")
    public EmployeeDto GetEmployeeByEmail(@RequestParam("email") String email) {
        return _EmployeeService.GetEmployeeByEmail(email);
    }

    @PostMapping("/employee")
    public EmployeeDto AddEmployee(@RequestBody EmployeeDto employeeDto) {
        return _EmployeeService.AddEmployee(employeeDto);
    }


    @DeleteMapping("/employee/{email}")
    public void DeleteEmployeeByEmail(@PathVariable("email") String email) {
        _EmployeeService.DeleteEmployeeByEmail(email);
    }
}
