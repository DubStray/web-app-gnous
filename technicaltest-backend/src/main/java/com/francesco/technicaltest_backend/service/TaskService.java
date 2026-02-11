package com.francesco.technicaltest_backend.service;

import java.util.List;

import com.francesco.technicaltest_backend.dtos.CreateTaskDTO;
import com.francesco.technicaltest_backend.dtos.TaskDTO;
import com.francesco.technicaltest_backend.dtos.UpdateTaskDTO;
import com.francesco.technicaltest_backend.dtos.UpdateTaskStatusDTO;

/**
 * Interfaccia Service per l'entità Task.
 *
 */
public interface TaskService {

    List<TaskDTO> getAllTasks();

    TaskDTO getTaskById(Long id);

    TaskDTO createTask(CreateTaskDTO createTaskDTO);

    TaskDTO updateTask(Long id, UpdateTaskDTO updateTaskDTO);

    TaskDTO updateTaskStatus(Long id, UpdateTaskStatusDTO updateTaskStatusDTO);

    void deleteTask(Long id);
}
