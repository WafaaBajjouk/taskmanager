package com.wbajjouk.taskmanager.taskmanagement;

import java.util.Date;

public class TaskResponse {


    public Long taskId;
    public String taskName;
    public String description;
    public Date dueDate;
    public String status;
    public String priority;
    public long projectId;

    public TaskResponse(Long taskId, String taskName, String description, Date dueDate, String status, String priority, long projectId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
        this.projectId = projectId;
    }
}
