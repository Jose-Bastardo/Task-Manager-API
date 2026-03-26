package com.josebastardo.task_manager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class TaskResponse {
    @NotBlank
    @Setter
    @Getter
    private Long Id;

    @NotBlank
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
