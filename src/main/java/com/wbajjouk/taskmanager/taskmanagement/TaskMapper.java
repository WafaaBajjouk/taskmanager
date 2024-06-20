package com.wbajjouk.taskmanager.taskmanagement;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskResponse taskToTaskResponse(Task task);

    Task taskRequestToTask(TaskRequest taskRequest);
}
