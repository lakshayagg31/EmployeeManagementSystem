package com.company.employees.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.company.employees.dto.DepartmentDto;
import com.company.employees.dto.EmployeeDetailsDto;
import com.company.employees.dto.EmployeeDto;
import com.company.employees.dto.SalaryDto;
import com.company.employees.irepository.IEmployeeRepository;
import com.company.employees.model.Employee;

@Service(value = "EmployeeService")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class EmployeeService {
    @Value("${com.company.pagination.size:10}")
    private int defaultPageSize;

    IEmployeeRepository _EmployeeRepository;
    ModelMapper _ModelMapper;
    final RestClient _DepartmentRestClient;
    final RestClient _SalaryRestClient;

    @Autowired
    public EmployeeService(
        IEmployeeRepository employeeRepository,
        ModelMapper modelMapper,
        RestClient.Builder restClientBuilder
    ) {
        this._EmployeeRepository = employeeRepository;
        this._ModelMapper = modelMapper;
        _DepartmentRestClient = restClientBuilder.baseUrl("http://localhost:9003/departments").build();
        _SalaryRestClient = restClientBuilder.baseUrl("http://localhost:9004/salaries").build();
    }

    public EmployeeDetailsDto GetEmployeeDetailsById(int employeeId) {
        Employee employee = _EmployeeRepository.GetEmployeeById(employeeId);
        if (employee == null) {
            return null;
        }

        String departmentName = null;
        Double baseSalary = null;

        try {
            DepartmentDto departmentDto = _DepartmentRestClient.get()
                .uri("/department/{departmentId}", employee.getDepartmentId())
                .retrieve()
                .body(DepartmentDto.class);
            if (departmentDto != null) {
                departmentName = departmentDto.getDepartmentName();
            }
        } catch (Exception e) {
            System.out.println("Error fetching department: " + e.getMessage());
        }

        try {
            SalaryDto salaryDto = _SalaryRestClient.get()
                .uri("/salary/{employeeId}", employeeId)
                .retrieve()
                .body(SalaryDto.class);

            System.out.println("Received SalaryDto object: " + salaryDto);
            if (salaryDto != null) {
                baseSalary = salaryDto.getBaseSalary();
                System.out.println("Extracted base salary: " + baseSalary);
            } else {
                System.out.println("SalaryDto is null");
            }

        } catch (Exception e) {
            System.out.println("Error fetching salary: " + e.getMessage());
        }

        return new EmployeeDetailsDto(
            employee.getEmployeeId(),
            employee.getName(),
            employee.getEmail(),
            employee.getJobTitle(),
            employee.getDepartmentId(),
            departmentName,
            baseSalary
        );
    }

    public List<EmployeeDto> GetAllEmployees() {
        List<Employee> employees = _EmployeeRepository.GetAllEmployees();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee emp : employees) {
            employeeDtos.add(_ModelMapper.map(emp, EmployeeDto.class));
        }
        return employeeDtos;
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
        Employee savedEmployee = _EmployeeRepository.AddEmployee(employee);
        return _ModelMapper.map(savedEmployee, EmployeeDto.class);
    }


    public void DeleteEmployeeByEmail(String email) {
        _EmployeeRepository.DeleteEmployeeByEmail(email);
    }

    public Map<String, Object> GetEmployeesPaginated(int page, int size) {
        int totalElements = _EmployeeRepository.GetEmployeeCount();
        int pageSize = (size > 0) ? size : defaultPageSize;
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        List<Employee> employees = _EmployeeRepository.GetEmployeesPaginated(page, pageSize);
        List<EmployeeDto> dtos = new ArrayList<>();
        for (Employee emp : employees) {
            dtos.add(_ModelMapper.map(emp, EmployeeDto.class));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("size", pageSize);
        response.put("totalPages", totalPages);
        response.put("totalElements", totalElements);
        response.put("employees", dtos);
        return response;
    }

    public Map<String, Object> GetEmployeesRange(int start, int end) {
        int totalElements = _EmployeeRepository.GetEmployeeCount();
        List<Employee> employees = _EmployeeRepository.GetEmployeesRange(start, end);

        List<EmployeeDto> dtos = new ArrayList<>();
        for (Employee emp : employees) {
            dtos.add(_ModelMapper.map(emp, EmployeeDto.class));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("start", start);
        response.put("end", end);
        response.put("totalElements", totalElements);
        response.put("employees", dtos);
        return response;
    }

}
