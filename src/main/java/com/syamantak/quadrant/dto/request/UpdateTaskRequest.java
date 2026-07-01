package com.syamantak.quadrant.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UpdateTaskRequest {

    @NotBlank(message = "Task name cannot be blank")
    private String name;

    @JsonProperty("isUrgent")
    private boolean isUrgent;

    @JsonProperty("isImportant")
    private boolean isImportant;

    private LocalDateTime dueDate;

    private boolean reminderEnabled;
}