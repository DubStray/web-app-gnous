package com.francesco.technicaltest_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.francesco.technicaltest_backend.dtos.CreateTaskDTO;
import com.francesco.technicaltest_backend.dtos.TaskDTO;
import com.francesco.technicaltest_backend.dtos.UpdateTaskDTO;
import com.francesco.technicaltest_backend.dtos.UpdateTaskStatusDTO;
import com.francesco.technicaltest_backend.service.TaskService;

import jakarta.validation.Valid;

/**
 * Controller per l'entitá Task.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(this.taskService.getAllTasks());
    }

    @GetMapping("/ordered-by-status")
    public ResponseEntity<List<TaskDTO>> getAllTasksOrderedByStatus() {
        return ResponseEntity.ok(this.taskService.getAllTasksOrderedByStatus());
    }

    @GetMapping("/ordered-by-priority")
    public ResponseEntity<List<TaskDTO>> getAllTasksOrderedByPriority() {
        return ResponseEntity.ok(this.taskService.getAllTasksOrderedByPriority());
    }

    @GetMapping("/ordered-by-date")
    public ResponseEntity<List<TaskDTO>> getAllTasksOrderedByDate() {
        return ResponseEntity.ok(this.taskService.getAllTasksOrderedByDate());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(this.taskService.getTaskById(id));
    }

    // @Valid per validare i campi del DTO in entrata (eventuali @NotBlanc, @NotNull ecc..)
    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid CreateTaskDTO createTaskDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.taskService.createTask(createTaskDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody @Valid UpdateTaskDTO updateTaskDTO) {
        return ResponseEntity.ok(this.taskService.updateTask(id, updateTaskDTO));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskDTO> updateTaskStatus(@PathVariable Long id, @RequestBody @Valid UpdateTaskStatusDTO updateTaskStatusDTO) {
        return ResponseEntity.ok(this.taskService.updateTaskStatus(id, updateTaskStatusDTO));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        this.taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
