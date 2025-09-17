package com.company.departments.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.departments.dto.DepartmentDto;
import com.company.departments.irepository.IDepartmentRepository;
import com.company.departments.model.Department;

@Service(value = "DepartmentService")
public class DepartmentService {
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


}