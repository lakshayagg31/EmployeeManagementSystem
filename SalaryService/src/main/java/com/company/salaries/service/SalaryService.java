package com.company.salaries.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.company.salaries.dto.SalaryDto;
import com.company.salaries.irepository.ISalaryRepository;
import com.company.salaries.model.Salary;

@Service(value = "SalaryService")
public class SalaryService {
    @Value("${com.company.pagination.size:10}")
    private int defaultPageSize;


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
    
    public Map<String, Object> GetSalariesPaginated(int page, int size) {
        int totalElements = _SalaryRepository.GetSalaryCount();
        int pageSize = (size > 0) ? size : defaultPageSize;
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        List<Salary> salaries = _SalaryRepository.GetSalariesPaginated(page, pageSize);
        List<SalaryDto> dtos = new ArrayList<>();
        for (Salary sal : salaries) {
            dtos.add(_ModelMapper.map(sal, SalaryDto.class));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("size", pageSize);
        response.put("totalPages", totalPages);
        response.put("totalElements", totalElements);
        response.put("salaries", dtos);
        return response;
    }

    public Map<String, Object> GetSalariesRange(int start, int end) {
        int totalElements = _SalaryRepository.GetSalaryCount();
        List<Salary> salaries = _SalaryRepository.GetSalariesRange(start, end);

        List<SalaryDto> dtos = new ArrayList<>();
        for (Salary sal : salaries) {
            dtos.add(_ModelMapper.map(sal, SalaryDto.class));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("start", start);
        response.put("end", end);
        response.put("totalElements", totalElements);
        response.put("salaries", dtos);
        return response;
    }

}
