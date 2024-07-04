package com.wbajjouk.taskmanager.taskmanagement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface TaskMapper {


    @Mapping(source = "project.id", target = "projectId")
    TaskResponse taskToTaskResponse(Task task);

    Task taskRequestToTask(TaskRequest taskRequest);
    void taskRequestToTask(TaskRequest request, @MappingTarget Task task);
}
