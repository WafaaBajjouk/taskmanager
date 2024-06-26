package com.wbajjouk.taskmanager.taskmanagement;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    // Open transaction and jpa session
    TaskResponse saveTask(TaskRequest taskRequest);

    Optional<TaskResponse> getTaskById(Long id);

    List<TaskResponse> findTaskByPriority(String priority);

    List<TaskResponse> getAllTasks();

    void deleteTask(Long id);


    List<TaskResponse> getTasksByProjectId(long projectId);

    List<TaskResponse> findTasksByStatus(String status);

    TaskResponse markTaskAsCompleted(long id);

    List<TaskResponse> getCompletedTasksByProjectId(long id);
}
