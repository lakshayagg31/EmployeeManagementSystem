package com.company.employees.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.employees.dto.EmployeeDetailsDto;
import com.company.employees.dto.EmployeeDetailsResponseDto;
import com.company.employees.dto.EmployeeDto;
import com.company.employees.dto.EmployeeResponseDto;
import com.company.employees.exception.ValidationException;
import com.company.employees.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService _EmployeeService;

    public EmployeeController(EmployeeService employeeService) {
        this._EmployeeService = employeeService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllEmployees() {
        try {
            List<EmployeeDto> employees = _EmployeeService.GetAllEmployees();
            return ResponseEntity.ok(employees);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get all employees: " + ex.getMessage());
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<EmployeeDetailsResponseDto> getEmployeeById(@PathVariable("employeeId") int employeeId) {
        EmployeeDetailsResponseDto response = new EmployeeDetailsResponseDto();
        try {
            EmployeeDetailsDto employeeDetails = _EmployeeService.GetEmployeeDetailsById(employeeId);
            response.setEmployeeDetails(employeeDetails);
            response.setStatus("Success");
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus("Failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/employee")
    public ResponseEntity<EmployeeResponseDto> getEmployeeByEmail(@RequestParam("email") String email) {
        EmployeeResponseDto response = new EmployeeResponseDto();
        try {
            EmployeeDto employee = _EmployeeService.GetEmployeeByEmail(email);
            response.setEmployee(employee);
            response.setStatus("Success");
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus("Failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeResponseDto> addEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeResponseDto response = new EmployeeResponseDto();
        try {
            EmployeeDto saved = _EmployeeService.AddEmployee(employeeDto);
            response.setEmployee(saved);
            response.setStatus("Success");
            return ResponseEntity.ok(response);
        } catch (ValidationException ex) {
            response.setStatus("Validation failed: " + ex.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception ex) {
            response.setStatus("Failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/employee/{email}")
    public ResponseEntity<EmployeeResponseDto> deleteEmployeeByEmail(@PathVariable("email") String email) {
        EmployeeResponseDto response = new EmployeeResponseDto();
        try {
            _EmployeeService.DeleteEmployeeByEmail(email);
            response.setEmployee(null);
            response.setStatus("Deleted successfully");
            return ResponseEntity.ok(response);
        } catch (ValidationException ex) {
            response.setStatus("Validation failed: " + ex.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception ex) {
            response.setStatus("Failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/nextpage")
    public ResponseEntity<?> getEmployeesPaginated(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> data = _EmployeeService.GetEmployeesPaginated(page, size);
            return ResponseEntity.ok(data);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get paginated employees: " + ex.getMessage());
        }
    }

    @GetMapping("/nextpage/range")
    public ResponseEntity<?> getEmployeesRange(@RequestParam int start, @RequestParam int end) {
        try {
            Map<String, Object> data = _EmployeeService.GetEmployeesRange(start, end);
            return ResponseEntity.ok(data);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get employee range: " + ex.getMessage());
        }
    }
}
