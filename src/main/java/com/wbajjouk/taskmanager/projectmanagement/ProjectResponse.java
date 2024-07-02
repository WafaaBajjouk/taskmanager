package com.wbajjouk.taskmanager.projectmanagement;

import java.time.LocalDate;

public class ProjectResponse {

    public Long id;
    public String projectName;
    public String description;
    public LocalDate startDate;
    public LocalDate endDate;
    public boolean isCompleted;
    public int progress;


}
