package com.francesco.technicaltest_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francesco.technicaltest_backend.entity.User;

/**
 * Repository per l'entitá User.
 * Spring Data rileva la repo e la registra come Bean
 * 
 * @author Francesco
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
