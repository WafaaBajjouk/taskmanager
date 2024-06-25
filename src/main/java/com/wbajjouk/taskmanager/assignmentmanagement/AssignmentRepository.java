package com.wbajjouk.taskmanager.assignmentmanagement;

import com.wbajjouk.taskmanager.taskmanagement.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<TaskAssignment, Long> {
    List<TaskAssignment> findByTask(Task task);
}
