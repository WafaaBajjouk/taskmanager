package com.wbajjouk.taskmanager;

import com.wbajjouk.taskmanager.projectmanagement.ProjectRequest;
import com.wbajjouk.taskmanager.projectmanagement.ProjectResponse;
import com.wbajjouk.taskmanager.projectmanagement.ProjectService;
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
public class MyFirstTest {

    @Autowired
    private ProjectService projectService;

    @Test
    public void canCreateProject() {
        ProjectRequest req = new ProjectRequest();
        req.setCompleted(false);
        req.setDescription("My test project");
        req.setStartDate(LocalDate.EPOCH);
        req.setEndDate(LocalDate.EPOCH);
        req.setProjectName("Test Project #1");
        ProjectResponse resp = projectService.saveProject(req);
        List<ProjectResponse> allProjects = projectService.getAllProjects();
        Optional<ProjectResponse> gotten = allProjects.stream()
                .filter(p -> p.getId().equals(resp.getId()))
                .findFirst();

        Assert.assertTrue(gotten.isPresent());
        Assert.assertEquals("Test project #1", gotten.get().getProjectName());
    }

}
