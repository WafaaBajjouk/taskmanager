package com.wbajjouk.taskmanager.model;

import com.wbajjouk.taskmanager.model.Task;
import com.wbajjouk.taskmanager.model.User;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Task_Assignments")
public class TaskAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long assignmentId;

    @ManyToOne()
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "assigned_date")
    private Date assignedDate;


    public TaskAssignment() {
    }

    public TaskAssignment(Task task, User user, Date assignedDate) {
        this.task = task;
        this.user = user;
        this.assignedDate = assignedDate;
    }


}
