package com.josebastardo.task_manager.Controller;

import com.josebastardo.task_manager.Service.AuthService;
import com.josebastardo.task_manager.Service.TaskService;
import com.josebastardo.task_manager.dto.TaskRequest;
import com.josebastardo.task_manager.dto.TaskResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping()
    public  List<TaskResponse> GetAllTasks(@RequestHeader (name = "Authorization") String token){
        return taskService.GetAllTasks(token);
    }

    @GetMapping("/{Id}")
    public TaskResponse getTask(@RequestHeader (name = "Authorization") String token, @PathVariable Long Id){
        return taskService.getTask(token, Id);
    }

    @PostMapping()
    public TaskResponse createTask(@RequestHeader (name = "Authorization") String token, @RequestBody TaskRequest request){
        return taskService.createNewTask(token, request);
    }

    @PutMapping("/{Id}")
    public TaskResponse updateTask(@RequestHeader (name = "Authorization") String token, @RequestBody TaskRequest request, @PathVariable Long Id) {
        return taskService.updateTask(token, request, Id);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<Void> deleteTask(@RequestHeader (name = "Authorization") String token, @PathVariable Long Id){
        taskService.deleteTask(token, Id);
        return ResponseEntity.noContent().build();
    }

}

