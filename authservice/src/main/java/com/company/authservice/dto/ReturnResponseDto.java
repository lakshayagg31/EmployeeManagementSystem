package com.company.authservice.dto;

public class ReturnResponseDto {

    private String status;
    private String email;

    public ReturnResponseDto() {}

    public ReturnResponseDto(String status, String email) {
        this.status = status;
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
