package com.francesco.technicaltest_backend.dtos;

import com.francesco.technicaltest_backend.entity.enums.TaskPriority;
import com.francesco.technicaltest_backend.entity.enums.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO per l'aggiornamento di una task
 * 
 * @author Francesco
 */
@Data
public class UpdateTaskDTO {

    @NotBlank(message = "Title is required and cannot be null")
    private String title;

    private String description;
    private TaskStatus status;
    private TaskPriority priority;
}
