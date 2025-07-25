package com.gaslab.repository;

import com.gaslab.model.DialogueLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DialogueLogRepository extends JpaRepository<DialogueLog, Long> {
    List<DialogueLog> findAllByOrderByTimestampAsc();
}
