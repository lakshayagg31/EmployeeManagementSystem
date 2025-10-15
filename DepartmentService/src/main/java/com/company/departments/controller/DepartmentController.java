package com.company.departments.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.departments.dto.DepartmentDto;
import com.company.departments.service.DepartmentService;

@RequestMapping("/departments")
@RestController
public class DepartmentController {

    DepartmentService _DepartmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this._DepartmentService = departmentService;
    }

    @GetMapping(path = "/")
    public List<DepartmentDto> getAllDepartments() {
        return _DepartmentService.GetAllDepartments();
    }

    @GetMapping(path = "/department/{departmentId}")
    public DepartmentDto getDepartmentById(@PathVariable("departmentId") int departmentId) {
        return _DepartmentService.GetDepartmentById(departmentId);
    }

    @GetMapping(path = "/department/departmentname/{departmentId}")
    public String getDepartmentNameById(@PathVariable("departmentId") int departmentId) {
        return _DepartmentService.GetDepartmentNameById(departmentId);
    }

    @GetMapping(path = "/department/departmentid/{departmentName}")
    public Integer getDepartmentIdByName(@PathVariable("departmentName") String departmentName) {
        return _DepartmentService.GetDepartmentIdByName(departmentName);
    }

    @GetMapping(path = "/department")
    public DepartmentDto getDepartmentByName(@RequestParam("departmentname") String departmentName) {
        return _DepartmentService.GetDepartmentByName(departmentName);
    }

    @PostMapping("/department")
    public ResponseEntity<?> addDepartment(@RequestBody DepartmentDto department) {
        try {
            DepartmentDto saved = _DepartmentService.AddDepartment(department);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/nextpage")
    public ResponseEntity<?> getDepartmentsPaginated(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "0") int size
    ) {
        Map<String, Object> data = _DepartmentService.GetDepartmentsPaginated(page, size);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/nextpage/range")
    public ResponseEntity<?> getDepartmentsRange(
        @RequestParam int start,
        @RequestParam int end
    ) {
        Map<String, Object> data = _DepartmentService.GetDepartmentsRange(start, end);
        return ResponseEntity.ok(data);
    }


}