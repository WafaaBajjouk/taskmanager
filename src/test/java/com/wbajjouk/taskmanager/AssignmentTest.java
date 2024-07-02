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
    AssignmentService assignmentService;
    @Autowired
    UserService userService;
    @Autowired
    private TaskServiceImpl taskService;
    @Autowired
    private ProjectService projectService;

    @Test
    public void testAssignmentUpdate() {
        UserResponse user = createSampleUser();
        TaskResponse task = createSampleTask();

        AssignmentRequest request = new AssignmentRequest();
        request.assignedDate = LocalDate.EPOCH;
        request.userId = user.userId;
        request.taskId = task.taskId;
        AssignmentResponse assignment = assignmentService.saveAssignment(request);

        LocalDate now = LocalDate.now();
        request.assignedDate = now;
        AssignmentResponse response = assignmentService.updateAssignment(assignment.assignmentId, request);
        Assert.assertEquals(now, response.assignedDate);
    }

    private UserResponse createSampleUser() {
        UserRequest request = new UserRequest();
        request.username = "Test User X";
        request.email = String.format("email%d@gmail.com", UserTest.TestUserId.nextId());
        request.role = "ADMIN";
        request.passwordHash = "PasswordHash";
        return userService.saveUser(request);
    }

    private TaskResponse createSampleTask() {
        TaskRequest requestTask = new TaskRequest();
        requestTask.taskName = "test";
        requestTask.description = "Description Test";
        requestTask.priority = "urgent";
        requestTask.status = "to-do";

        ProjectResponse project = createSampleProject();
        requestTask.projectId = project.id;
        return taskService.saveTask(requestTask);
    }

    private ProjectResponse createSampleProject() {
        ProjectRequest requestProject = new ProjectRequest();
        requestProject.isCompleted= false;
        requestProject.description = "My test project";
        requestProject.startDate = LocalDate.EPOCH;
        requestProject.endDate = LocalDate.EPOCH;
        requestProject.projectName = "Test Project #3";
        return projectService.saveProject(requestProject);
    }

    @Test
    public void testSaveAssignment() {
        UserResponse user = createSampleUser();
        TaskResponse task = createSampleTask();

        AssignmentRequest request = new AssignmentRequest();
        request.assignedDate = LocalDate.EPOCH;
        request.userId = user.userId;
        request.taskId = task.taskId;
        AssignmentResponse assignment = assignmentService.saveAssignment(request);
        Assert.assertNotNull(assignment);
        Assert.assertEquals(LocalDate.EPOCH, assignment.assignedDate);
        Assert.assertEquals(user.userId, assignment.user.userId);
        Assert.assertEquals(task.taskId, assignment.task.taskId);
    }

    @Test
    public void testDeleteAssignment() {
        UserResponse user = createSampleUser();
        TaskResponse task = createSampleTask();

        AssignmentRequest request = new AssignmentRequest();
        request.assignedDate = LocalDate.EPOCH;
        request.userId = user.userId;
        request.taskId = task.taskId;

        AssignmentResponse assignment = assignmentService.saveAssignment(request);

        assignmentService.deleteAssignment(assignment.assignmentId);
        Optional<AssignmentResponse> deletedAssignment = assignmentService.getAssignmentById(assignment.assignmentId);
        Assert.assertFalse(deletedAssignment.isPresent());
    }
}
