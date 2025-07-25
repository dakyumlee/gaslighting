package com.gaslab.controller;

import com.gaslab.model.Statement;
import com.gaslab.model.Situation;
import com.gaslab.repository.StatementRepository;
import com.gaslab.repository.SituationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DialogueController {

    private final StatementRepository statementRepository;
    private final SituationRepository situationRepository;

    @GetMapping("/api/situations")
    public List<String> getStatementsBySituation(@RequestParam String situation) {
        Situation sit = situationRepository.findByName(situation);
        if (sit == null) return List.of();
        return statementRepository.findBySituation(sit)
                                  .stream()
                                  .map(Statement::getContent)
                                  .toList();
    }
}
