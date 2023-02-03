package com.example.hotel.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.io.*;

@Data
public class UserRequest implements Serializable {

    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "Role is mandatory")
    private String role;





}
