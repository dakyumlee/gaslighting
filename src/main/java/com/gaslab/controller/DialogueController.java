package com.gaslab.controller;

import com.gaslab.model.DialogueLog;
import com.gaslab.model.Statement;
import com.gaslab.model.Situation;
import com.gaslab.repository.DialogueLogRepository;
import com.gaslab.repository.StatementRepository;
import com.gaslab.repository.SituationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DialogueController {

    private final StatementRepository statementRepository;
    private final SituationRepository situationRepository;
    private final DialogueLogRepository logRepository;

    @GetMapping("/dialogue/logs")
    public List<Map<String, String>> getLogs() {
        return logRepository.findAllByOrderByTimestampAsc().stream()
            .map(log -> Map.of(
                "sender", log.getSender(),
                "message", log.getMessage()
            ))
            .toList();
    }
}
