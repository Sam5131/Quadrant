package com.syamantak.quadrant.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CreateSubtaskRequest {

    @NotBlank(message = "Subtask name cannot be blank")
    private String name;

    private LocalDateTime dueDate;

    private boolean reminderEnabled;
}