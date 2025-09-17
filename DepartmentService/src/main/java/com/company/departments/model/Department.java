package com.company.departments.model;

public class Department {
    private Integer departmentId;
    private String departmentName;
    private Integer headId;

    public Department() {}

    public Department(Integer departmentId, String departmentName, Integer headId) {
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
