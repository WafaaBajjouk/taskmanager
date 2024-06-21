package com.wbajjouk.taskmanager.projectmanagement;

import com.wbajjouk.taskmanager.assignmentmanagement.AssignmentRequest;
import com.wbajjouk.taskmanager.assignmentmanagement.TaskAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ProjectResponse projectToProjectResponse(Project project);

    Project projectRequestToProject(ProjectRequest projectRequest);
    ProjectResponse validateProjectRequest(ProjectRequest projectRequest);
}
