package com.wbajjouk.taskmanager;


import com.wbajjouk.taskmanager.projectmanagement.ProjectRequest;
import com.wbajjouk.taskmanager.projectmanagement.ProjectResponse;
import com.wbajjouk.taskmanager.projectmanagement.ProjectService;
import com.wbajjouk.taskmanager.taskmanagement.TaskRequest;
import com.wbajjouk.taskmanager.taskmanagement.TaskResponse;
import com.wbajjouk.taskmanager.taskmanagement.TaskService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TaskmanagerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class TaskTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectService projectService;

    @Test
    public void testSaveTask(){

        ProjectResponse project = createSampleProject();
        TaskRequest requestTask = new TaskRequest();
        requestTask.setTaskName("test");
        requestTask.setDescription("Description Test");
        requestTask.setPriority("urgent");
        requestTask.setStatus("to-do");

        requestTask.setProjectId(project.getId());
        requestTask.setDueDate(LocalDate.now());

        TaskResponse response = taskService.saveTask(requestTask);

        List<TaskResponse> allTasks = taskService.getAllTasks();
        Optional<TaskResponse> gotten = allTasks.stream()
                .filter(t -> t.getTaskId().equals(response.getTaskId()))
                .findFirst();

        Assert.assertTrue(gotten.isPresent());
        Assert.assertEquals("test", gotten.get().getTaskName());
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
    public void testMarkAsCompleted(){
        ProjectResponse sampleProject = createSampleProject();

        TaskRequest requestTask = new TaskRequest();
        requestTask.setTaskName("test");
        requestTask.setDescription("Description Test");
        requestTask.setPriority("urgent");
        requestTask.setStatus("to-do");
        requestTask.setProjectId(sampleProject.getId());
        TaskResponse task = taskService.saveTask(requestTask);

        TaskResponse response = taskService.markTaskAsCompleted(task.getTaskId());

        Assert.assertEquals("completed", response.getStatus());

    }

    @Test
    public void testTaskUpdate(){
        ProjectResponse sampleProject = createSampleProject();

        TaskRequest requestTask = new TaskRequest();
        requestTask.setTaskName("test");
        requestTask.setDescription("Description Test");
        requestTask.setPriority("urgent");
        requestTask.setStatus("to-do");
        requestTask.setProjectId(sampleProject.getId());
        TaskResponse task = taskService.saveTask(requestTask);

        requestTask.setTaskName("updated");
        TaskResponse updatedTask = taskService.updateTask(task.getTaskId(), requestTask);
        Assert.assertEquals("updated", updatedTask.getTaskName());

    }


}
