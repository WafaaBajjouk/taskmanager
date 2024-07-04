package com.wbajjouk.taskmanager.usermanagement;
import com.wbajjouk.taskmanager.assignmentmanagement.AssignmentRepository;
import com.wbajjouk.taskmanager.taskmanagement.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements UserService , UserDetailsService {
//    Note : INSTEAD OF AUTOWIRED THe OBJ , CONSTRUCTION DEPENDICIES IS BETTER .

    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final TaskRepository taskRepository;
    private final AssignmentRepository assignmentRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


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
        user.setPasswordHash(passwordEncoder.encode(userRqt.passwordHash));
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
    public UserResponse updateUser(UserRequest userrqt, Long id) {

        User user = userRepository.findById(id).orElseThrow();
        userMapper.userRequestToUser(userrqt,user);
        user.setPasswordHash(passwordEncoder.encode(userrqt.passwordHash));
        return userMapper.userToUserResponse(user);


    }

    @Override
    public UserResponse assignRole(Long id, String role) {
        User user= userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User not found"));
        user.setRole(role);
        return userMapper.userToUserResponse(userRepository.save(user));
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPasswordHash())
                .build();
    }


}



