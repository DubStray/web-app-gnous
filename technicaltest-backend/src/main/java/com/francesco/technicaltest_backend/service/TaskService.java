package com.francesco.technicaltest_backend.service;

import java.util.List;

import com.francesco.technicaltest_backend.dtos.CreateTaskDTO;
import com.francesco.technicaltest_backend.dtos.TaskDTO;
import com.francesco.technicaltest_backend.dtos.UpdateTaskDTO;

/**
 * Interfaccia Service per l'entità Task.
 *
 */
public interface TaskService {

    List<TaskDTO> getAllTasks();

    TaskDTO getTaskById(Long id);

    TaskDTO createTask(CreateTaskDTO createTaskDTO);

    TaskDTO updateTask(Long id, UpdateTaskDTO updateTaskDTO);

    void deleteTask(Long id);
}
