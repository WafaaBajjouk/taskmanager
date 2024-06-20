package com.wbajjouk.taskmanager.assignmentmanagement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssignmentMapper {

    AssignmentMapper INSTANCE = Mappers.getMapper(AssignmentMapper.class);

    AssignmentResponse assignmentToAssignmentResponse(TaskAssignment assignment);
    TaskAssignment assignmentRequestToAssignment(AssignmentRequest assignmentRequest);
}