package com.gaslab.controller;

import com.gaslab.model.Statement;
import com.gaslab.model.DialogueLog;
import com.gaslab.model.Situation;
import com.gaslab.repository.StatementRepository;
import com.gaslab.repository.DialogueLogRepository;
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

    @GetMapping("/api/situations")
    public List<String> getStatementsBySituation(@RequestParam String situation) {
        Situation sit = situationRepository.findByName(situation);
        if (sit == null) return List.of();
        return statementRepository.findBySituation(sit)
                                  .stream()
                                  .map(Statement::getContent)
                                  .toList();
    }

@PostMapping("/dialogue/generate")
public Map<String, String> generateReply(@RequestBody Map<String, String> payload) {
    String input = payload.get("text");

    String aiReply = gaslightReply(input);
    logRepository.save(new DialogueLog("user", input));
    logRepository.save(new DialogueLog("ai", aiReply));

    return Map.of("aiReply", aiReply);
}

private String gaslightReply(String input) {
    List<String> replies = List.of(
        "그건 다 네가 만든 환상이야",
        "나는 그런 말 한 적 없는데? ㅋㅋ",
        "지금 이러는 거 보면 역시 문제는 너네.",
        "\"" + input + "\"? 넌 진짜 망상충이다."
    );
    return "AI: " + replies.get((int)(Math.random() * replies.size()));
}
}

