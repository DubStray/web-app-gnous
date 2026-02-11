package com.francesco.technicaltest_backend.service.impl;

import java.util.List;

import com.francesco.technicaltest_backend.entity.Task;
import org.springframework.stereotype.Service;

import com.francesco.technicaltest_backend.dtos.CreateTaskDTO;
import com.francesco.technicaltest_backend.dtos.TaskDTO;
import com.francesco.technicaltest_backend.dtos.UpdateTaskDTO;
import com.francesco.technicaltest_backend.dtos.UpdateTaskStatusDTO;
import com.francesco.technicaltest_backend.dtos.mapper.TaskMapper;
import com.francesco.technicaltest_backend.entity.enums.AuditLogEventType;
import com.francesco.technicaltest_backend.exception.task.TaskExceptions.TaskNotFoundException;
import com.francesco.technicaltest_backend.repository.TaskRepository;
import com.francesco.technicaltest_backend.service.AuditLogService;
import com.francesco.technicaltest_backend.service.TaskService;
import com.francesco.technicaltest_backend.service.WalletService;

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
    private final WalletService walletService;
    private final AuditLogService auditLogService;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, 
                          WalletService walletService, AuditLogService auditLogService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.walletService = walletService;
        this.auditLogService = auditLogService;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return this.taskMapper.toTaskDTOList(this.taskRepository.findAll());
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        return this.taskRepository.findById(id)
            .map(this.taskMapper::toTaskDTO)
            .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    public TaskDTO createTask(CreateTaskDTO createTaskDTO) {
        //Crea il task e lo salva
        Task task = this.taskMapper.toEntity(createTaskDTO);
        Task savedTask = this.taskRepository.save(task);
        
        // Scala 1 credito dal wallet
        // Se fallisce, @Transactional farà rollback anche della creazione del task
        walletService.debit(walletService.getTaskCost(), savedTask, "Task creation: " + savedTask.getTitle());
        
        // Registra l'evento di audit
        auditLogService.logEvent(
            AuditLogEventType.TASK_CREATED, 
            "Task created: " + savedTask.getTitle() +
            " with priority: " + savedTask.getPriority() +
            " with description: " + savedTask.getDescription() +
            " with status: " + savedTask.getStatus(),
            savedTask, 
            walletService.getTaskCost()
        );
        
        return this.taskMapper.toTaskDTO(savedTask);
    }

    @Override
    public TaskDTO updateTask(Long id, UpdateTaskDTO updateTaskDTO) {
        Task task = this.taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        this.taskMapper.updateTaskFromDTO(updateTaskDTO, task);

        TaskDTO updatedTask = this.taskMapper.toTaskDTO(this.taskRepository.save(task));

        auditLogService.logEvent(
            AuditLogEventType.TASK_UPDATED, 
            "Task updated: " + updatedTask.getTitle() +
            " with status: " + updatedTask.getStatus() +
            " with priority: " + updatedTask.getPriority() +
            " with description: " + updatedTask.getDescription(),
            task, 
            0);

        return updatedTask;
    }

    @Override
    public TaskDTO updateTaskStatus(Long id, UpdateTaskStatusDTO updateTaskStatusDTO) {
        Task task = this.taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        this.taskMapper.updateTaskStatusFromDTO(updateTaskStatusDTO, task);

        TaskDTO updatedTask = this.taskMapper.toTaskDTO(this.taskRepository.save(task));

        auditLogService.logEvent(
            AuditLogEventType.TASK_STATUS_CHANGED, 
            "Task status changed: " + updatedTask.getStatus(), 
            task, 
            0);

        return updatedTask;
    }

    @Override
    public void deleteTask(Long id) {
        Task task =  this.taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        this.taskRepository.delete(task);

        auditLogService.logEvent(
            AuditLogEventType.TASK_DELETED, 
            "Task deleted: " + task.getTitle(), 
            task, 
            0);
    }

}
