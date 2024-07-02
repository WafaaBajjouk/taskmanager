package com.wbajjouk.taskmanager;

import com.wbajjouk.taskmanager.projectmanagement.ProjectRequest;
import com.wbajjouk.taskmanager.projectmanagement.ProjectResponse;
import com.wbajjouk.taskmanager.projectmanagement.ProjectService;
import com.wbajjouk.taskmanager.taskmanagement.TaskRequest;
import com.wbajjouk.taskmanager.taskmanagement.TaskResponse;
import com.wbajjouk.taskmanager.taskmanagement.TaskService;
import com.wbajjouk.taskmanager.usermanagement.UserRequest;
import com.wbajjouk.taskmanager.usermanagement.UserResponse;
import com.wbajjouk.taskmanager.usermanagement.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TaskmanagerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class UserTest {

    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private TaskService taskService;

    @Test
    public void testSaveUser() {
        UserRequest request = new UserRequest();

        request.username = "Test User X";
        request.email = String.format("email%d@gmail.com", TestUserId.nextId());
        request.role = "ADMIN";
        request.passwordHash = "PasswordHash";

        UserResponse response = userService.saveUser(request);
        List<UserResponse> allUsers = userService.getAllUsers();
        Optional<UserResponse> gotten = allUsers.stream()
                .filter(u -> u.userId == response.userId)
                .findFirst();

        Assert.assertTrue(gotten.isPresent());
        Assert.assertEquals("Test User X", gotten.get().username);
    }

    public static class TestUserId {
        private static final Random RANDOM = new Random();

        public static long nextId() {
            return RANDOM.nextLong();
        }
    }

    @Test
    public void testDeleteUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.username = "Test User";
        userRequest.email = String.format("email%d@gmail.com", TestUserId.nextId());
        userRequest.role = "ADMIN";
        userRequest.passwordHash = "PasswordHash";
        UserResponse userResponse = userService.saveUser(userRequest);

        ProjectResponse sampleProject = createSampleProject();
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.taskName = "Test Task";
        taskRequest.description = "Test Description";
        taskRequest.priority = "urgent";
        taskRequest.status = "to-do";
        taskRequest.projectId = sampleProject.id;
        TaskResponse taskResponse = taskService.saveTask(taskRequest);

        userService.deleteUser(userResponse.userId);
        // Verify the user has been deleted
        Optional<UserResponse> deletedUser = userService.getUserById(userResponse.userId);
        assertFalse(deletedUser.isPresent());
    }

    private ProjectResponse createSampleProject() {
        ProjectRequest requestProject = new ProjectRequest();
        requestProject.isCompleted = false;
        requestProject.description = "My test project";
        requestProject.startDate = LocalDate.EPOCH;
        requestProject.endDate = LocalDate.EPOCH;
        requestProject.projectName = "Project Sample from User Testing";
        return projectService.saveProject(requestProject);
    }

    @Test
    public void testUpdateUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.username = "Original User";
        userRequest.email = String.format("email%d@gmail.com", TestUserId.nextId());
        userRequest.role = "ADMIN";
        userRequest.passwordHash = "PasswordHash";
        UserResponse userResponse = userService.saveUser(userRequest);

        userRequest.username = "Updated User";
        String expectedEmail = String.format("email%d@gmail.com", TestUserId.nextId());
        userRequest.email = expectedEmail;

        UserResponse updatedUserResponse = userService.updateUser(userRequest, userResponse.userId);

        Optional<UserResponse> retrievedUser = userService.getUserById(userResponse.userId);

        assertTrue(retrievedUser.isPresent());
        Assertions.assertEquals("Updated User", retrievedUser.get().username);
    }

    @Test
    public void testGetAllUsers() {
        // Create multiple users
        UserRequest userRequest1 = new UserRequest();
        userRequest1.username = "User One";
        userRequest1.email = String.format("email%d@gmail.com", TestUserId.nextId());
        userRequest1.role = "ADMIN";
        userRequest1.passwordHash = "PasswordHash1";
        userService.saveUser(userRequest1);

        UserRequest userRequest2 = new UserRequest();
        userRequest2.username = "User Two";
        userRequest2.email = String.format("email%d@gmail.com", TestUserId.nextId());
        userRequest2.role = "USER";
        userRequest2.passwordHash = "PasswordHash2";
        userService.saveUser(userRequest2);

        List<UserResponse> allUsers = userService.getAllUsers();
        Assertions.assertTrue(allUsers.size() >= 2);
        Assertions.assertTrue(allUsers.stream().anyMatch(user -> "User One".equals(user.username)));
        Assertions.assertTrue(allUsers.stream().anyMatch(user -> "User Two".equals(user.username)));
    }

    @Test
    public void testGetUserById() {
        UserRequest userRequest = new UserRequest();
        userRequest.username = "Sample User";
        userRequest.email = String.format("email%d@gmail.com", TestUserId.nextId());
        userRequest.role = "USER";
        userRequest.passwordHash = "PasswordHash";
        UserResponse savedUser = userService.saveUser(userRequest);

        Optional<UserResponse> retrievedUser = userService.getUserById(savedUser.userId);
        assertTrue(retrievedUser.isPresent());
        Assertions.assertEquals("Sample User", retrievedUser.get().username);
        Assertions.assertEquals(userRequest.email, retrievedUser.get().email);
    }

    @Test
    public void testAssignRole() {
        UserRequest userRequest = new UserRequest();
        userRequest.username = "Role Test User";
        userRequest.email = String.format("email%d@gmail.com", TestUserId.nextId());
        userRequest.role = "USER";
        userRequest.passwordHash = "PasswordHash";
        UserResponse savedUser = userService.saveUser(userRequest);

        String newRole = "ADMIN";
        UserResponse updatedUser = userService.assignRole(savedUser.userId, newRole);

        Optional<UserResponse> retrievedUser = userService.getUserById(savedUser.userId);

        assertTrue(retrievedUser.isPresent());
        Assertions.assertEquals(newRole, retrievedUser.get().role);
    }
}
