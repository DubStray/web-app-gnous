package com.francesco.technicaltest_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francesco.technicaltest_backend.entity.User;

/**
 * Repository per l'entitá User.
 * Spring Data rileva la repo e la registra come Bean
 * 
 * @author Francesco
 */
public interface UserRepository extends JpaRepository<User, Long> {

    // Metodo per trovare un utente tramite username
    // Optional viene usato per indicare che l'utente potrebbe non esistere
    // in caso contrario si riceverebbe un NullPointerException
    Optional<User> findByUsername(String username);
}
