package com.company.salaries.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.salaries.dto.SalaryDto;
import com.company.salaries.dto.SalaryResponseDto;
import com.company.salaries.exception.ValidationException;
import com.company.salaries.service.SalaryService;

@RestController
@RequestMapping("/salaries")
public class SalaryController {

    private final SalaryService _SalaryService;

    public SalaryController(SalaryService salaryService) {
        this._SalaryService = salaryService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllSalaries() {
        try {
            List<SalaryDto> salaries = _SalaryService.GetAllSalaries();
            return ResponseEntity.ok(salaries);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get all salaries: " + ex.getMessage());
        }
    }

    @GetMapping("/salary/{employeeId}")
    public ResponseEntity<SalaryResponseDto> getSalaryByEmployeeId(@PathVariable int employeeId) {
        SalaryResponseDto response = new SalaryResponseDto();
        try {
            SalaryDto salary = _SalaryService.GetSalaryByEmployeeId(employeeId);
            response.setSalary(salary);
            response.setStatus("Success");
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus("Failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/salary")
    public ResponseEntity<SalaryResponseDto> addSalary(@RequestBody SalaryDto salaryDto) {
        SalaryResponseDto response = new SalaryResponseDto();
        try {
            SalaryDto saved = _SalaryService.AddSalary(salaryDto);
            response.setSalary(saved);
            response.setStatus("Success");
            return ResponseEntity.ok(response);
        } catch (ValidationException ex) {
            response.setStatus("Validation failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            response.setStatus("Failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/salary/{employeeId}")
    public ResponseEntity<SalaryResponseDto> updateSalary(@PathVariable int employeeId, @RequestBody SalaryDto salaryDto) {
        SalaryResponseDto response = new SalaryResponseDto();
        try {
            SalaryDto updated = _SalaryService.UpdateSalary(employeeId, salaryDto);
            response.setSalary(updated);
            response.setStatus("Success");
            return ResponseEntity.ok(response);
        } catch (ValidationException ex) {
            response.setStatus("Validation failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            response.setStatus("Failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/salary/{employeeId}")
    public ResponseEntity<SalaryResponseDto> deleteSalary(@PathVariable int employeeId) {
        SalaryResponseDto response = new SalaryResponseDto();
        try {
            _SalaryService.DeleteSalaryByEmployeeId(employeeId);
            response.setStatus("Success");
            return ResponseEntity.ok(response);
        } catch (ValidationException ex) {
            response.setStatus("Validation failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            response.setStatus("Failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/nextpage")
    public ResponseEntity<?> getSalariesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> data = _SalaryService.GetSalariesPaginated(page, size);
            return ResponseEntity.ok(data);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get paginated salaries: " + ex.getMessage());
        }
    }

    @GetMapping("/nextpage/range")
    public ResponseEntity<?> getSalariesRange(
            @RequestParam int start,
            @RequestParam int end) {
        try {
            Map<String, Object> data = _SalaryService.GetSalariesRange(start, end);
            return ResponseEntity.ok(data);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get salary range: " + ex.getMessage());
        }
    }
}
