package com.wbajjouk.taskmanager.projectmanagement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest projectRequest) {
        ProjectResponse savedProject = projectService.saveProject(projectRequest);
        return ResponseEntity.ok(savedProject);
    }

    @GetMapping
    public List<ProjectResponse> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        Optional<ProjectResponse> projectResponse = projectService.getProjectById(id);
        return projectResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/completed")
   public List<ProjectResponse> getCompletedProjects() {
        return projectService.getCompletedProjects();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @RequestBody ProjectRequest projectRequest) {
        return projectService.updateProject(id,projectRequest);
    }

    @PutMapping("/completed/{id}")
    public ResponseEntity<ProjectResponse> markCompletedProject(@PathVariable Long id) {
        return projectService.maskAsCompleted(id);

    }
}
