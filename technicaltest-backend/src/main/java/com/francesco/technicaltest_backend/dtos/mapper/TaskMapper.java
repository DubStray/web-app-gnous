package com.francesco.technicaltest_backend.dtos.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.francesco.technicaltest_backend.dtos.CreateTaskDTO;
import com.francesco.technicaltest_backend.dtos.TaskDTO;
import com.francesco.technicaltest_backend.dtos.UpdateTaskDTO;
import com.francesco.technicaltest_backend.dtos.UpdateTaskStatusDTO;
import com.francesco.technicaltest_backend.entity.Task;

/**
 * Interfaccia Mapper per l'entità Task
 */
@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    Task toEntity(CreateTaskDTO createTaskDTO);

    TaskDTO toTaskDTO(Task task);

    List<TaskDTO> toTaskDTOList(List<Task> tasks);

    void updateTaskFromDTO(UpdateTaskDTO updateTaskDTO, @MappingTarget Task task);

    void updateTaskStatusFromDTO(UpdateTaskStatusDTO updateTaskStatusDTO, @MappingTarget Task task);
}
