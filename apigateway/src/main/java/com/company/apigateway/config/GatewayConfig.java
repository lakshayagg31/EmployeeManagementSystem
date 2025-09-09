package com.company.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder)
    {
        return builder.routes()
            .route("departmentservice", r -> r.path("/departments/**")
                .uri("lb://departmentservice"))
            .route("salaryservice", r -> r.path("/salaries/**")
                .uri("lb://salaryservice"))
            .route("employeeservice", r -> r.path("/employees/**")
                .uri("lb://employeeservice"))
            .build();
    }
}