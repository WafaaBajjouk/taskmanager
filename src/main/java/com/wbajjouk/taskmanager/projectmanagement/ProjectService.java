package com.wbajjouk.taskmanager.projectmanagement;

import com.wbajjouk.taskmanager.usermanagement.User;
import com.wbajjouk.taskmanager.usermanagement.UserMapper;
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
    private final ProjectMapper projectmapper = Mappers.getMapper(ProjectMapper.class);

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectResponse saveProject(ProjectRequest projectRequest) {
        Project project = projectmapper.projectRequestToProject(projectRequest);
        Project savedProject = projectRepository.save(project);
        return projectmapper.projectToProjectResponse(savedProject);
    }

    public Optional<ProjectResponse> getProjectById(Long id) {
        return projectRepository.findById(id).map(projectmapper::projectToProjectResponse);
    }

    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream().map(projectmapper::projectToProjectResponse).collect(Collectors.toList());
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public List<ProjectResponse> getCompletedProjects() {
        return projectRepository.findByProjectCompleteTrue().stream()
                .map(projectmapper::projectToProjectResponse).collect(Collectors.toList());
    }


}
