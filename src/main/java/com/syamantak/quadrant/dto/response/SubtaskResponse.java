package com.syamantak.quadrant.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class SubtaskResponse {
    private UUID id;
    private UUID taskId;
    private String name;
    private LocalDateTime dueDate;
    private boolean reminderEnabled;

    @JsonProperty("isCompleted")
    private boolean isCompleted;

    private LocalDateTime createdAt;
}