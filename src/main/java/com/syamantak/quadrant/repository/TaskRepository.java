package com.syamantak.quadrant.repository;

import com.syamantak.quadrant.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByDueDateBetween(LocalDateTime start, LocalDateTime end);

    List<Task> findByIsUrgentAndIsImportant(boolean isUrgent, boolean isImportant);

    List<Task> findByIsCompleted(boolean isCompleted);
}