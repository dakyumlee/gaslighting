package com.gaslab.controller;

import com.gaslab.model.DialogueLog;
import com.gaslab.repository.DialogueLogRepository;
import com.gaslab.repository.StatementRepository;
import com.gaslab.repository.SituationRepository;
import com.gaslab.model.Statement;
import com.gaslab.model.Situation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class DialogueController {

    @Autowired
    private DialogueLogRepository logRepository;
    
    @Autowired
    private StatementRepository statementRepository;
    
    @Autowired
    private SituationRepository situationRepository;
    
    private final Random random = new Random();
    private Map<String, Integer> conversationState = new HashMap<>();
    private List<String> recentResponses = new ArrayList<>();

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
        String userText = request.get("text").toLowerCase();
        String situation = request.getOrDefault("situation", "all");
        
        int messageCount = conversationState.getOrDefault("count", 0) + 1;
        conversationState.put("count", messageCount);
        
        String aiReply = generateContextualResponse(userText, situation, messageCount);
        
        if (recentResponses.size() >= 5) {
            recentResponses.remove(0);
        }
        recentResponses.add(aiReply);
        
        DialogueLog aiLog = new DialogueLog();
        aiLog.setSender("ai");
        aiLog.setMessage(aiReply);
        logRepository.save(aiLog);
        
        Map<String, String> response = new HashMap<>();
        response.put("aiReply", aiReply);
        return response;
    }
    
    private String generateContextualResponse(String userText, String situation, int messageCount) {
        boolean isApology = userText.contains("미안") || userText.contains("죄송") || userText.contains("잘못");
        boolean isDefense = userText.contains("아니") || userText.contains("그런거") || userText.contains("왜");
        boolean isQuestion = userText.contains("?") || userText.contains("왜") || userText.contains("뭐");
        boolean isAgreement = userText.contains("그래") || userText.contains("맞아") || userText.contains("네");
        
        if (messageCount <= 2) {
            return getInitialResponse(userText, situation);
        } else if (messageCount <= 5) {
            return getEscalatingResponse(userText, situation, isApology, isDefense);
        } else {
            return getIntenseResponse(userText, situation, isQuestion, isAgreement);
        }
    }
    
    private String getInitialResponse(String userText, String situation) {
        String[] responses = {
            "그래? 근데 네가 먼저 " + extractKeyword(userText) + " 했잖아.",
            "왜 갑자기 변명해? 뭔가 찔리는 게 있나봐.",
            "네가 그렇게 말하니까 더 화가 나네.",
            "아직도 네가 뭘 잘못했는지 모르겠어?",
            "말을 그렇게 하면 내가 나쁜 사람이 되는 거야?"
        };
        
        // 상황별 맞춤 응답 추가
        if ("relationship".equals(situation)) {
            String[] relationshipResponses = {
                "사랑한다면서 왜 이렇게 날 힘들게 해?",
                "다른 커플들은 이런 문제 없는데 우리만 왜 이래?",
                "네가 날 정말 사랑하는지 의심스러워."
            };
            responses = combineArrays(responses, relationshipResponses);
        }
        
        return selectUniqueResponse(responses);
    }
    
    private String getEscalatingResponse(String userText, String situation, boolean isApology, boolean isDefense) {
        if (isApology) {
            String[] apologyResponses = {
                "미안하다고 하면 다야? 행동으로 보여줘야지.",
                "맨날 미안하다고만 하고 또 똑같이 행동하잖아.",
                "사과는 무슨. 진심이 느껴지지도 않아.",
                "이제 와서 미안하다고? 이미 늦었어.",
                "네 사과 따위는 필요 없어. 애초에 하지 말았어야지."
            };
            return selectUniqueResponse(apologyResponses);
        }
        
        if (isDefense) {
            String[] defenseResponses = {
                "또 변명이야? 정말 실망이다.",
                "네가 그렇게 나오니까 대화가 안 되는 거야.",
                "왜 맨날 남 탓만 해? 네 잘못은 안 보여?",
                "그래, 나만 나쁜 사람이지. 너는 완벽하고.",
                "네가 이렇게 방어적으로 나오는 것 자체가 문제야."
            };
            return selectUniqueResponse(defenseResponses);
        }
        
        if (!"all".equals(situation)) {
            Situation sit = situationRepository.findByName(situation);
            if (sit != null) {
                List<Statement> statements = statementRepository.findBySituation(sit);
                if (!statements.isEmpty()) {
                    Statement stmt = statements.get(random.nextInt(statements.size()));
                    return stmt.getContent();
                }
            }
        }
        
        return getGeneralEscalatingResponse();
    }
    
    private String getIntenseResponse(String userText, String situation, boolean isQuestion, boolean isAgreement) {
        if (isQuestion) {
            String[] questionResponses = {
                "질문으로 화제 돌리지 마. 네가 잘못한 거 인정해.",
                "왜? 왜? 맨날 왜야. 생각이란 걸 해봐.",
                "그것도 몰라? 정말 답답하다.",
                "내가 설명해줘야 알아? 상식적으로 생각해봐.",
                "그런 것도 물어봐야 해? 진짜 한심하다."
            };
            return selectUniqueResponse(questionResponses);
        }
        
        if (isAgreement) {
            String[] agreementResponses = {
                "이제야 인정하네. 처음부터 그렇게 하지.",
                "그래 맞아. 다 네 잘못이야. 이제 알겠어?",
                "인정은 하는구나. 근데 바뀌는 건 없잖아.",
                "말로만 그래 그래 하지 말고 행동으로 보여줘.",
                "네가 인정한다고 내 상처가 없어지는 줄 알아?"
            };
            return selectUniqueResponse(agreementResponses);
        }
        
        return getGeneralIntenseResponse();
    }
    
    private String getGeneralEscalatingResponse() {
        String[] responses = {
            "네가 이렇게 나오니까 우리 사이가 망가지는 거야.",
            "다른 사람들한테 물어봐. 누가 맞는지.",
            "너는 왜 항상 문제를 만들어?",
            "내가 이렇게 힘들어하는데 넌 뭐하고 있어?",
            "정말 너랑은 대화가 안 돼."
        };
        return selectUniqueResponse(responses);
    }
    
    private String getGeneralIntenseResponse() {
        String[] responses = {
            "너 같은 사람이랑 있는 내가 불쌍해.",
            "내 인생 최악의 선택이 너를 만난 거야.",
            "네가 변하지 않으면 우린 끝이야.",
            "왜 내가 이런 취급을 받아야 해?",
            "너는 정말 구제불능이야."
        };
        return selectUniqueResponse(responses);
    }
    
    private String selectUniqueResponse(String[] responses) {
        List<String> availableResponses = new ArrayList<>();
        for (String response : responses) {
            if (!recentResponses.contains(response)) {
                availableResponses.add(response);
            }
        }
        
        if (availableResponses.isEmpty()) {
            availableResponses = Arrays.asList(responses);
        }
        
        return availableResponses.get(random.nextInt(availableResponses.size()));
    }
    
    private String[] combineArrays(String[] arr1, String[] arr2) {
        String[] result = new String[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }
    
    private String extractKeyword(String text) {
        String[] words = text.split(" ");
        if (words.length > 2) {
            return words[words.length - 1];
        }
        return "그런 말";
    }

    @GetMapping("/api/dialogues")
    public List<Map<String, String>> getDialoguesBySituation(@RequestParam String situation) {
        return new ArrayList<>();
    }
    
    @PostMapping("/api/dialogue/reset")
    public Map<String, String> resetConversation() {
        conversationState.clear();
        recentResponses.clear();
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "reset");
        return response;
    }
}