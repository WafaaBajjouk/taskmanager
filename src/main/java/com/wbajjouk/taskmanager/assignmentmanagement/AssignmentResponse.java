package com.wbajjouk.taskmanager.assignmentmanagement;

import com.wbajjouk.taskmanager.taskmanagement.TaskResponse;
import com.wbajjouk.taskmanager.usermanagement.UserResponse;

import java.time.LocalDate;
import java.util.Date;


public class AssignmentResponse {

    private Long assignmentId;
    private UserResponse user;
    private TaskResponse task;
    private LocalDate assignedDate;

    public AssignmentResponse(Long assignmentId, UserResponse user, TaskResponse task, LocalDate assignedDate) {
        this.assignmentId = assignmentId;
        this.user = user;
        this.task = task;
        this.assignedDate = assignedDate;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public TaskResponse getTask() {
        return task;
    }

    public void setTask(TaskResponse task) {
        this.task = task;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }
}
