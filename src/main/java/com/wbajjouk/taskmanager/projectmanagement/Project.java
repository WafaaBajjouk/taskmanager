package com.wbajjouk.taskmanager.projectmanagement;
import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "Projects")
public class
Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "project_complete")
    private boolean isCompleted;

    @Column(name = "project_progress_percent")
    private int progress;

    // Constructors, getters, and setters


    public Project(Long id, String projectName, String description, LocalDate startDate, LocalDate endDate, boolean isCompleted, int progress) {
        this.id = id;
        this.projectName = projectName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isCompleted = isCompleted;
        this.progress = progress;

    }

    public Project() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
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
