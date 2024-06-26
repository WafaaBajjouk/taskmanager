package com.wbajjouk.taskmanager.taskmanagement;

import java.util.Date;

public class TaskResponse {


    private Long taskId;
    private String taskName;
    private String description;
    private Date dueDate;
    private String status;
    private String priority;
    private int project_id;

    public TaskResponse(Long taskId, String taskName, String description, Date dueDate, String status, String priority) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;

    }


    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }
}
