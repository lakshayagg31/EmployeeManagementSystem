package com.company.departments.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.departments.dto.DepartmentDto;
import com.company.departments.dto.DepartmentResponseDto;
import com.company.departments.exception.ValidationException;
import com.company.departments.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService _DepartmentService;

    public DepartmentController(DepartmentService departmentService) {
        this._DepartmentService = departmentService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllDepartments() {
        try {
            List<DepartmentDto> departments = _DepartmentService.GetAllDepartments();
            return ResponseEntity.ok(departments);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get all departments: " + ex.getMessage());
        }
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<DepartmentResponseDto> getDepartmentById(@PathVariable int departmentId) {
        DepartmentResponseDto response = new DepartmentResponseDto();
        try {
            DepartmentDto dept = _DepartmentService.GetDepartmentById(departmentId);
            response.setDepartment(dept);
            response.setStatus("Success");
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus("Failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/department")
    public ResponseEntity<DepartmentResponseDto> getDepartmentByName(@RequestParam("departmentname") String departmentName) {
        DepartmentResponseDto response = new DepartmentResponseDto();
        try {
            DepartmentDto dept = _DepartmentService.GetDepartmentByName(departmentName);
            response.setDepartment(dept);
            response.setStatus("Success");
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setStatus("Failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/department")
    public ResponseEntity<DepartmentResponseDto> addDepartment(@RequestBody DepartmentDto departmentDto) {
        DepartmentResponseDto response = new DepartmentResponseDto();
        try {
            DepartmentDto saved = _DepartmentService.AddDepartment(departmentDto);
            response.setDepartment(saved);
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

    @GetMapping("/nextpage")
    public ResponseEntity<?> getDepartmentsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> data = _DepartmentService.GetDepartmentsPaginated(page, size);
            return ResponseEntity.ok(data);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get paginated departments: " + ex.getMessage());
        }
    }

    @GetMapping("/nextpage/range")
    public ResponseEntity<?> getDepartmentsRange(
            @RequestParam int start,
            @RequestParam int end) {
        try {
            Map<String, Object> data = _DepartmentService.GetDepartmentsRange(start, end);
            return ResponseEntity.ok(data);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get department range: " + ex.getMessage());
        }
    }
}
