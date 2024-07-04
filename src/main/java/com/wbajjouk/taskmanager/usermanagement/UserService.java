package com.wbajjouk.taskmanager.usermanagement;

import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponse> getAllUsers();

    Optional<UserResponse> getUserById(Long id);

    UserResponse saveUser(UserRequest userRqt);

    void deleteUser(Long id);

    UserResponse updateUser(UserRequest userrqt, Long id);

    UserResponse assignRole(Long id, String role);


}
