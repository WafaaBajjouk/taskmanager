package com.wbajjouk.taskmanager;


import com.wbajjouk.taskmanager.projectmanagement.ProjectRequest;
import com.wbajjouk.taskmanager.projectmanagement.ProjectResponse;
import com.wbajjouk.taskmanager.projectmanagement.ProjectService;
import com.wbajjouk.taskmanager.taskmanagement.TaskRepository;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;

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


    @Test
    public void testDeleteTask() {
        ProjectResponse sampleProject = createSampleProject();

        TaskRequest requestTask = new TaskRequest();
        requestTask.setTaskName("test");
        requestTask.setDescription("Description Test");
        requestTask.setPriority("urgent");
        requestTask.setStatus("to-do");
        requestTask.setProjectId(sampleProject.getId());
        TaskResponse task = taskService.saveTask(requestTask);

        taskService.deleteTask(task.getTaskId());
        //  verify(taskRepository).deleteById(task.getTaskId()); BUT IAM NOT USING MOCKS

        //  retrieve the deleted task ----> should be Optional.empty!!!!
        Optional<TaskResponse> deletedTask = taskService.getTaskById(task.getTaskId());
        assertFalse(deletedTask.isPresent());
    }

    @Test
    public void testGetTasksByProjectId() {
        TaskRequest requestTask1 = new TaskRequest();
        requestTask1.setTaskName("task1");
        requestTask1.setDescription("Description Test 1");
        requestTask1.setPriority("urgent");
        requestTask1.setStatus("to-do");
        requestTask1.setProjectId(sampleProject.getId());
        taskService.saveTask(requestTask1);

        TaskRequest requestTask2 = new TaskRequest();
        requestTask2.setTaskName("task2");
        requestTask2.setDescription("Description Test 2");
        requestTask2.setPriority("trivial");
        requestTask2.setStatus("to-do");
        requestTask2.setProjectId(sampleProject.getId());
        taskService.saveTask(requestTask2);

        List<TaskResponse> tasks = taskService.getTasksByProjectId(sampleProject.getId());

        Assert.assertNotNull(tasks);
        Assert.assertEquals(2, tasks.size());

        Assert.assertTrue(tasks.stream().anyMatch(t -> t.getTaskName().equals("task1")));
        Assert.assertTrue(tasks.stream().anyMatch(t -> t.getTaskName().equals("task2")));
    }

    @Test
    public void testGetCompletedTasksByProjectId() {
        ProjectResponse sampleProject = createSampleProject();

        // save multiple tasks for the created project
        TaskRequest requestTask1 = new TaskRequest();
        requestTask1.setTaskName("task1");
        requestTask1.setDescription("Description Test 1");
        requestTask1.setPriority("urgent");
        requestTask1.setStatus("to-do");
        requestTask1.setProjectId(sampleProject.getId());
        TaskResponse task1 = taskService.saveTask(requestTask1);

        TaskRequest requestTask2 = new TaskRequest();
        requestTask2.setTaskName("task2");
        requestTask2.setDescription("Description Test 2");
        requestTask2.setPriority("urgent");
        requestTask2.setStatus("to-do");
        requestTask2.setProjectId(sampleProject.getId());
        TaskResponse task2 = taskService.saveTask(requestTask2);

        // Mark one task as completed
        taskService.markTaskAsCompleted(task1.getTaskId());

        List<TaskResponse> completedTasks = taskService.getCompletedTasksByProjectId(sampleProject.getId());

        Assert.assertNotNull(completedTasks);
        Assert.assertEquals(1, completedTasks.size());
        Assert.assertTrue(completedTasks.stream().anyMatch(t -> t.getTaskName().equals("task1")));
    }



}
