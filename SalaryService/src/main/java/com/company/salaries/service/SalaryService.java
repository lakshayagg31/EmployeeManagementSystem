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
import com.company.salaries.exception.DatabaseException;
import com.company.salaries.exception.SalaryNotFoundException;
import com.company.salaries.exception.ValidationException;
import com.company.salaries.irepository.ISalaryRepository;
import com.company.salaries.model.Salary;

@Service(value = "SalaryService")
public class SalaryService {

    @Value("${com.company.pagination.size:10}")
    private int defaultPageSize;

    private final ISalaryRepository _SalaryRepository;
    private final ModelMapper _ModelMapper;

    @Autowired
    public SalaryService(ISalaryRepository salaryRepository, ModelMapper modelMapper) {
        this._SalaryRepository = salaryRepository;
        this._ModelMapper = modelMapper;
    }

    public List<SalaryDto> GetAllSalaries() {
        try {
            List<Salary> salaries = _SalaryRepository.GetAllSalaries();
            List<SalaryDto> dtos = new ArrayList<>();
            for (Salary s : salaries) {
                dtos.add(_ModelMapper.map(s, SalaryDto.class));
            }
            return dtos;
        } catch (Exception ex) {
            throw new DatabaseException("Failed to fetch all salaries: " + ex.getMessage());
        }
    }

    public SalaryDto GetSalaryByEmployeeId(int employeeId) {
        try {
            Salary salary = _SalaryRepository.GetSalaryByEmployeeId(employeeId);
            if (salary == null) {
                throw new SalaryNotFoundException("Salary not found for Employee ID: " + employeeId);
            }
            return _ModelMapper.map(salary, SalaryDto.class);
        } catch (SalaryNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DatabaseException("Error fetching salary: " + ex.getMessage());
        }
    }

    public SalaryDto AddSalary(SalaryDto salaryDto) {
        if (salaryDto == null || salaryDto.get_EmployeeId() <= 0 || salaryDto.get_BaseSalary() == null) {
            throw new ValidationException("Invalid salary data");
        }
        try {
            Salary salary = _ModelMapper.map(salaryDto, Salary.class);
            _SalaryRepository.AddSalary(salary);
            return salaryDto;
        } catch (Exception ex) {
            throw new DatabaseException("Failed to add salary: " + ex.getMessage());
        }
    }

    public SalaryDto UpdateSalary(int employeeId, SalaryDto salaryDto) {
        if (salaryDto == null || employeeId <= 0) {
            throw new ValidationException("Invalid input data");
        }
        try {
            Salary existingSalary = _SalaryRepository.GetSalaryByEmployeeId(employeeId);
            if (existingSalary == null) {
                throw new SalaryNotFoundException("Salary not found for update with Employee ID: " + employeeId);
            }
            Salary salary = _ModelMapper.map(salaryDto, Salary.class);
            _SalaryRepository.UpdateSalary(employeeId, salary);
            return salaryDto;
        } catch (SalaryNotFoundException | ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DatabaseException("Failed to update salary: " + ex.getMessage());
        }
    }

    public void DeleteSalaryByEmployeeId(int employeeId) {
        if (employeeId <= 0) {
            throw new ValidationException("Invalid employee ID for delete");
        }
        try {
            _SalaryRepository.DeleteSalaryByEmployeeId(employeeId);
        } catch (Exception ex) {
            throw new DatabaseException("Failed to delete salary: " + ex.getMessage());
        }
    }

    public Map<String, Object> GetSalariesPaginated(int page, int size) {
        try {
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

        } catch (Exception ex) {
            throw new DatabaseException("Failed to fetch paginated salaries: " + ex.getMessage());
        }
    }

    public Map<String, Object> GetSalariesRange(int start, int end) {
        try {
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

        } catch (Exception ex) {
            throw new DatabaseException("Failed to fetch salary range: " + ex.getMessage());
        }
    }
}
