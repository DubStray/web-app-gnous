package com.francesco.technicaltest_backend.service.impl;

import java.util.List;

import com.francesco.technicaltest_backend.entity.Task;
import org.springframework.stereotype.Service;

import com.francesco.technicaltest_backend.dtos.CreateTaskDTO;
import com.francesco.technicaltest_backend.dtos.TaskDTO;
import com.francesco.technicaltest_backend.dtos.UpdateTaskDTO;
import com.francesco.technicaltest_backend.dtos.mapper.TaskMapper;
import com.francesco.technicaltest_backend.repository.TaskRepository;
import com.francesco.technicaltest_backend.service.TaskService;

import jakarta.transaction.Transactional;

/**
 * Classe di implementazione Service per l'entitá Task.
 * 
 * @Transactional per effetuare rollback sul DB in caso di errore
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return this.taskMapper.toTaskDTOList(this.taskRepository.findAll());
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        return this.taskRepository.findById(id)
            .map(this.taskMapper::toTaskDTO)
            .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Override
    public TaskDTO createTask(CreateTaskDTO createTaskDTO) {
        return this.taskMapper.toTaskDTO(this.taskRepository.save(this.taskMapper.toEntity(createTaskDTO)));
    }

    @Override
    public TaskDTO updateTask(Long id, UpdateTaskDTO updateTaskDTO) {

        Task task =  this.taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));

        this.taskMapper.updateTaskFromDTO(updateTaskDTO, task);

        return this.taskMapper.toTaskDTO(this.taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) {
        Task task =  this.taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));

        this.taskRepository.delete(task);
    }

}
