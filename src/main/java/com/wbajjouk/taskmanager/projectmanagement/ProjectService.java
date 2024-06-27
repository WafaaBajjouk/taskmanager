package com.wbajjouk.taskmanager.projectmanagement;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    void updateProgressForAllProjects();

    ProjectResponse saveProject(ProjectRequest projectRequest);

    Optional<ProjectResponse> getProjectById(long id);

    List<ProjectResponse> getAllProjects();

    void deleteProject(Long id);

    List<ProjectResponse> getCompletedProjects();

    ProjectResponse updateProject(long id, ProjectRequest projectRequest);

    ProjectResponse maskAsCompleted(Long id);
}
