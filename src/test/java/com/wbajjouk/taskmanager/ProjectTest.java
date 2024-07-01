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
        req.setCompleted(false);
        req.setDescription("My test project");
        req.setStartDate(LocalDate.EPOCH);
        req.setEndDate(LocalDate.EPOCH);
        req.setProjectName("Test Project #3");
        ProjectResponse resp = projectService.saveProject(req);
        List<ProjectResponse> allProjects = projectService.getAllProjects();
        Optional<ProjectResponse> gotten = allProjects.stream()
                .filter(p -> p.getId().equals(resp.getId()))
                .findFirst();

        Assert.assertTrue(gotten.isPresent());
        Assert.assertEquals("Test Project #3", gotten.get().getProjectName());
    }

    @Test
    public void testUpdateProject() {
        ProjectRequest request = new ProjectRequest();
        request.setCompleted(false);
        request.setDescription("My test project");
        request.setStartDate(LocalDate.EPOCH);
        request.setEndDate(LocalDate.EPOCH);
        request.setProjectName("Test Project #3");
        ProjectResponse project = projectService.saveProject(request);

        request.setProjectName("Test Updated Project #4");

        ProjectResponse response = projectService.updateProject(project.getId(),request);

        Assert.assertEquals("Test Updated Project #4", response.getProjectName());
    }

    @Test
    public void testGetProjectById(){
        ProjectRequest request = new ProjectRequest();
        request.setCompleted(false);
        request.setDescription("anything");
        request.setStartDate(LocalDate.EPOCH);
        request.setEndDate(LocalDate.EPOCH);
        request.setProjectName("GottenById");
        ProjectResponse project = projectService.saveProject(request);

        Optional<ProjectResponse> found = projectService.getProjectById(project.getId());

        Assert.assertTrue(found.isPresent());
        Assert.assertEquals("GottenById", found.get().getProjectName());
    }


    @Test
    public void testProgressCalculation() throws InterruptedException {
        ProjectRequest request = new ProjectRequest();
        request.setCompleted(false);
        request.setDescription("anything");
        request.setStartDate(LocalDate.EPOCH);
        request.setEndDate(LocalDate.EPOCH);
        request.setProjectName("ProgressTest");
        ProjectResponse project = projectService.saveProject(request);
        Assert.assertEquals(0, project.getProgress());

        TaskRequest requestTask = new TaskRequest();
        requestTask.setTaskName("test");
        requestTask.setDescription("Description Test");
        requestTask.setPriority("urgent");
        requestTask.setStatus("completed");
        requestTask.setProjectId(project.getId());
        requestTask.setDueDate(LocalDate.now());
        taskService.saveTask(requestTask);
        projectService.updateProgressForAllProjects();
        int progress = projectService.getProjectById(project.getId()).orElseThrow().getProgress();
        Assert.assertEquals(100, progress);


    }


    @Test
    public void testDeleteProject() {
        ProjectRequest request = new ProjectRequest();
        request.setCompleted(false);
        request.setDescription("Delete Test Project");
        request.setStartDate(LocalDate.EPOCH);
        request.setEndDate(LocalDate.EPOCH);
        request.setProjectName("Delete Test Project");
        ProjectResponse project = projectService.saveProject(request);

        projectService.deleteProject(project.getId());

        Optional<ProjectResponse> deletedProject = projectService.getProjectById(project.getId());
        Assert.assertFalse(deletedProject.isPresent());
    }



    @Test
    public void testMaskAsCompleted() {
        ProjectRequest request = new ProjectRequest();
        request.setCompleted(false);
        request.setDescription("Incomplete Project");
        request.setStartDate(LocalDate.EPOCH);
        request.setEndDate(LocalDate.EPOCH);
        request.setProjectName("Incomplete Project");
        ProjectResponse project = projectService.saveProject(request);

        ProjectResponse completedProject = projectService.maskAsCompleted(project.getId());

        Assert.assertTrue(completedProject.isCompleted());
    }
//    notes:
//    keep test independent from the rest of your tests
//    go over specification
}
