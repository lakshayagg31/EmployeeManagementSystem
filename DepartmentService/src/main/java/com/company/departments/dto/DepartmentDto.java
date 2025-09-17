package com.company.departments.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DepartmentDto {
    @JsonProperty("departmentid")
    private Integer departmentId;    // Allow null

    @JsonProperty("departmentname")
    private String departmentName;

    @JsonProperty("headid")
    private Integer headId;          // Allow null

    public DepartmentDto() {}

    public DepartmentDto(Integer departmentId, String departmentName, Integer headId) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.headId = headId;
    }

    public Integer getDepartmentId() { return departmentId; }
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public Integer getHeadId() { return headId; }
    public void setHeadId(Integer headId) { this.headId = headId; }
}
