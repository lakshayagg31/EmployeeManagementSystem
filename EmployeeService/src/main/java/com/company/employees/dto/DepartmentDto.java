package com.company.employees.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DepartmentDto {
    @JsonProperty("departmentid")
    int departmentId;

    @JsonProperty("departmentname")
    String departmentName;

    @JsonProperty("headid")
    int headId;

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    public int getHeadId() { return headId; }
    public void setHeadId(int headId) { this.headId = headId; }
}
