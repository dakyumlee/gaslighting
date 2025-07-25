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

    @PostMapping("/dialogue/save")
    public Map<String, String> saveDialogue(@RequestBody Map<String, String> request) {
        String sender = request.get("sender");
        String message = request.get("message");
        
        DialogueLog log = new DialogueLog();
        log.setSender(sender);
        log.setMessage(message);
        
        logRepository.save(log);
        
        return Map.of("status", "success");
    }

    @PostMapping("/dialogue/generate")
    public Map<String, String> generateReply(@RequestBody Map<String, String> request) {
        String userText = request.get("text");

        String aiReply = "그래서 결국 너 탓이라는 거야.";
        
        DialogueLog aiLog = new DialogueLog();
        aiLog.setSender("ai");
        aiLog.setMessage(aiReply);
        logRepository.save(aiLog);
        
        return Map.of("aiReply", aiReply);
    }

    @GetMapping("/dialogues")
    public List<Map<String, String>> getDialoguesBySituation(@RequestParam String situation) {
        return List.of();
    }
}