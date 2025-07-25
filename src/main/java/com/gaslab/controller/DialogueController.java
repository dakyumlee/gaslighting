package com.gaslab.controller;

import com.gaslab.model.DialogueLog;
import com.gaslab.repository.DialogueLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;

@RestController
public class DialogueController {

    @Autowired
    private DialogueLogRepository logRepository;
    
    private final Random random = new Random();

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
        
        String[][] responses = {
            {
                "너 정말 정신병원 가봐야겠다. 정상인은 그런 생각 안 해.",
                "네가 이상한 거야. 다른 사람들은 다 나한테 잘 대해주는데.",
                "너만 유별나게 예민한 거야. 세상이 너 중심으로 돌아가는 줄 아니?",
                "또 시작이네. 피해망상증 있는 거 아니야?",
                "너 진짜 정신과 상담 받아봐야겠다. 이건 정상이 아니야."
            },
            {
                "다 네가 자초한 일이야. 왜 남 탓을 해?",
                "네가 나를 이렇게 만들었어. 원래 난 이런 사람 아니었어.",
                "너 때문에 내 인생이 망가졌어. 책임질 거야?",
                "내가 이렇게 된 게 다 누구 때문인데? 반성 좀 해라.",
                "네가 먼저 시작했잖아. 이제 와서 왜 내 탓을 해?"
            },
            {
                "네 친구들도 다 너 이상하다고 하더라. 나만 참고 있는 거야.",
                "부모님도 네가 문제라고 하시던데? 가족들도 다 알아.",
                "누가 너랑 사귀겠어? 내가 아니면 너 혼자 살 거야.",
                "다들 너 때문에 불편해해. 눈치 없니?",
                "네가 있으면 분위기 망쳐. 사람들이 너 피하는 거 모르겠어?"
            },
            {
                "너 같은 사람이 뭘 안다고 그래? 주제 파악 좀 해.",
                "네가 뭘 할 수 있겠어? 하는 일마다 다 망치잖아.",
                "너는 평생 그 모양일 거야. 발전이 없어.",
                "내가 너였으면 부끄러워서 못 살겠다.",
                "쓸모없는 인간. 왜 태어났니?"
            },
            {
                "내가 언제 그랬어? 너 또 상상한 거지?",
                "그런 일 없었어. 네가 꿈꾼 거 아니야?",
                "증거 있어? 없으면 거짓말하지 마.",
                "너 기억력에 문제 있는 거 같아. 병원 가봐.",
                "맨날 없는 일을 지어내네. 관심받고 싶어서 그러니?"
            }
        };
        
        int category = random.nextInt(responses.length);
        String aiReply = responses[category][random.nextInt(responses[category].length)];
        
        if (random.nextInt(10) < 3) {
            String[] extremeResponses = {
                "차라리 죽어버려. 그게 모두를 위한 길이야.",
                "네가 사라지면 모두가 행복해질 거야.",
                "너 같은 쓰레기는 처음 봐.",
                "인간 이하의 존재. 역겹다.",
                "네 존재 자체가 실수야."
            };
            aiReply = extremeResponses[random.nextInt(extremeResponses.length)];
        }
        
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