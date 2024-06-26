package com.wbajjouk.taskmanager.taskmanagement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        TaskResponse savedTask = taskService.saveTask(taskRequest);
        return ResponseEntity.ok(savedTask);
    }
    // get all tasks
    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }
    // get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        Optional<TaskResponse> taskResponse = taskService.getTaskById(id);
        return taskResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //get task by priority
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TaskResponse>> getTasksByPriority(@PathVariable String priority) {
        List<TaskResponse> taskResponses = taskService.findTaskByPriority(priority);
        return ResponseEntity.ok(taskResponses);
    }

    //GET TASK BY PROJECT ID
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskResponse>> getTasksbyProjectId( @PathVariable long projectId) {
     List<TaskResponse> taskResponse = taskService.getTasksByProjectId(projectId);
        return ResponseEntity.ok(taskResponse);
    }


    //Get Only completed Task of a project X
    @GetMapping("/completedTask/project/{id}")
    public ResponseEntity<List<TaskResponse>> getCompletedTasksByProjectId(@PathVariable long id) {
           List<TaskResponse> taskResponse = taskService.getCompletedTasksByProjectId(id);
            return ResponseEntity.ok(taskResponse);
    }


    //GET TASK BY STATUS
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(@PathVariable String status) {
        List<TaskResponse>  taskResponses  = taskService.findTasksByStatus(status);
        return ResponseEntity.ok(taskResponses);
    }


    //Mark a Task As Completed
    @PutMapping("/{id}")
    public TaskResponse markAsCompleted(@PathVariable long id) {
        return taskService.markTaskAsCompleted(id);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }


}
