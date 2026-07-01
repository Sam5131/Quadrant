package com.syamantak.quadrant.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syamantak.quadrant.enums.Quadrant;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class TaskResponse {
    private UUID id;
    private String name;

    @JsonProperty("isUrgent")
    private boolean isUrgent;

    @JsonProperty("isImportant")
    private boolean isImportant;

    private Quadrant quadrant;

    private LocalDateTime dueDate;
    private boolean reminderEnabled;

    @JsonProperty("isCompleted")
    private boolean isCompleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<SubtaskResponse> subtasks;
}