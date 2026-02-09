package com.francesco.technicaltest_backend.service;

import java.util.List;

import com.francesco.technicaltest_backend.dtos.UserDTO;

/**
 * Interfaccia Service per l'entità User.
 *
 */
public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO createUser(UserDTO userDTO);

    void deleteUser(Long id);
}
