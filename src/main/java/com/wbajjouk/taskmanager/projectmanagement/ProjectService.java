package com.wbajjouk.taskmanager.projectmanagement;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    ProjectResponse saveProject(ProjectRequest projectRequest);

    Optional<ProjectResponse> getProjectById(Long id);

    List<ProjectResponse> getAllProjects();

    void deleteProject(Long id);

    List<ProjectResponse> getCompletedProjects();

    ResponseEntity<ProjectResponse> updateProject(Long id, ProjectRequest projectRequest);

    ResponseEntity<ProjectResponse> maskAsCompleted(Long id);
}
