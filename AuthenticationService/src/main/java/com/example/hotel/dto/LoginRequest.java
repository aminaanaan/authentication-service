package com.example.hotel.dto;

import lombok.*;

import javax.validation.constraints.*;

@Data
public class LoginRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}
