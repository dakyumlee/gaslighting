package com.gaslab.controller;

import com.gaslab.model.DialogueLog;
import com.gaslab.repository.DialogueLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@RestController
public class DialogueController {

    @Autowired
    private DialogueLogRepository logRepository;

    @GetMapping("/api/dialogue/logs")
    public List<Map<String, String>> getLogs() {
        List<DialogueLog> logs = logRepository.findAllByOrderByTimestampAsc();
        List<Map<String, String>> result = new ArrayList<>();
        
        for (DialogueLog log : logs) {
            Map<String, String> item = new HashMap<>();
            item.put("sender", log.getSender());
            item.put("message", log.getMessage());
            result.add(item);
        }
        
        return result;
    }

    @PostMapping("/api/dialogue/save")
    public Map<String, String> saveDialogue(@RequestBody Map<String, String> request) {
        String sender = request.get("sender");
        String message = request.get("message");
        
        DialogueLog log = new DialogueLog();
        log.setSender(sender);
        log.setMessage(message);
        
        logRepository.save(log);
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return response;
    }

    @PostMapping("/api/dialogue/generate")
    public Map<String, String> generateReply(@RequestBody Map<String, String> request) {
        String userText = request.get("text");

        String[] responses = {
            "그래서 결국 너 탓이라는 거야.",
            "왜 맨날 이렇게 예민하게 굴어?",
            "너 때문에 내가 이렇게 된 거 몰라?",
            "네가 먼저 그렇게 했잖아.",
            "또 시작이네. 피곤해.",
            "너랑 대화하는 게 진짜 힘들다.",
            "내가 뭘 잘못했는데? 다 너 때문이야.",
            "너는 왜 항상 나를 의심해?",
            "내가 너한테 얼마나 잘해줬는데 이러는 거야?",
            "너무 예민하게 받아들이지 마."
        };
        
        String aiReply = responses[(int)(Math.random() * responses.length)];
        
        DialogueLog aiLog = new DialogueLog();
        aiLog.setSender("ai");
        aiLog.setMessage(aiReply);
        logRepository.save(aiLog);
        
        Map<String, String> response = new HashMap<>();
        response.put("aiReply", aiReply);
        return response;
    }

    @GetMapping("/api/dialogues")
    public List<Map<String, String>> getDialoguesBySituation(@RequestParam String situation) {
        return new ArrayList<>();
    }
}