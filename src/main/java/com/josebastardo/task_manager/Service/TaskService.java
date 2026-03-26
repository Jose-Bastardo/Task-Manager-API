package com.josebastardo.task_manager.Service;

import com.josebastardo.task_manager.Repository.TaskRepository;
import com.josebastardo.task_manager.Repository.UserRepository;
import com.josebastardo.task_manager.dto.TaskRequest;
import com.josebastardo.task_manager.dto.TaskResponse;
import com.josebastardo.task_manager.model.Task;
import com.josebastardo.task_manager.model.User;
import com.josebastardo.task_manager.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository, JwtService jwtService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public TaskResponse createNewTask(String token, TaskRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        if(jwtService.isTokenValid(token, user)) {
            Task newTask = new Task();
            newTask.setUser(user);
            newTask.setTitle(request.getTitle());
            newTask.setDescription(request.getDescription());
            taskRepository.save(newTask);
            return convertToResponse(newTask);
        }
        else {
            throw new RuntimeException("Token Not Valid");
        }

    }

    public List<TaskResponse> GetAllTasks(String token){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        if(jwtService.isTokenValid(token, user)) {
            return convertTaskList(taskRepository.findByUserId(user.getId()));
        }
        else {
            throw new RuntimeException("User not Authenticated");
        }
    }

    public TaskResponse getTask(String token, Long Id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Optional<Task> task = taskRepository.findByIdAndUserId(Id, user.getId());
        if(jwtService.isTokenValid(token, user)) {
            return task.map(this::convertToResponse).orElse(null);
        }
        else {
            throw new RuntimeException("User not Authenticated");
        }
    }

    public TaskResponse updateTask(String token, TaskRequest request, Long Id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        if(jwtService.isTokenValid(token, user)) {
            Task task = taskRepository.findByIdAndUserId(Id, user.getId()).orElseThrow(() -> new RuntimeException("Task Not Found"));;
            task.setTitle(request.getTitle());
            task.setDescription(request.getDescription());
            task.setCompleted(request.getCompleted());
            taskRepository.save(task);
            return convertToResponse(task);
        }
        else {
            throw new RuntimeException("User not Authenticated");
        }
    }

    public void deleteTask(String token, Long Id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        if(jwtService.isTokenValid(token, user)) {
            Optional<Task> task = Optional.of(taskRepository.findByIdAndUserId(Id, user.getId()).orElseThrow(() -> new RuntimeException("Task Not Found")));
            task.ifPresent(taskRepository::delete);
        }
        else {
            throw new RuntimeException("User not Authenticated");
        }
    }

    private TaskResponse convertToResponse(Task task){
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setCompleted(task.getCompleted());
        return response;
    }

    public List<TaskResponse> convertTaskList(List<Task> tasks) {
        return tasks.stream()
                .map(this::convertToResponse) // Map each individual entity
                .collect(Collectors.toList());
    }
}
