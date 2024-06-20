package com.wbajjouk.taskmanager.assignmentmanagement;

import com.wbajjouk.taskmanager.usermanagement.User;
import com.wbajjouk.taskmanager.taskmanagement.Task;
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
    //many assignment could be associated iwth one task
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

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }
}
