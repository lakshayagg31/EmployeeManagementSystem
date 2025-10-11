package com.company.apigateway.config;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthHeaderFactory {

    @Value("${employeeservice.auth.username}")
    String _EmployeeUsername;
    @Value("${employeeservice.auth.password}")
    String _EmployeePassword;

    @Value("${departmentservice.auth.username}")
    String _DepartmentUsername;
    @Value("${departmentservice.auth.password}")
    String _DepartmentPassword;

    @Value("${salaryservice.auth.username}")
    String _SalaryUsername;
    @Value("${salaryservice.auth.password}")
    String _SalaryPassword;

    @Value("${apigateway.shared.secret}")
    String _SharedSecret;
    
    String BuildAuthHeader(String serviceName)
    {
        String username = "";
        String password = "";

        if(serviceName == "employeeservice")
        {
            username = _EmployeeUsername; 
            password = _EmployeePassword;
        }
        else if(serviceName == "departmentservice")
        {
            username = _DepartmentUsername; 
            password = _DepartmentPassword;            
        }
        else if(serviceName == "salaryservice")
        {
            username = _SalaryUsername; 
            password = _SalaryPassword;
        }

        String auth = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
    }

    String getSharedSecret()
    {
        return _SharedSecret;
    }
}