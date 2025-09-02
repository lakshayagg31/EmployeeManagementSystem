package com.company.salaries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.company.salaries")
public class SalaryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SalaryServiceApplication.class, args);
    }
}
