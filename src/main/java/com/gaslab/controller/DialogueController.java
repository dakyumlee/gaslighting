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
        List<DialogueLog> logs = logRepository.findAllByOrderByTimestampAsc();
        if (logs == null || logs.isEmpty()) return List.of();

        return logs.stream()
            .map(log -> Map.of(
                "sender", log.getSender(),
                "message", log.getMessage()
            ))
            .toList();
    }

    // 🔥 이거 추가! POST로 로그 저장하는 API
    @PostMapping("/dialogue/save")
    public void saveDialogue(@RequestBody DialogueLog log) {
        logRepository.save(log);
    }
}
