package com.wbajjouk.taskmanager.model;

import jakarta.persistence.*;

import java.util.Date;

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
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    // Constructors, getters, and setters

    public Project() {
    }

    public Project(String projectName, String description, Date startDate, Date endDate) {
        this.projectName = projectName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }


}
