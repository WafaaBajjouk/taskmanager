package com.wbajjouk.taskmanager.assignmentmanagement;

import java.util.List;
import java.util.Optional;

public interface AssignmentService {
    List<AssignmentResponse> getAllAssignments();

    AssignmentResponse saveAssignment(AssignmentRequest assignmentRequest);

    Optional<AssignmentResponse> getAssignmentById(Long id);

    void deleteAssignment(Long id);

    AssignmentResponse updateAssignment(long id, AssignmentRequest assignmentRequest);

    Optional<List<AssignmentResponse>> getAssignmentByTaskId(Long taskId);
}
