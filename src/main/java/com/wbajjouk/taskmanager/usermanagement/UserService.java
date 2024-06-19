package com.wbajjouk.taskmanager.usermanagement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserService {
//    Note : INSTEAD OF AUTOWIRED THe OBJ , CONSTRUCTION DEPENDICIES IS BETTER .

    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.usersToUserResponses(users);
    }

    public Optional<UserResponse> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);//represent a value that may be absent.
        // to avoid NullPointerException and allows  to express the fact that a return value might be missing.
        return user.map(userMapper::userToUserResponse);
    }

    public UserResponse saveUser(UserResponse userResponse) {
        User user = userMapper.userResponseToUser(userResponse);
        User savedUser = userRepository.save(user);
        return userMapper.userToUserResponse(savedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

