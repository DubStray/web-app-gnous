package com.francesco.technicaltest_backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.francesco.technicaltest_backend.dtos.UserDTO;
import com.francesco.technicaltest_backend.dtos.mapper.UserMapper;
import com.francesco.technicaltest_backend.entity.User;
import com.francesco.technicaltest_backend.repository.UserRepository;
import com.francesco.technicaltest_backend.service.UserService;

import jakarta.transaction.Transactional;

/**
 * Classe di implementazione Service per l'entità User.
 * 
 * @Transactional per effetuare rollback sul DB in caso di errore
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepository, 
                            UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return this.userMapper.toUserDTOList(this.userRepository.findAll());
    }

    @Override
    public UserDTO getUserById(Long id) {
        return this.userRepository.findById(id)
            .map(this.userMapper::toUserDTO)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        // Controllo se l'utente esiste già
        if (this.userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        // Mappo l'utente in entità
        User user = this.userMapper.toEntity(userDTO);

        // Salvo l'utente
        return this.userMapper.toUserDTO(this.userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        this.userRepository.delete(user);
    }

}
