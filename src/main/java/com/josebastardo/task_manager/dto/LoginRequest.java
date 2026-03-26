package com.josebastardo.task_manager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class LoginRequest {
    @NotBlank
    @Getter
    private String username;

    @NotBlank
    @Getter
    private String password;
}
