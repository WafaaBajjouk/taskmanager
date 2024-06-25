package com.wbajjouk.taskmanager.projectmanagement;

import jakarta.persistence.*;

import java.time.*;
import java.time.temporal.TemporalAmount;

@Entity
@Table(name = "Projects")
public class
Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "project_complete")
    private boolean projectComplete;

    @Column(name = "project_progress_percent")
    private int project_progress;



    // Constructors, getters, and setters

    public Project() {
    }

    public Project(String projectName, String description, LocalDate startDate, LocalDate endDate,boolean projectComplete, int project_progress) {
        this.projectName = projectName;
        this.projectComplete=projectComplete;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.project_progress=project_progress;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

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

    public boolean isProjectComplete() {
        return projectComplete;
    }

    public void setProjectComplete(boolean projectComplete) {
        this.projectComplete = projectComplete;
    }

    public int getProject_progress() {
        return project_progress;
    }

    public void setProject_progress(int project_progress) {
        this.project_progress = project_progress;
    }

    public Duration calculateRemainingTime() {
        Instant begin = this.startDate.atTime(LocalTime.MIDNIGHT).atZone(ZoneId.of("Europe/Rome")).toInstant();
        Instant end = this.endDate.atTime(LocalTime.MIDNIGHT).atZone(ZoneId.of("Europe/Rome")).toInstant();
        Instant now = Instant.now();

        if (begin.isAfter(now)) {
            throw new RuntimeException("Not started yet");
        }
        if (end.isBefore(now)) {
            throw new RuntimeException("Already finished");
        }
        return Duration.between(now, end);
    }

}
