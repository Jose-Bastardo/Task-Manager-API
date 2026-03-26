package com.josebastardo.task_manager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class TaskRequest {

    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    private String description;

    @Setter
    @Getter
    private Boolean completed;
}

