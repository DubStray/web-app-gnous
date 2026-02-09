package com.francesco.technicaltest_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francesco.technicaltest_backend.entity.Task;

/**
 * Repository per l'entitá Task.
 * Spring Data rileva la repo e la registra come Bean
 * 
 * @author Francesco
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

}
