package com.wbajjouk.taskmanager.assignmentmanagement;

import com.wbajjouk.taskmanager.taskmanagement.Task;
import com.wbajjouk.taskmanager.taskmanagement.TaskRepository;
import com.wbajjouk.taskmanager.usermanagement.User;
import com.wbajjouk.taskmanager.usermanagement.UserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper mapper = Mappers.getMapper(AssignmentMapper.class);
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.assignmentRepository = assignmentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<AssignmentResponse> getAllAssignments() {
        return assignmentRepository.findAll().stream().map(mapper::assignmentToAssignmentResponse).collect(Collectors.toList());
    }

    @Override
    public AssignmentResponse saveAssignment(AssignmentRequest assignmentRequest) {
        TaskAssignment assignment = mapper.assignmentRequestToAssignment(assignmentRequest);

        Task task = taskRepository.findById(assignmentRequest.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        User user = userRepository.findById(assignmentRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        assignment.setTask(task);
        assignment.setUser(user);
        TaskAssignment savedAssignment = assignmentRepository.save(assignment);
        return mapper.assignmentToAssignmentResponse(savedAssignment);
    }

    @Override
    public Optional<AssignmentResponse> getAssignmentById(Long id) {
        return assignmentRepository.findById(id).map(mapper::assignmentToAssignmentResponse);
    }


    @Override
    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }


    @Override
    public AssignmentResponse updateAssignment(long id, AssignmentRequest assignmentRequest) {

        TaskAssignment assignment = mapper.assignmentRequestToAssignment(assignmentRequest);
        assignment.setAssignmentId(id);
        TaskAssignment entity = assignmentRepository.save(assignment);

        return mapper.assignmentToAssignmentResponse(entity);

    }

    @Override
    public Optional<List<AssignmentResponse>> getAssignmentByTaskId(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        return Optional.of(assignmentRepository.findByTask(task).stream().map(mapper::assignmentToAssignmentResponse).collect(Collectors.toList()));

    }
}
