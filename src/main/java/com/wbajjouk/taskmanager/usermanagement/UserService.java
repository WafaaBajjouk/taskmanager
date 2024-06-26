package com.wbajjouk.taskmanager.usermanagement;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponse> getAllUsers();

    Optional<UserResponse> getUserById(Long id);

    UserResponse saveUser(UserRequest userRqt);

    void deleteUser(Long id);

    ResponseEntity<UserResponse> updateUser(UserRequest userrqt, Long id);

    ResponseEntity<UserResponse> assignRole(Long id, String role);
}
