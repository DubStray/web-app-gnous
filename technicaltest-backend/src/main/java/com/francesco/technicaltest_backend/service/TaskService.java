package com.francesco.technicaltest_backend.service;

import java.util.List;

import com.francesco.technicaltest_backend.dtos.TaskDTO;

/**
 * Interfaccia Service per l'entità Task.
 *
 */
public interface TaskService {

    List<TaskDTO> getAllTasks();

    TaskDTO getTaskById(Long id);

    TaskDTO createTask(TaskDTO taskDTO);

    TaskDTO updateTask(Long id, TaskDTO taskDTO);

    void deleteTask(Long id);
}
