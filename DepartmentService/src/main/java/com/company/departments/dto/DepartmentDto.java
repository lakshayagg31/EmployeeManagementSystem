package com.company.departments.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DepartmentDto {
    @JsonProperty("departmentid")
    int _DepartmentId;
    @JsonProperty("departmentname")
    String _DepartmentName;
    @JsonProperty("headid")
    int _HeadId;

    public DepartmentDto() {
        // Default constructor
    }

    public DepartmentDto(int departmentId, String departmentName, int headId) {
        this._DepartmentId = departmentId;
        this._DepartmentName = departmentName;
        this._HeadId = headId;
    }

    // getters and setters

    public int get_DepartmentId() {
        return _DepartmentId;
    }

    public void set_DepartmentId(int departmentId) {
        this._DepartmentId = departmentId;
    }

    public String get_DepartmentName() {
        return _DepartmentName;
    }

    public void set_DepartmentName(String departmentName) {
        this._DepartmentName = departmentName;
    }

    public int get_HeadId() {
        return _HeadId;
    }

    public void set_HeadId(int headId) {
        this._HeadId = headId;
    }
}
