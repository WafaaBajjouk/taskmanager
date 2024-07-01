package com.wbajjouk.taskmanager;

import com.wbajjouk.taskmanager.assignmentmanagement.AssignmentRequest;
import com.wbajjouk.taskmanager.assignmentmanagement.AssignmentResponse;
import com.wbajjouk.taskmanager.assignmentmanagement.AssignmentService;
import com.wbajjouk.taskmanager.projectmanagement.ProjectRequest;
import com.wbajjouk.taskmanager.projectmanagement.ProjectResponse;
import com.wbajjouk.taskmanager.projectmanagement.ProjectService;
import com.wbajjouk.taskmanager.taskmanagement.TaskRequest;
import com.wbajjouk.taskmanager.taskmanagement.TaskResponse;
import com.wbajjouk.taskmanager.taskmanagement.TaskServiceImpl;
import com.wbajjouk.taskmanager.usermanagement.UserRequest;
import com.wbajjouk.taskmanager.usermanagement.UserResponse;
import com.wbajjouk.taskmanager.usermanagement.UserService;
import org.junit.Assert;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TaskmanagerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")

public class AssignmentTest {

    @Autowired
    AssignmentService  assignmentService;
    @Autowired
    UserService userService;
    @Autowired
    private TaskServiceImpl taskService;
    @Autowired
    private ProjectService projectService;

    @Test
    public void testAssignmentUpdate() {
        AssignmentRequest request = new AssignmentRequest();
        UserResponse user = createSampleUser();
        TaskResponse task = createSampleTask();

        request.setAssignedDate(LocalDate.EPOCH);
        request.setUserId(user.userId);
        request.setTaskId(task.getTaskId());
        AssignmentResponse assignment = assignmentService.saveAssignment(request);

        request.setAssignedDate(LocalDate.now());


        AssignmentResponse response = assignmentService.updateAssignment(assignment.getAssignmentId(), request);
        Assert.assertEquals(LocalDate.now(), response.getAssignedDate());
    }

    private UserResponse createSampleUser() {
        UserRequest request = new UserRequest();
        request.setUsername("Test User X");
        request.setEmail(String.format("email%d@gmail.com", UserTest.TestUserId.nextId()));
        request.setRole("ADMIN");
        request.setPasswordHash("PasswordHash");
        return userService.saveUser(request);
    }


    private TaskResponse createSampleTask() {
        TaskRequest requestTask = new TaskRequest();
        requestTask.setTaskName("test");
        requestTask.setDescription("Description Test");
        requestTask.setPriority("urgent");
        requestTask.setStatus("to-do");

        ProjectResponse project = createSampleProject();
        requestTask.setProjectId(project.getId());
        return taskService.saveTask(requestTask);
    }

    private ProjectResponse createSampleProject() {
        ProjectRequest requestProject = new ProjectRequest();
        requestProject.setCompleted(false);
        requestProject.setDescription("My test project");
        requestProject.setStartDate(LocalDate.EPOCH);
        requestProject.setEndDate(LocalDate.EPOCH);
        requestProject.setProjectName("Test Project #3");
        return projectService.saveProject(requestProject);
    }

    @Test
    public void testSaveAssignment() {
        UserResponse user = createSampleUser();
        TaskResponse task = createSampleTask();

        AssignmentRequest request = new AssignmentRequest();
        request.setAssignedDate(LocalDate.EPOCH);
        request.setUserId(user.userId);
        request.setTaskId(task.getTaskId());
        AssignmentResponse assignment = assignmentService.saveAssignment(request);

        // Verify the assignment
        Assert.assertNotNull(assignment);
        Assert.assertEquals(LocalDate.EPOCH, assignment.getAssignedDate());
        Assert.assertEquals(user.userId, assignment.getUser().userId);
        Assert.assertEquals(task.getTaskId(), assignment.getTask().getTaskId());
    }

    @Test
    public void testDeleteAssignment() {
        UserResponse user = createSampleUser();
        TaskResponse task = createSampleTask();

        AssignmentRequest request = new AssignmentRequest();
        request.setAssignedDate(LocalDate.EPOCH);
        request.setUserId(user.userId);
        request.setTaskId(task.getTaskId());

        AssignmentResponse assignment = assignmentService.saveAssignment(request);

        assignmentService.deleteAssignment(assignment.getAssignmentId());
        Optional<AssignmentResponse> deletedAssignment = assignmentService.getAssignmentById(assignment.getAssignmentId());
        Assert.assertFalse(deletedAssignment.isPresent());

    }




}
