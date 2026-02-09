package com.francesco.technicaltest_backend.dtos;

import java.util.List;

import lombok.Data;

/**
 * Data Transfer Object per l'entitá User.
 * 
 * @author Francesco
 */
@Data
public class UserDTO {
    private Long id;
    private String username;
    private int wallet;
    private List<TaskDTO> tasks;
}
