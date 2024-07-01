package com.wbajjouk.taskmanager.assignmentmanagement;

import java.time.LocalDate;
import java.util.Date;

public class AssignmentRequest {
    private Long userId;
    private Long taskId;
    private LocalDate assignedDate;

    public AssignmentRequest(Long userId, Long taskId, LocalDate assignedDate) {
        this.userId = userId;
        this.taskId = taskId;
        this.assignedDate = assignedDate;
    }

    public AssignmentRequest() {

    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }
}
