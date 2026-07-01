package com.syamantak.quadrant.controller;

import com.syamantak.quadrant.dto.request.CreateSubtaskRequest;
import com.syamantak.quadrant.dto.request.UpdateSubtaskRequest;
import com.syamantak.quadrant.dto.response.SubtaskResponse;
import com.syamantak.quadrant.service.SubtaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks/{taskId}/subtasks")
@RequiredArgsConstructor
public class SubtaskController {

    private final SubtaskService subtaskService;

    @PostMapping
    public ResponseEntity<SubtaskResponse> createSubtask(
            @PathVariable UUID taskId,
            @Valid @RequestBody CreateSubtaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subtaskService.createSubtask(taskId, request));
    }

    @GetMapping
    public ResponseEntity<List<SubtaskResponse>> getSubtasks(@PathVariable UUID taskId) {
        return ResponseEntity.ok(subtaskService.getSubtasksByTaskId(taskId));
    }

    @PutMapping("/{subtaskId}")
    public ResponseEntity<SubtaskResponse> updateSubtask(
            @PathVariable UUID taskId,
            @PathVariable UUID subtaskId,
            @Valid @RequestBody UpdateSubtaskRequest request) {
        return ResponseEntity.ok(subtaskService.updateSubtask(taskId, subtaskId, request));
    }

    @PatchMapping("/{subtaskId}/complete")
    public ResponseEntity<SubtaskResponse> toggleComplete(
            @PathVariable UUID taskId,
            @PathVariable UUID subtaskId) {
        return ResponseEntity.ok(subtaskService.toggleSubtaskComplete(taskId, subtaskId));
    }

    @DeleteMapping("/{subtaskId}")
    public ResponseEntity<Void> deleteSubtask(
            @PathVariable UUID taskId,
            @PathVariable UUID subtaskId) {
        subtaskService.deleteSubtask(taskId, subtaskId);
        return ResponseEntity.noContent().build();
    }
}