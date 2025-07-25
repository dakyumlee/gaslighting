package com.gaslab.repository;

import com.gaslab.model.DialogueLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DialogueLogRepository extends JpaRepository<DialogueLog, Long> {
    List<DialogueLog> findAllByOrderByTimestampAsc();
}