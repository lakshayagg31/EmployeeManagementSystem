package com.company.departments.dto;

public class DepartmentResponseDto {

    private DepartmentDto department; // Department data on success
    private String status;            // Status message (Success or error description)

    public DepartmentResponseDto() {}

    public DepartmentResponseDto(DepartmentDto department, String status) {
        this.department = department;
        this.status = status;
    }

    public DepartmentDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDto department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
