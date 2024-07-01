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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;


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


//    private UserRequest request = new UserRequest();

//    @BeforeEach
//    void setUp(){
//        request.setUsername("Test User");
//        request.setEmail("testemail@gmail.com");
//        request.setRole("ADMIN");
//    }

    @Test
    public void testSaveUser() {
        UserRequest request = new UserRequest();

        request.setUsername("Test User X");
        request.setEmail(String.format("email%d@gmail.com", TestUserId.nextId()));
        request.setRole("ADMIN");
        request.setPasswordHash("PasswordHash");

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
        userRequest.setUsername("Test User");
        userRequest.setEmail(String.format("email%d@gmail.com", TestUserId.nextId()));
        userRequest.setRole("ADMIN");
        userRequest.setPasswordHash("PasswordHash");
        UserResponse userResponse = userService.saveUser(userRequest);

        ProjectResponse sampleProject = createSampleProject();
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTaskName("Test Task");
        taskRequest.setDescription("Test Description");
        taskRequest.setPriority("urgent");
        taskRequest.setStatus("to-do");
        taskRequest.setProjectId(sampleProject.getId());
        TaskResponse taskResponse = taskService.saveTask(taskRequest);

        userService.deleteUser(userResponse.userId);
        // Verify the user has been deleted
        Optional<UserResponse> deletedUser = userService.getUserById(userResponse.userId);
        assertFalse(deletedUser.isPresent());
    }

    private ProjectResponse createSampleProject() {
        ProjectRequest requestProject = new ProjectRequest();
        requestProject.setCompleted(false);
        requestProject.setDescription("My test project");
        requestProject.setStartDate(LocalDate.EPOCH);
        requestProject.setEndDate(LocalDate.EPOCH);
        requestProject.setProjectName("Project Sample from User Testing");
        return projectService.saveProject(requestProject);
    }

    @Test
    public void testUpdateUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("Original User");
        userRequest.setEmail(String.format("email%d@gmail.com", TestUserId.nextId()));
        userRequest.setRole("ADMIN");
        userRequest.setPasswordHash("PasswordHash");
        UserResponse userResponse = userService.saveUser(userRequest);

        userRequest.setUsername("Updated User");
        String expectedEmail = String.format("email%d@gmail.com", TestUserId.nextId());
        userRequest.setEmail(expectedEmail);

        UserResponse updatedUserResponse = userService.updateUser(userRequest, userResponse.userId);

        Optional<UserResponse> retrievedUser = userService.getUserById(userResponse.userId);

        assertTrue(retrievedUser.isPresent());
        Assertions.assertEquals("Updated User", retrievedUser.get().username);
        }


    @Test
    public void testGetAllUsers() {
        // Create multiple users
        UserRequest userRequest1 = new UserRequest();
        userRequest1.setUsername("User One");
        userRequest1.setEmail(String.format("email%d@gmail.com", TestUserId.nextId()));
        userRequest1.setRole("ADMIN");
        userRequest1.setPasswordHash("PasswordHash1");
        userService.saveUser(userRequest1);

        UserRequest userRequest2 = new UserRequest();
        userRequest2.setUsername("User Two");
        userRequest2.setEmail(String.format("email%d@gmail.com", TestUserId.nextId()));
        userRequest2.setRole("USER");
        userRequest2.setPasswordHash("PasswordHash2");
        userService.saveUser(userRequest2);

        List<UserResponse> allUsers = userService.getAllUsers();
        Assertions.assertTrue(allUsers.size() >= 2);
        Assertions.assertTrue(allUsers.stream().anyMatch(user -> "User One".equals(user.username)));
        Assertions.assertTrue(allUsers.stream().anyMatch(user -> "User Two".equals(user.username)));
    }


    @Test
    public void testGetUserById() {
        // Create a sample user
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("Sample User");
        userRequest.setEmail(String.format("email%d@gmail.com", TestUserId.nextId()));
        userRequest.setRole("USER");
        userRequest.setPasswordHash("PasswordHash");
        UserResponse savedUser = userService.saveUser(userRequest);

        Optional<UserResponse> retrievedUser = userService.getUserById(savedUser.userId);
        assertTrue(retrievedUser.isPresent());
        Assertions.assertEquals("Sample User", retrievedUser.get().username);
        Assertions.assertEquals(userRequest.getEmail(), retrievedUser.get().email);
    }

    @Test
    public void testAssignRole() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("Role Test User");
        userRequest.setEmail(String.format("email%d@gmail.com", TestUserId.nextId()));
        userRequest.setRole("USER");
        userRequest.setPasswordHash("PasswordHash");
        UserResponse savedUser = userService.saveUser(userRequest);

        String newRole = "ADMIN";
        UserResponse updatedUser = userService.assignRole(savedUser.userId, newRole);

        Optional<UserResponse> retrievedUser = userService.getUserById(savedUser.userId);

        assertTrue(retrievedUser.isPresent());
        Assertions.assertEquals(newRole, retrievedUser.get().role);
    }



}
