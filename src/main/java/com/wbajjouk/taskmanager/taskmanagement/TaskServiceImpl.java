package com.wbajjouk.taskmanager.taskmanagement;

import com.wbajjouk.taskmanager.projectmanagement.Project;
import com.wbajjouk.taskmanager.projectmanagement.ProjectRepository;
import com.wbajjouk.taskmanager.usermanagement.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    private final TaskMapper mapper = Mappers.getMapper(TaskMapper.class);
    private final UserRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    // Open transaction and jpa session
    @Override
    public TaskResponse saveTask(TaskRequest taskRequest) {
        Project project = projectRepository.findById(taskRequest.projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        Task task = mapper.taskRequestToTask(taskRequest);
        task.setProject(project);
        Task savedTask = taskRepository.save(task);
        return mapper.taskToTaskResponse(savedTask);
    }
    // Commit transaction, or rollback on exception

    @Override
    public Optional<TaskResponse> getTaskById(Long id) {
        return taskRepository.findById(id).map(mapper::taskToTaskResponse);
    }

    @Override
    public List<TaskResponse> findTaskByPriority(String priority) {
        return taskRepository.findByPriority(priority).stream()
                .map(mapper::taskToTaskResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> getAllTasks() {

        return taskRepository.findAll().stream().map(mapper::taskToTaskResponse).collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> getTasksByProjectId(long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        return taskRepository.findByProject(project).stream().map(mapper::taskToTaskResponse).collect(Collectors.toList());
    }



    @Override
    public List<TaskResponse> findTasksByStatus(String status) {
        return taskRepository.findByStatus(status).stream()
                .map(mapper::taskToTaskResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse markTaskAsCompleted(long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        task.setStatus("completed");
//        Task savedTask = taskRepository.save(task);
        return mapper.taskToTaskResponse(task);
    }

    @Override
    public List<TaskResponse> getCompletedTasksByProjectId(long id) {
        List<Task> tasks = taskRepository.findByProjectIdAndStatus(id, "completed");
        List<TaskResponse> taskResponses = tasks.stream()
                .map(task -> new TaskResponse(task.getTaskId(), task.getTaskName(), task.getDescription(), task.getDueDate(), task.getStatus(), task.getPriority(), task.getProject().getId()))
                .collect(Collectors.toList());
        return taskResponses;

    }

    @Override
    public TaskResponse updateTask(Long id, TaskRequest request) {
//        Task task = mapper.taskRequestToTask(request);
//        task.setTaskId(id);
//        Task savedTask = taskRepository.save(task);
//        return mapper.taskToTaskResponse(savedTask);
        Task task = taskRepository.findById(id).orElseThrow();
        mapper.taskRequestToTask(request, task);
        return mapper.taskToTaskResponse(task);
    }


    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
