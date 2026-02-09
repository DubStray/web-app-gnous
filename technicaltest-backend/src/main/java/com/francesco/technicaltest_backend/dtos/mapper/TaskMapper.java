package com.francesco.technicaltest_backend.dtos.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.francesco.technicaltest_backend.dtos.TaskDTO;
import com.francesco.technicaltest_backend.entity.Task;

/**
 * Interfaccia Mapper per l'entità Task
 */
@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "createdAt", ignore = true)
    Task toEntity(TaskDTO taskDTO);

    TaskDTO toTaskDTO(Task task);

    List<TaskDTO> toTaskDTOList(List<Task> tasks);

    void updateTaskFromDTO(TaskDTO taskDTO, @MappingTarget Task task);
}
