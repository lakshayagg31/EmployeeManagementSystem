package com.company.departments.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.company.departments.dto.DepartmentDto;
import com.company.departments.irepository.IDepartmentRepository;
import com.company.departments.model.Department;

@Service(value = "DepartmentService")
public class DepartmentService {
    @Value("${com.company.pagination.size:10}")
    private int defaultPageSize;

    IDepartmentRepository _DepartmentRepository;
    ModelMapper _ModelMapper;

    @Autowired
    public DepartmentService(IDepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this._DepartmentRepository = departmentRepository;
        this._ModelMapper = modelMapper;
    }

    public DepartmentDto GetDepartmentById(int departmentId) {
        Department department = _DepartmentRepository.GetDepartmentById(departmentId);
        return _ModelMapper.map(department, DepartmentDto.class);
    }

    public String GetDepartmentNameById(int departmentId) {
        return _DepartmentRepository.GetDepartmentNameById(departmentId);
    }

    public Integer GetDepartmentIdByName(String departmentName) {
        Integer id = _DepartmentRepository.GetDepartmentIdByName(departmentName);
        if (id != null) {
            return id;
        }
        return null;
    }

    public DepartmentDto GetDepartmentByName(String departmentName) {
        Department department = _DepartmentRepository.GetDepartmentByName(departmentName);
        return _ModelMapper.map(department, DepartmentDto.class);
    }

    public List<DepartmentDto> GetAllDepartments() {
        List<Department> departments = _DepartmentRepository.GetAllDepartments();
        List<DepartmentDto> departmentDtos = new ArrayList<>();
        for (Department department : departments) {
            departmentDtos.add(_ModelMapper.map(department, DepartmentDto.class));
        }
        return departmentDtos;
    }

    public DepartmentDto AddDepartment(DepartmentDto departmentDto) {
    try {
        Department department = _ModelMapper.map(departmentDto, Department.class);
        Department savedDept = _DepartmentRepository.AddDepartment(department);
        return _ModelMapper.map(savedDept, DepartmentDto.class);
    } catch (org.springframework.dao.DataIntegrityViolationException e) {
        throw new RuntimeException("Wrong employee id: No such employee exists. Please try again with a correct id. No data has been added in the database.");
    }
}
    public Map<String, Object> GetDepartmentsPaginated(int page, int size) {
        int totalElements = _DepartmentRepository.GetDepartmentCount();
        int pageSize = (size > 0) ? size : defaultPageSize;
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        List<Department> departments = _DepartmentRepository.GetDepartmentsPaginated(page, pageSize);
        List<DepartmentDto> dtos = new ArrayList<>();
        for (Department dep : departments) {
            dtos.add(_ModelMapper.map(dep, DepartmentDto.class));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("size", pageSize);
        response.put("totalPages", totalPages);
        response.put("totalElements", totalElements);
        response.put("departments", dtos);
        return response;
    }

    public Map<String, Object> GetDepartmentsRange(int start, int end) {
        int totalElements = _DepartmentRepository.GetDepartmentCount();

        List<Department> departments = _DepartmentRepository.GetDepartmentsRange(start, end);
        List<DepartmentDto> dtos = new ArrayList<>();
        for (Department dep : departments) {
            dtos.add(_ModelMapper.map(dep, DepartmentDto.class));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("start", start);
        response.put("end", end);
        response.put("totalElements", totalElements);
        response.put("departments", dtos);
        return response;
    }


}