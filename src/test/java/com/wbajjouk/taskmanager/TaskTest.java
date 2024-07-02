package com.wbajjouk.taskmanager;

import com.wbajjouk.taskmanager.projectmanagement.ProjectRequest;
import com.wbajjouk.taskmanager.projectmanagement.ProjectResponse;
import com.wbajjouk.taskmanager.projectmanagement.ProjectService;
import com.wbajjouk.taskmanager.taskmanagement.TaskRequest;
import com.wbajjouk.taskmanager.taskmanagement.TaskResponse;
import com.wbajjouk.taskmanager.taskmanagement.TaskService;
import org.junit.Assert;
import org.junit.Before;
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

import static org.junit.jupiter.api.Assertions.assertFalse;

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

    private ProjectResponse sampleProject;

    @Before
    public void setup() {
        sampleProject = createSampleProject();
    }

    @Test
    public void testSaveTask() {
        ProjectResponse project = createSampleProject();
        TaskRequest requestTask = new TaskRequest();
        requestTask.taskName = "test";
        requestTask.description = "Description Test";
        requestTask.priority = "urgent";
        requestTask.status = "to-do";
        requestTask.projectId = project.id;
        requestTask.dueDate = LocalDate.now();

        TaskResponse response = taskService.saveTask(requestTask);

        List<TaskResponse> allTasks = taskService.getAllTasks();
        Optional<TaskResponse> gotten = allTasks.stream()
                .filter(t -> t.taskId.equals(response.taskId))
                .findFirst();

        Assert.assertTrue(gotten.isPresent());
        Assert.assertEquals("test", gotten.get().taskName);
    }

    private ProjectResponse createSampleProject() {
        ProjectRequest requestProject = new ProjectRequest();
        requestProject.isCompleted = false;
        requestProject.description = "My test project";
        requestProject.startDate = LocalDate.EPOCH;
        requestProject.endDate = LocalDate.EPOCH;
        requestProject.projectName = "Test Project #3";
        return projectService.saveProject(requestProject);
    }

    @Test
    public void testMarkAsCompleted() {
        ProjectResponse sampleProject = createSampleProject();

        TaskRequest requestTask = new TaskRequest();
        requestTask.taskName = "test";
        requestTask.description = "Description Test";
        requestTask.priority = "urgent";
        requestTask.status = "to-do";
        requestTask.projectId = sampleProject.id;
        TaskResponse task = taskService.saveTask(requestTask);

        TaskResponse response = taskService.markTaskAsCompleted(task.taskId);

        Assert.assertEquals("completed", response.status);
    }

    @Test
    public void testTaskUpdate() {
        ProjectResponse sampleProject = createSampleProject();

        TaskRequest requestTask = new TaskRequest();
        requestTask.taskName = "test";
        requestTask.description = "Description Test";
        requestTask.priority = "urgent";
        requestTask.status = "to-do";
        requestTask.projectId = sampleProject.id;
        TaskResponse task = taskService.saveTask(requestTask);

        requestTask.taskName = "updated";
        TaskResponse updatedTask = taskService.updateTask(task.taskId, requestTask);
        Assert.assertEquals("updated", updatedTask.taskName);
    }

    @Test
    public void testDeleteTask() {
        ProjectResponse sampleProject = createSampleProject();

        TaskRequest requestTask = new TaskRequest();
        requestTask.taskName = "test";
        requestTask.description = "Description Test";
        requestTask.priority = "urgent";
        requestTask.status = "to-do";
        requestTask.projectId = sampleProject.id;
        TaskResponse task = taskService.saveTask(requestTask);

        taskService.deleteTask(task.taskId);

        Optional<TaskResponse> deletedTask = taskService.getTaskById(task.taskId);
        assertFalse(deletedTask.isPresent());
    }

    @Test
    public void testGetTasksByProjectId() {
        TaskRequest requestTask1 = new TaskRequest();
        requestTask1.taskName = "task1";
        requestTask1.description = "Description Test 1";
        requestTask1.priority = "urgent";
        requestTask1.status = "to-do";
        requestTask1.projectId = sampleProject.id;
        taskService.saveTask(requestTask1);

        TaskRequest requestTask2 = new TaskRequest();
        requestTask2.taskName = "task2";
        requestTask2.description = "Description Test 2";
        requestTask2.priority = "trivial";
        requestTask2.status = "to-do";
        requestTask2.projectId = sampleProject.id;
        taskService.saveTask(requestTask2);

        List<TaskResponse> tasks = taskService.getTasksByProjectId(sampleProject.id);

        Assert.assertNotNull(tasks);
        Assert.assertEquals(2, tasks.size());

        Assert.assertTrue(tasks.stream().anyMatch(t -> t.taskName.equals("task1")));
        Assert.assertTrue(tasks.stream().anyMatch(t -> t.taskName.equals("task2")));
    }

    @Test
    public void testGetCompletedTasksByProjectId() {
        ProjectResponse sampleProject = createSampleProject();

        TaskRequest requestTask1 = new TaskRequest();
        requestTask1.taskName = "task1";
        requestTask1.description = "Description Test 1";
        requestTask1.priority = "urgent";
        requestTask1.status = "to-do";
        requestTask1.projectId = sampleProject.id;
        TaskResponse task1 = taskService.saveTask(requestTask1);

        TaskRequest requestTask2 = new TaskRequest();
        requestTask2.taskName = "task2";
        requestTask2.description = "Description Test 2";
        requestTask2.priority = "urgent";
        requestTask2.status = "to-do";
        requestTask2.projectId = sampleProject.id;
        TaskResponse task2 = taskService.saveTask(requestTask2);

        taskService.markTaskAsCompleted(task1.taskId);

        List<TaskResponse> completedTasks = taskService.getCompletedTasksByProjectId(sampleProject.id);

        Assert.assertNotNull(completedTasks);
        Assert.assertEquals(1, completedTasks.size());
        Assert.assertTrue(completedTasks.stream().anyMatch(t -> t.taskName.equals("task1")));
    }
}
