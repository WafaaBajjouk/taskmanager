package com.wbajjouk.taskmanager.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "task_name", nullable = false)
    private String taskName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "status", length = 50, nullable = false)
    private String status = "to-do";

    @Column(name = "priority", length = 50, nullable = false)
    private String priority = "medium";

    @ManyToOne
//    @ManyToOne(fetch = FetchType.LAZY) // THISSS tells JPA to load the associated entity lazily,Specifies that fetching of
//    // the associated the entity task should be deferred until it's explicitly accessed.
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;


    public Task() {
    }

    public Task(String taskName, String description, Date dueDate, String status, String priority, Project project) {
        this.taskName = taskName;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
        this.project = project;
    }


}
