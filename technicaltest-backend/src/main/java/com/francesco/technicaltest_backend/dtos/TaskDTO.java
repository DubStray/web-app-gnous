package com.francesco.technicaltest_backend.dtos;

import java.time.LocalDateTime;

import com.francesco.technicaltest_backend.entity.enums.TaskPriority;
import com.francesco.technicaltest_backend.entity.enums.TaskStatus;

import lombok.Data;

/**
 * DTO per l'entitá Task.
 * 
 * @author Francesco
 */
@Data
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime createdAt;
}
