package com.wbajjouk.taskmanager.assignmentmanagement;

import com.wbajjouk.taskmanager.taskmanagement.TaskResponse;
import com.wbajjouk.taskmanager.usermanagement.UserResponse;

import java.util.Date;

public class AssignmentRequest {
    private Long userId;
    private Long taskId;
    private Date assignedDate;

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

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }
}
