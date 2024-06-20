package com.wbajjouk.taskmanager.projectmanagement;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ProjectResponse projectToProjectResponse(Project project);

    Project projectRequestToProject(ProjectRequest projectRequest);
}
