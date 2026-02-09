package com.francesco.technicaltest_backend.dtos.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.francesco.technicaltest_backend.dtos.CreateUserDTO;
import com.francesco.technicaltest_backend.dtos.UserDTO;
import com.francesco.technicaltest_backend.entity.User;

/**
 * Interfaccia Mapper per lo User
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "createdAt", ignore = true)
    User toEntity(UserDTO userDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User toCreateUserFromDTO(CreateUserDTO createUserDTO);

    UserDTO toUserDTO(User user);

    List<UserDTO> toUserDTOList(List<User> users);
}
