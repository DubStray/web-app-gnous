package com.francesco.technicaltest_backend.dtos;

import com.francesco.technicaltest_backend.entity.enums.TaskStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTaskStatusDTO {
    @NotNull(message = "Status is required")
    private TaskStatus status;
}
