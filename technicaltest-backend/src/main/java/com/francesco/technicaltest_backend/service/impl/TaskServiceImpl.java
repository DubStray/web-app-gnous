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
import com.francesco.technicaltest_backend.entity.enums.TaskStatus;
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
    public List<TaskDTO> getAllTasksOrderedByStatus() {
        return this.taskMapper.toTaskDTOList(this.taskRepository.orderTaskByStatus());
    }

    @Override
    public List<TaskDTO> getAllTasksOrderedByPriority() {
        return this.taskMapper.toTaskDTOList(this.taskRepository.orderTaskByPriority());
    }

    @Override
    public List<TaskDTO> getAllTasksOrderedByDate() {
        return this.taskMapper.toTaskDTOList(this.taskRepository.orderTaskByDate());
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
            "TASK [CREATED]: " + savedTask.getTitle() +
            " -- PRIORITY: " + savedTask.getPriority() +
            " -- DESCRIPTION: " + savedTask.getDescription() +
            " -- STATUS: " + savedTask.getStatus(),
            savedTask, 
            0
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
            "TASK [UPDATED]: " + updatedTask.getTitle() +
            " -- STATUS: " + updatedTask.getStatus() +
            " -- PRIORITY: " + updatedTask.getPriority() +
            " -- DESCRIPTION: " + updatedTask.getDescription(),
            task, 
            0);

        return updatedTask;
    }

    @Override
    public TaskDTO updateTaskStatus(Long id, UpdateTaskStatusDTO updateTaskStatusDTO) {
        Task task = this.taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        TaskStatus oldStatus = task.getStatus();
        this.taskMapper.updateTaskStatusFromDTO(updateTaskStatusDTO, task);

        TaskDTO updatedTask = this.taskMapper.toTaskDTO(this.taskRepository.save(task));

        if (updatedTask.getStatus() == TaskStatus.DONE && oldStatus != TaskStatus.DONE) {
            walletService.credit(2, task, "Completed Task: " + updatedTask.getTitle());

            auditLogService.logEvent(
                AuditLogEventType.WALLET_CREDIT, 
                "TASK [COMPLETED]: " + updatedTask.getTitle(), 
                task, 
                2);
        }

        auditLogService.logEvent(
            AuditLogEventType.TASK_STATUS_CHANGED, 
            "TASK [STATUS CHANGED]: " + updatedTask.getStatus() +
            " -- OLD STATUS: " + oldStatus,
            task, 
            0);

        return updatedTask;
    }

    @Override
    public void deleteTask(Long id) {
        Task task =  this.taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        // Sgancia i log associati prima della cancellazione fisica
        auditLogService.detachFromTask(task);
        walletService.detachFromTask(task);

        if (task.getStatus() != TaskStatus.DONE) {
            walletService.credit(walletService.getTaskDeletionRefund(), null, "Refund for task deletion: " + task.getTitle());
        }

        this.taskRepository.delete(task);

        auditLogService.logEvent(
            AuditLogEventType.TASK_DELETED, 
            "TASK [DELETED]: " + task.getTitle(), 
            null, 
            1);
    }

}
