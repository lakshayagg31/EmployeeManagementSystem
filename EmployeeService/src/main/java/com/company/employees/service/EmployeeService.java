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
import com.company.employees.exception.DatabaseException;
import com.company.employees.exception.EmployeeNotFoundException;
import com.company.employees.exception.ValidationException;
import com.company.employees.irepository.IEmployeeRepository;
import com.company.employees.model.Employee;

@Service(value = "EmployeeService")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class EmployeeService {

    @Value("${com.company.pagination.size:10}")
    private int defaultPageSize;

    private final IEmployeeRepository _EmployeeRepository;
    private final ModelMapper _ModelMapper;
    private final RestClient _DepartmentRestClient;
    private final RestClient _SalaryRestClient;

    @Autowired
    public EmployeeService(IEmployeeRepository employeeRepository,
                           ModelMapper modelMapper,
                           RestClient.Builder restClientBuilder) {
        this._EmployeeRepository = employeeRepository;
        this._ModelMapper = modelMapper;
        _DepartmentRestClient = restClientBuilder.baseUrl("http://localhost:9003/departments").build();
        _SalaryRestClient = restClientBuilder.baseUrl("http://localhost:9004/salaries").build();
    }

    public EmployeeDetailsDto GetEmployeeDetailsById(int employeeId) {
        try {
            Employee employee = _EmployeeRepository.GetEmployeeById(employeeId);
            if (employee == null) {
                throw new EmployeeNotFoundException("Employee not found for ID: " + employeeId);
            }

            String departmentName = null;
            Double baseSalary = null;

            try {
                departmentName = _DepartmentRestClient.get()
                    .uri("/department/{departmentId}", employee.getDepartmentId())
                    .retrieve()
                    .body(DepartmentDto.class)
                    .getDepartmentName();
            } catch (Exception e) {
                System.out.println("Error fetching department: " + e.getMessage());
            }

            try {
                baseSalary = _SalaryRestClient.get()
                    .uri("/salary/{employeeId}", employeeId)
                    .retrieve()
                    .body(Double.class);
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
        } catch (EmployeeNotFoundException | ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DatabaseException("Failed to get employee details: " + ex.getMessage());
        }
    }

    public List<EmployeeDto> GetAllEmployees() {
        try {
            List<Employee> employees = _EmployeeRepository.GetAllEmployees();
            List<EmployeeDto> dtos = new ArrayList<>();
            for (Employee emp : employees) {
                dtos.add(_ModelMapper.map(emp, EmployeeDto.class));
            }
            return dtos;
        } catch (Exception ex) {
            throw new DatabaseException("Failed to fetch employees: " + ex.getMessage());
        }
    }

    public EmployeeDto GetEmployeeByEmail(String email) {
        try {
            Employee emp = _EmployeeRepository.GetEmployeeByEmail(email);
            if(emp == null) {
                throw new EmployeeNotFoundException("Employee not found for email: " + email);
            }
            return _ModelMapper.map(emp, EmployeeDto.class);
        } catch (EmployeeNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DatabaseException("Failed to get employee by email: " + ex.getMessage());
        }
    }

    public EmployeeDto AddEmployee(EmployeeDto employeeDto) {
        if(employeeDto == null || employeeDto.getEmail() == null || employeeDto.getEmail().isBlank()) {
            throw new ValidationException("Email cannot be empty");
        }
        try {
            Employee emp = _ModelMapper.map(employeeDto, Employee.class);
            Employee savedEmp = _EmployeeRepository.AddEmployee(emp);
            return _ModelMapper.map(savedEmp, EmployeeDto.class);
        } catch (Exception ex) {
            throw new DatabaseException("Failed to add employee: " + ex.getMessage());
        }
    }

    public void DeleteEmployeeByEmail(String email) {
        if(email == null || email.isBlank()) {
            throw new ValidationException("Email cannot be empty to delete employee");
        }
        try {
            _EmployeeRepository.DeleteEmployeeByEmail(email);
        } catch (Exception ex) {
            throw new DatabaseException("Failed to delete employee: " + ex.getMessage());
        }
    }

    public Map<String, Object> GetEmployeesPaginated(int page, int size) {
        try {
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
        } catch (Exception ex) {
            throw new DatabaseException("Failed to fetch paginated employees: " + ex.getMessage());
        }
    }

    public Map<String, Object> GetEmployeesRange(int start, int end) {
        try {
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
        } catch (Exception ex) {
            throw new DatabaseException("Failed to fetch employee range: " + ex.getMessage());
        }
    }
}
