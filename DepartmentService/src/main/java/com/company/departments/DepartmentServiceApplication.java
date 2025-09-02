package com.company.departments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.company.departments")
public class DepartmentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DepartmentServiceApplication.class, args);
    }
}