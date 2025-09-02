package com.company.salaries.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.salaries.dto.SalaryDto;
import com.company.salaries.service.SalaryService;

@RestController
@RequestMapping("/salaries")
public class SalaryController {

    SalaryService _SalaryService;

    @Autowired
    public SalaryController(SalaryService salaryService) {
        this._SalaryService = salaryService;
    }

    @GetMapping(path = "/")
    public List<SalaryDto> GetAllSalaries() {
        return _SalaryService.GetAllSalaries();
    }

    @GetMapping(path = "/salary/{employeeId}")
    public SalaryDto GetSalaryByEmployeeId(@PathVariable("employeeId") int employeeId) {
        return _SalaryService.GetSalaryByEmployeeId(employeeId);
    }

    @PostMapping("/salary")
    public SalaryDto AddSalary(@RequestBody SalaryDto salary) {
        return _SalaryService.AddSalary(salary);
    }

    @PutMapping("/salary/{employeeId}")
    public SalaryDto UpdateSalary(@PathVariable("employeeId") int employeeId, @RequestBody SalaryDto salary) {
        return _SalaryService.UpdateSalary(employeeId, salary);
    }

    @DeleteMapping("/salary/{employeeId}")
    public void DeleteSalary(@PathVariable("employeeId") int employeeId) {
        _SalaryService.DeleteSalaryByEmployeeId(employeeId);
    }
}
