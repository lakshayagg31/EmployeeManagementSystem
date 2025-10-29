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
import com.company.departments.exception.DatabaseException;
import com.company.departments.exception.DepartmentNotFoundException;
import com.company.departments.exception.ValidationException;
import com.company.departments.irepository.IDepartmentRepository;
import com.company.departments.model.Department;

@Service(value = "DepartmentService")
public class DepartmentService {
    @Value("${com.company.pagination.size:10}")
    private int defaultPageSize;

    private final IDepartmentRepository _DepartmentRepository;
    private final ModelMapper _ModelMapper;

    @Autowired
    public DepartmentService(IDepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this._DepartmentRepository = departmentRepository;
        this._ModelMapper = modelMapper;
    }

    public DepartmentDto GetDepartmentById(int departmentId) {
        try {
            Department department = _DepartmentRepository.GetDepartmentById(departmentId);
            if(department == null) {
                throw new DepartmentNotFoundException("Department not found for ID: " + departmentId);
            }
            return _ModelMapper.map(department, DepartmentDto.class);
        } catch (DepartmentNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DatabaseException("Error fetching department: " + ex.getMessage());
        }
    }

    public List<DepartmentDto> GetAllDepartments() {
        try {
            List<Department> departments = _DepartmentRepository.GetAllDepartments();
            List<DepartmentDto> dtos = new ArrayList<>();
            for (Department dep : departments) {
                dtos.add(_ModelMapper.map(dep, DepartmentDto.class));
            }
            return dtos;
        } catch (Exception ex) {
            throw new DatabaseException("Error fetching departments: " + ex.getMessage());
        }
    }

    public DepartmentDto AddDepartment(DepartmentDto departmentDto) {
        if(departmentDto == null || departmentDto.getDepartmentName() == null || departmentDto.getDepartmentName().isBlank()) {
            throw new ValidationException("Department name cannot be empty");
        }
        try {
            Department department = _ModelMapper.map(departmentDto, Department.class);
            Department saved = _DepartmentRepository.AddDepartment(department);
            return _ModelMapper.map(saved, DepartmentDto.class);
        } catch (Exception ex) {
            throw new DatabaseException("Failed to add department: " + ex.getMessage());
        }
    }

    public DepartmentDto GetDepartmentByName(String departmentName) {
        try {
            Department department = _DepartmentRepository.GetDepartmentByName(departmentName);
            if (department == null) {
                throw new DepartmentNotFoundException("Department not found for name: " + departmentName);
            }
            return _ModelMapper.map(department, DepartmentDto.class);
        } catch (DepartmentNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DatabaseException("Error fetching department by name: " + ex.getMessage());
        }
    }

    public Map<String, Object> GetDepartmentsPaginated(int page, int size) {
        try {
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
        } catch (Exception ex) {
            throw new DatabaseException("Failed to fetch paginated departments: " + ex.getMessage());
        }
    }

    public Map<String, Object> GetDepartmentsRange(int start, int end) {
        try {
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
        } catch (Exception ex) {
            throw new DatabaseException("Failed to fetch department range: " + ex.getMessage());
        }
    }
}
