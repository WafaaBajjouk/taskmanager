package com.wbajjouk.taskmanager.usermanagement;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")


public class UserController {

    private final UserService userService;
    private final AuthService authService;
    private final HttpSession httpSession;


    public UserController(UserService userService, AuthService authService, HttpSession httpSession) {
        this.userService = userService;
        this.authService = authService;
        this.httpSession = httpSession;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        // @PathVariable annotation is used to handle template variables in the request URI mapping, and set them as method parameters.
        Optional<UserResponse> userResponse = userService.getUserById(id);
        return userResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userrqt) {
        // @RequestBody annotation maps the HttpRequest body to a transfer or domain object, enabling automatic deserialization
        UserResponse savedUserResponse = userService.saveUser(userrqt);
        return ResponseEntity.ok(savedUserResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userrqt , @PathVariable Long id) {
        UserResponse updatedUserResponse = userService.updateUser(userrqt, id);
                return ResponseEntity.ok(updatedUserResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<UserResponse> assignRoleToUser(@PathVariable Long id, @RequestBody String role) {
           UserResponse assigned = userService.assignRole(id,role);
           return ResponseEntity.ok(assigned);
    }
//
//    @PostMapping("/api/login")
//    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
//        boolean isAuthenticated = authService.login(authRequest.username, authRequest.password);
//
//        if (!isAuthenticated) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        return ResponseEntity.ok().build();
//    }




}
