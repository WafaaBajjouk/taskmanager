package com.wbajjouk.taskmanager.assignmentmanagement;

import com.wbajjouk.taskmanager.taskmanagement.Task;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper mapper = Mappers.getMapper(AssignmentMapper.class);

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public AssignmentResponse saveAssignment(AssignmentRequest assignmentRequest) {
        TaskAssignment assignment = mapper.assignmentRequestToAssignment(assignmentRequest);
        TaskAssignment savedAssignment = assignmentRepository.save(assignment);
        return mapper.assignmentToAssignmentResponse(savedAssignment);
    }

    public Optional<AssignmentResponse> getAssignmentById(Long id) {
        return assignmentRepository.findById(id).map(mapper::assignmentToAssignmentResponse);
    }

    public List<AssignmentResponse> getAllAssignments() {
        return assignmentRepository.findAll().stream().map(mapper::assignmentToAssignmentResponse).collect(Collectors.toList());
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }


    public AssignmentResponse updateAssignment(long id, AssignmentRequest assignmentRequest) {

        TaskAssignment assignment = mapper.assignmentRequestToAssignment(assignmentRequest);
        assignment.setAssignmentId(id);
        TaskAssignment entity = assignmentRepository.save(assignment);

        return mapper.assignmentToAssignmentResponse(entity);

    }
}
