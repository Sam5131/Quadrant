package com.syamantak.quadrant.mapper;

import com.syamantak.quadrant.dto.response.SubtaskResponse;
import com.syamantak.quadrant.dto.response.TaskResponse;
import com.syamantak.quadrant.entity.Subtask;
import com.syamantak.quadrant.entity.Task;
import com.syamantak.quadrant.enums.Quadrant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskMapper {

    public Quadrant deriveQuadrant(boolean isUrgent, boolean isImportant) {
        if (isUrgent && isImportant)   return Quadrant.Q1;
        if (!isUrgent && isImportant)  return Quadrant.Q2;
        if (isUrgent && !isImportant)  return Quadrant.Q3;
        return Quadrant.Q4;
    }

    public SubtaskResponse toSubtaskResponse(Subtask subtask) {
        return SubtaskResponse.builder()
                .id(subtask.getId())
                .taskId(subtask.getTask().getId())
                .name(subtask.getName())
                .dueDate(subtask.getDueDate())
                .reminderEnabled(subtask.isReminderEnabled())
                .isCompleted(subtask.isCompleted())
                .createdAt(subtask.getCreatedAt())
                .build();
    }

    public TaskResponse toTaskResponse(Task task) {
        List<SubtaskResponse> subtaskResponses = task.getSubtasks()
                .stream()
                .map(this::toSubtaskResponse)
                .toList();

        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .isUrgent(task.isUrgent())
                .isImportant(task.isImportant())
                .quadrant(deriveQuadrant(task.isUrgent(), task.isImportant()))
                .dueDate(task.getDueDate())
                .reminderEnabled(task.isReminderEnabled())
                .isCompleted(task.isCompleted())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .subtasks(subtaskResponses)
                .build();
    }
}