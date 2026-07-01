package com.syamantak.quadrant.service;

import com.syamantak.quadrant.dto.request.CreateSubtaskRequest;
import com.syamantak.quadrant.dto.request.UpdateSubtaskRequest;
import com.syamantak.quadrant.dto.response.SubtaskResponse;
import com.syamantak.quadrant.entity.Subtask;
import com.syamantak.quadrant.entity.Task;
import com.syamantak.quadrant.exception.ResourceNotFoundException;
import com.syamantak.quadrant.mapper.TaskMapper;
import com.syamantak.quadrant.repository.SubtaskRepository;
import com.syamantak.quadrant.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public SubtaskResponse createSubtask(UUID taskId, CreateSubtaskRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        Subtask subtask = Subtask.builder()
                .task(task)
                .name(request.getName())
                .dueDate(request.getDueDate())
                .reminderEnabled(request.isReminderEnabled())
                .isCompleted(false)
                .build();
        return taskMapper.toSubtaskResponse(subtaskRepository.save(subtask));
    }

    public List<SubtaskResponse> getSubtasksByTaskId(UUID taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new ResourceNotFoundException("Task not found with id: " + taskId);
        }
        return subtaskRepository.findByTaskId(taskId)
                .stream().map(taskMapper::toSubtaskResponse).toList();
    }

    @Transactional
    public SubtaskResponse updateSubtask(UUID taskId, UUID subtaskId, UpdateSubtaskRequest request) {
        Subtask subtask = getValidatedSubtask(taskId, subtaskId);
        subtask.setName(request.getName());
        subtask.setDueDate(request.getDueDate());
        subtask.setReminderEnabled(request.isReminderEnabled());
        return taskMapper.toSubtaskResponse(subtaskRepository.save(subtask));
    }

    @Transactional
    public SubtaskResponse toggleSubtaskComplete(UUID taskId, UUID subtaskId) {
        Subtask subtask = getValidatedSubtask(taskId, subtaskId);
        subtask.setCompleted(!subtask.isCompleted());
        subtaskRepository.save(subtask);

        // Auto-complete parent if no incomplete subtasks remain
        boolean hasIncomplete = subtaskRepository.existsByTaskIdAndIsCompletedFalse(taskId);
        if (!hasIncomplete) {
            Task task = subtask.getTask();
            task.setCompleted(true);
            taskRepository.save(task);
        }

        return taskMapper.toSubtaskResponse(subtask);
    }

    @Transactional
    public void deleteSubtask(UUID taskId, UUID subtaskId) {
        Subtask subtask = getValidatedSubtask(taskId, subtaskId);
        subtaskRepository.delete(subtask);
    }

    // Validates subtask exists AND belongs to the given taskId
    private Subtask getValidatedSubtask(UUID taskId, UUID subtaskId) {
        Subtask subtask = subtaskRepository.findById(subtaskId)
                .orElseThrow(() -> new ResourceNotFoundException("Subtask not found with id: " + subtaskId));
        if (!subtask.getTask().getId().equals(taskId)) {
            throw new ResourceNotFoundException(
                    "Subtask " + subtaskId + " does not belong to task " + taskId
            );
        }
        return subtask;
    }
}