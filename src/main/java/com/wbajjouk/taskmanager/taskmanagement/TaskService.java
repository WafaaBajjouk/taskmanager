package com.wbajjouk.taskmanager.taskmanagement;


import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper mapper = Mappers.getMapper(TaskMapper.class);

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponse saveTask(Task task) {
        Task savedTask = taskRepository.save(task);
        return mapper.taskToTaskResponse(savedTask);
    }

    public Optional<TaskResponse> getTaskById(Long id) {
        return taskRepository.findById(id).map(mapper::taskToTaskResponse);
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream().map(mapper::taskToTaskResponse).collect(Collectors.toList());
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
