package com.company.salaries.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.salaries.dto.SalaryDto;
import com.company.salaries.irepository.ISalaryRepository;
import com.company.salaries.model.Salary;

@Service(value = "SalaryService")
public class SalaryService {

    ISalaryRepository _SalaryRepository;
    ModelMapper _ModelMapper;

    @Autowired
    public SalaryService(ISalaryRepository salaryRepository, ModelMapper modelMapper) {
        this._SalaryRepository = salaryRepository;
        this._ModelMapper = modelMapper;
    }

    public List<SalaryDto> GetAllSalaries() {
        List<Salary> salaries = _SalaryRepository.GetAllSalaries();
        List<SalaryDto> salaryDtos = new ArrayList<>();
        for (Salary salary : salaries) {
            salaryDtos.add(_ModelMapper.map(salary, SalaryDto.class));
        }
        return salaryDtos;
    }

    public SalaryDto GetSalaryByEmployeeId(int employeeId) {
        Salary salary = _SalaryRepository.GetSalaryByEmployeeId(employeeId);
        if (salary == null) {
            return null;
        }
        return _ModelMapper.map(salary, SalaryDto.class);
    }

    public SalaryDto AddSalary(SalaryDto salaryDto) {
        Salary salary = _ModelMapper.map(salaryDto, Salary.class);
        _SalaryRepository.AddSalary(salary);
        return salaryDto;
    }

    public SalaryDto UpdateSalary(int employeeId, SalaryDto salaryDto) {
        Salary salary = _ModelMapper.map(salaryDto, Salary.class);
        _SalaryRepository.UpdateSalary(employeeId, salary);
        return salaryDto;
    }

    public void DeleteSalaryByEmployeeId(int employeeId) {
        _SalaryRepository.DeleteSalaryByEmployeeId(employeeId);
    }
}
