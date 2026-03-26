package com.josebastardo.task_manager.Repository;

import com.josebastardo.task_manager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    Optional<Task> findByIdAndUserId(Long Id, Long userId);
}
