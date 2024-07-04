package com.wbajjouk.taskmanager.usermanagement;
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
