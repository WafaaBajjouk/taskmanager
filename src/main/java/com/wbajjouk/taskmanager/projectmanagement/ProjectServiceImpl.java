package com.wbajjouk.taskmanager.projectmanagement;

import com.wbajjouk.taskmanager.taskmanagement.TaskRepository;
import com.wbajjouk.taskmanager.taskmanagement.TaskService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final TaskRepository taskRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, TaskService taskService, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }


    // set the progress value = avg of completed task within the project
    @Scheduled(fixedDelayString = "${progress.updateInterval}")
    public synchronized void updateProgressForAllProjects() {
        System.out.println("Yay, updating progress");
        List<Project> projects = projectRepository.findAll();
        for (Project project : projects) {
            int progress = calculateProjectProgress(project.getId());
            project.setProgress(progress);
            // unnecessary within the transaction
            // projectRepository.save(project);
        }
    }

    private int calculateProjectProgress(long projectId) {
        int allTasks = taskRepository.countByProjectId(projectId);
        int completedTasks = taskRepository.countByProjectIdAndStatus(projectId, "completed");
        if (allTasks == 0) {
            return 0;
        }

        return (int) ((double) completedTasks / allTasks * 100);
    }

    @Override
    public ProjectResponse saveProject(ProjectRequest projectRequest) {
        Project project = projectmapper.projectRequestToProject(projectRequest);
        Project savedProject = projectRepository.save(project);
//        this.initializeProgress();
        return projectmapper.projectToProjectResponse(savedProject);
    }

    @Override
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream().map(projectmapper::projectToProjectResponse).collect(Collectors.toList());
    }

    @Override
    public Optional<ProjectResponse> getProjectById(long id) {
        return projectRepository.findById(id).map(projectmapper::projectToProjectResponse);
    }


    // Option 1a: recalculate progress each time we fetch the project:
    // PROs: simple and robust (never out-of-alignment)
    // CONs: inefficient
    //    public Optional<ProjectResponse> getProjectById(Long id) {
    //        return projectRepository.findById(id).map(p -> {
    //            int progress = calculateProjectProgress(id);
    //            return projectmapper.projectToProjectResponse(p, progress);
    //        });
    //    }

    // Option 1b: calculated column on JPA entity
    // PROs: efficient
    // CONs: difficult to test, bit more complicated


    // Option X: custom native SQL query

    // Option 2: we can use a trigger on the database
    // PROs: efficient
    // CONs: difficult to test, logic is divided between java and db, there is potential for misalignment because the value is denormalized

    // Option 3a: we can periodically recalculate the progress
    // Option 3b: we can cache the progress calculation
    // PROs: reasonably efficient
    // CONs: much more complicated (lots of different parts, spring config, etc), even worse potential for misalignment (as the denormalization is in memory and harder to detect)
    // Option 3b:
    //    @Cached("progress") // supports invalidation
    //    public int calculateProjectProgress(long projectId) {
    //        Optional<List<TaskResponse>> tasks = taskService.getTasksByProjectId(projectId);
    //        Optional<List<TaskResponse>> completedTasksList= taskService.getCompletedTasksByProjectId(projectId);
    //
    //        if (tasks.isEmpty()) {
    //            return 0;
    //        }
    //
    //        List<TaskResponse> allTasks = tasks.get();
    //        List<TaskResponse> completedTasks = completedTasksList.orElse(List.of());
    //
    //        return (int) ((double) completedTasks.size() / allTasks.size() * 100);
    //    }
    //   @InvalidatesCache("progress")
    //   public void markAsCompleted()


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
    public ProjectResponse updateProject(long id, ProjectRequest projectRequest) {
            Project project = projectmapper.projectRequestToProject(projectRequest);
            project.setId(id);
            Project updatedProject = projectRepository.save(project);
            return projectmapper.projectToProjectResponse(updatedProject);
    }

    @Override
    public ProjectResponse maskAsCompleted(Long id) {
        Project project = projectRepository.findById(id)
                                              .orElseThrow(() -> new IllegalArgumentException("User not found"));

        project.setCompleted(true);
        projectRepository.save(project);
        return projectmapper.projectToProjectResponse(project);

    }


}