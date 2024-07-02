package com.wbajjouk.taskmanager.assignmentmanagement;

import com.wbajjouk.taskmanager.taskmanagement.TaskResponse;
import com.wbajjouk.taskmanager.usermanagement.UserResponse;

import java.time.LocalDate;
import java.util.Date;


public class AssignmentResponse {

    public Long assignmentId;
    public UserResponse user;
    public TaskResponse task;
    public LocalDate assignedDate;


}
