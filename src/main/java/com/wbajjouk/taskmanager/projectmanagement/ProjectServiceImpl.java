package com.wbajjouk.taskmanager.projectmanagement;

import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectmapper = Mappers.getMapper(ProjectMapper.class);

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectResponse saveProject(ProjectRequest projectRequest) {
        Project project = projectmapper.projectRequestToProject(projectRequest);
        Project savedProject = projectRepository.save(project);
        return projectmapper.projectToProjectResponse(savedProject);
    }

    @Override
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream().map(projectmapper::projectToProjectResponse).collect(Collectors.toList());
    }

    @Override
    public Optional<ProjectResponse> getProjectById(Long id) {
        return projectRepository.findById(id).map(projectmapper::projectToProjectResponse);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<ProjectResponse> getCompletedProjects() {
        return projectRepository.findByIsCompletedTrue().stream()
                .map(projectmapper::projectToProjectResponse).collect(Collectors.toList());

    }

    @Override
    public ResponseEntity<ProjectResponse> updateProject(Long id, ProjectRequest projectRequest) {
            Project project = projectmapper.projectRequestToProject(projectRequest);
            project.setId(id);
            Project updatedProject = projectRepository.save(project);
            return ResponseEntity.ok(projectmapper.projectToProjectResponse(updatedProject));
    }

    @Override
    public ResponseEntity<ProjectResponse> maskAsCompleted(Long id) {
        Project project = projectRepository.findById(id)
                                              .orElseThrow(() -> new IllegalArgumentException("User not found"));

        project.setCompleted(true);
        projectRepository.save(project);
        return ResponseEntity.ok(projectmapper.projectToProjectResponse(project));

    }


}