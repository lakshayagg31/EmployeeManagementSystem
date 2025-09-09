package com.company.salaries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.company.salaries")
@EnableDiscoveryClient
public class SalaryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SalaryServiceApplication.class, args);
    }
}
