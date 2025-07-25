package com.gaslab.repository;

import com.gaslab.model.DialogueLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DialogueLogRepository extends JpaRepository<DialogueLog, Long> {
}
