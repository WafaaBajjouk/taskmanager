package com.wbajjouk.taskmanager.projectmanagement;

import com.wbajjouk.taskmanager.taskmanagement.Task;
import com.wbajjouk.taskmanager.taskmanagement.TaskResponse;
import com.wbajjouk.taskmanager.taskmanagement.TaskService;
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
    private final TaskService taskService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.taskService = taskService;
        this.initializeProgress();
    }

    private void initializeProgress() {
        List<Project> projects = projectRepository.findAll();
        for (Project project : projects) {
            int progress = calculateProjectProgress(project.getId());
            project.setProgress(progress);
            projectRepository.save(project);

        }
    }

    private int calculateProjectProgress(long projectId) {
        Optional<List<TaskResponse>> allTasksOptional = taskService.getTasksByProjectId(projectId);
        Optional<List<TaskResponse>> completedTasksOptional = taskService.getCompletedTasksByProjectId(projectId);

        if (allTasksOptional.isEmpty()) {
            return 0;
        }

        List<TaskResponse> allTasks = allTasksOptional.get();
        List<TaskResponse> completedTasks = completedTasksOptional.orElse(List.of());

        return (int) ((double) completedTasks.size() / allTasks.size() * 100);
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