package com.francesco.technicaltest_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.francesco.technicaltest_backend.entity.Task;

/**
 * Repository per l'entitá Task.
 * Spring Data rileva la repo e la registra come Bean
 * 
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

    // QUery custom così da ordinare le task in base allo status
    @Query("SELECT t FROM Task t ORDER BY CASE t.status WHEN 'TODO' THEN 1 WHEN 'DOING' THEN 2 WHEN 'DONE' THEN 3 END")
    List<Task> orderTaskByStatus();

    // Query per ordinare le task in base alla priorità (dalla più alta alla più bassa)
    @Query("SELECT t FROM Task t ORDER BY CASE t.priority WHEN 'HIGH' THEN 1 WHEN 'MEDIUM' THEN 2 WHEN 'LOW' THEN 3 END")
    List<Task> orderTaskByPriority();

    // Query per ordinare le task in base alla data
    @Query("SELECT t FROM Task t ORDER BY t.createdAt DESC")
    List<Task> orderTaskByDate();
}
