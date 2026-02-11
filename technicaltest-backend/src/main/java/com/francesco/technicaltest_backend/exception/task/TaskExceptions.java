package com.francesco.technicaltest_backend.exception.task;

import org.springframework.http.HttpStatus;

import com.francesco.technicaltest_backend.exception.BusinessException;

/**
 * Classe che contiene le eccezioni relative all'entità Task
 */
public class TaskExceptions extends RuntimeException {

    public static class TaskNotFoundException extends BusinessException {
        public TaskNotFoundException(Long id) {
            super("Task not found", 
                HttpStatus.NOT_FOUND, 
                "Task with id " + id + " not found");
        }
    } 
}
