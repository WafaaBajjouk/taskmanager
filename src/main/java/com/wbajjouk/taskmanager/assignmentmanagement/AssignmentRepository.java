package com.wbajjouk.taskmanager.assignmentmanagement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<TaskAssignment, Long> {
}
