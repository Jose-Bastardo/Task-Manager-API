package com.josebastardo.task_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class RegisterRequest {
    @NotBlank
    @Getter
    private String username;

    @Email
    @NotBlank
    @Getter
    private String email;

    @NotBlank
    @Getter
    private String password;
}
