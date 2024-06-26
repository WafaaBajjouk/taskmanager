package com.wbajjouk.taskmanager.usermanagement;

import com.wbajjouk.taskmanager.assignmentmanagement.AssignmentRepository;
import com.wbajjouk.taskmanager.assignmentmanagement.TaskAssignment;
import com.wbajjouk.taskmanager.taskmanagement.Task;
import com.wbajjouk.taskmanager.taskmanagement.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements UserService {
//    Note : INSTEAD OF AUTOWIRED THe OBJ , CONSTRUCTION DEPENDICIES IS BETTER .

    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final TaskRepository taskRepository;
    private final AssignmentRepository assignmentRepository;

    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository, AssignmentRepository assignmentRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::userToUserResponse).toList();
    }

    @Override
    public Optional<UserResponse> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);//represent a value that may be absent.
        // to avoid NullPointerException and allows  to express the fact that a return value might be missing.
        return user.map(userMapper::userToUserResponse);
    }

    @Override
    public UserResponse saveUser(UserRequest userRqt) {
        User user = userMapper.userRequestToUser(userRqt);
        User savedUser = userRepository.save(user);
        return userMapper.userToUserResponse(savedUser);
    }

    @Override
    public void deleteUser(Long id) {
        final User delendo = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User not found"));
        assignmentRepository.deleteByUser(delendo);
        userRepository.delete(delendo);
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(UserRequest userrqt, Long id) {
        User user = userMapper.userRequestToUser(userrqt);
        user.setUserId(id);
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(userMapper.userToUserResponse(savedUser));
    }

    @Override
    public ResponseEntity<UserResponse> assignRole(Long id, String role) {
        User user= userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User not found"));
        user.setRole(role);
        return ResponseEntity.ok(userMapper.userToUserResponse(userRepository.save(user)));
    }
}

