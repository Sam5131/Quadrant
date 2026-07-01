package com.syamantak.quadrant.service;

import com.syamantak.quadrant.dto.request.CreateTaskRequest;
import com.syamantak.quadrant.dto.request.UpdateTaskRequest;
import com.syamantak.quadrant.dto.response.TaskResponse;
import com.syamantak.quadrant.entity.Task;
import com.syamantak.quadrant.enums.Quadrant;
import com.syamantak.quadrant.exception.ResourceNotFoundException;
import com.syamantak.quadrant.mapper.TaskMapper;
import com.syamantak.quadrant.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskResponse createTask(CreateTaskRequest request) {
        Task task = Task.builder()
                .name(request.getName())
                .isUrgent(request.isUrgent())
                .isImportant(request.isImportant())
                .dueDate(request.getDueDate())
                .reminderEnabled(request.isReminderEnabled())
                .isCompleted(false)
                .build();
        return taskMapper.toTaskResponse(taskRepository.save(task));
    }

    public TaskResponse getTaskById(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return taskMapper.toTaskResponse(task);
    }

    public List<TaskResponse> getAllTasks(LocalDate date, Quadrant quadrant, Boolean completed) {
        if (date != null) {
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.atTime(23, 59, 59);
            return taskRepository.findByDueDateBetween(start, end)
                    .stream().map(taskMapper::toTaskResponse).toList();
        }
        if (quadrant != null) {
            boolean isUrgent   = (quadrant == Quadrant.Q1 || quadrant == Quadrant.Q3);
            boolean isImportant = (quadrant == Quadrant.Q1 || quadrant == Quadrant.Q2);
            return taskRepository.findByIsUrgentAndIsImportant(isUrgent, isImportant)
                    .stream().map(taskMapper::toTaskResponse).toList();
        }
        if (completed != null) {
            return taskRepository.findByIsCompleted(completed)
                    .stream().map(taskMapper::toTaskResponse).toList();
        }
        return taskRepository.findAll()
                .stream().map(taskMapper::toTaskResponse).toList();
    }

    @Transactional
    public TaskResponse updateTask(UUID id, UpdateTaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setName(request.getName());
        task.setUrgent(request.isUrgent());
        task.setImportant(request.isImportant());
        task.setDueDate(request.getDueDate());
        task.setReminderEnabled(request.isReminderEnabled());
        return taskMapper.toTaskResponse(taskRepository.save(task));
    }

    @Transactional
    public TaskResponse toggleComplete(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setCompleted(!task.isCompleted());
        return taskMapper.toTaskResponse(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}