package com.wbajjouk.taskmanager;

import com.wbajjouk.taskmanager.projectmanagement.ProjectRequest;
import com.wbajjouk.taskmanager.projectmanagement.ProjectResponse;
import com.wbajjouk.taskmanager.projectmanagement.ProjectService;
import com.wbajjouk.taskmanager.taskmanagement.TaskRequest;
import com.wbajjouk.taskmanager.taskmanagement.TaskService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TaskmanagerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class ProjectTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectService projectService;

    @Test
    public void canCreateProject() {
        ProjectRequest req = new ProjectRequest();
        req.isCompleted = false;
        req.description = "My test project";
        req.startDate = LocalDate.EPOCH;
        req.endDate = LocalDate.EPOCH;
        req.projectName = "Test Project #3";
        ProjectResponse resp = projectService.saveProject(req);
        List<ProjectResponse> allProjects = projectService.getAllProjects();
        Optional<ProjectResponse> gotten = allProjects.stream()
                .filter(p -> p.id.equals(resp.id))
                .findFirst();

        Assert.assertTrue(gotten.isPresent());
        Assert.assertEquals("Test Project #3", gotten.get().projectName);
    }

    @Test
    public void testUpdateProject() {
        ProjectRequest request = new ProjectRequest();
        request.isCompleted = false;
        request.description = "My test project";
        request.startDate = LocalDate.EPOCH;
        request.endDate = LocalDate.EPOCH;
        request.projectName = "Test Project #3";
        ProjectResponse project = projectService.saveProject(request);

        request.projectName = "Test Updated Project #4";

        ProjectResponse response = projectService.updateProject(project.id, request);

        Assert.assertEquals("Test Updated Project #4", response.projectName);
    }

    @Test
    public void testGetProjectById() {
        ProjectRequest request = new ProjectRequest();
        request.isCompleted = false;
        request.description = "anything";
        request.startDate = LocalDate.EPOCH;
        request.endDate = LocalDate.EPOCH;
        request.projectName = "GottenById";
        ProjectResponse project = projectService.saveProject(request);

        Optional<ProjectResponse> found = projectService.getProjectById(project.id);

        Assert.assertTrue(found.isPresent());
        Assert.assertEquals("GottenById", found.get().projectName);
    }

    @Test
    public void testProgressCalculation() throws InterruptedException {
        ProjectRequest request = new ProjectRequest();
        request.isCompleted = false;
        request.description = "anything";
        request.startDate = LocalDate.EPOCH;
        request.endDate = LocalDate.EPOCH;
        request.projectName = "ProgressTest";
        ProjectResponse project = projectService.saveProject(request);
        Assert.assertEquals(0, project.progress);

        TaskRequest requestTask = new TaskRequest();
        requestTask.taskName = "test";
        requestTask.description = "Description Test";
        requestTask.priority = "urgent";
        requestTask.status = "completed";
        requestTask.projectId = project.id;
        requestTask.dueDate = LocalDate.now();
        taskService.saveTask(requestTask);
        projectService.updateProgressForAllProjects();
        int progress = projectService.getProjectById(project.id).orElseThrow().progress;
        Assert.assertEquals(100, progress);
    }

    @Test
    public void testDeleteProject() {
        ProjectRequest request = new ProjectRequest();
        request.isCompleted = false;
        request.description = "Delete Test Project";
        request.startDate = LocalDate.EPOCH;
        request.endDate = LocalDate.EPOCH;
        request.projectName = "Delete Test Project";
        ProjectResponse project = projectService.saveProject(request);

        projectService.deleteProject(project.id);

        Optional<ProjectResponse> deletedProject = projectService.getProjectById(project.id);
        Assert.assertFalse(deletedProject.isPresent());
    }

    @Test
    public void testMaskAsCompleted() {
        ProjectRequest request = new ProjectRequest();
        request.isCompleted = false;
        request.description = "Incomplete Project";
        request.startDate = LocalDate.EPOCH;
        request.endDate = LocalDate.EPOCH;
        request.projectName = "Incomplete Project";
        ProjectResponse project = projectService.saveProject(request);

        ProjectResponse completedProject = projectService.maskAsCompleted(project.id);

//        Assert.assertFalse(!completedProject.isCompleted);
    }
}
