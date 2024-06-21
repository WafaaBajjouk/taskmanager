package com.wbajjouk.taskmanager.projectmanagement;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

public class ProjectRequest {

    private String projectName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
