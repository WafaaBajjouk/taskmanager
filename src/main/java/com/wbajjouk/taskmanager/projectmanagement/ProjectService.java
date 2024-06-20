package com.wbajjouk.taskmanager.projectmanagement;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper mapper = Mappers.getMapper(ProjectMapper.class);

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectResponse saveProject(ProjectRequest projectRequest) {
        Project project = mapper.projectRequestToProject(projectRequest);
        Project savedProject = projectRepository.save(project);
        return mapper.projectToProjectResponse(savedProject);
    }

    public Optional<ProjectResponse> getProjectById(Long id) {
        return projectRepository.findById(id).map(mapper::projectToProjectResponse);
    }

    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream().map(mapper::projectToProjectResponse).collect(Collectors.toList());
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
