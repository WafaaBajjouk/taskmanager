package com.wbajjouk.taskmanager.taskmanagement;

import com.wbajjouk.taskmanager.projectmanagement.Project;
import com.wbajjouk.taskmanager.projectmanagement.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {


    @Mapping(source = "project.id", target = "project_id")
    TaskResponse taskToTaskResponse(Task task);

    Task taskRequestToTask(TaskRequest taskRequest);


}
