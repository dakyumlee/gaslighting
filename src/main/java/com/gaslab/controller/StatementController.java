package com.gaslab.controller;

import com.gaslab.model.Situation;
import com.gaslab.model.Statement;
import com.gaslab.repository.SituationRepository;
import com.gaslab.repository.StatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statements2")
public class StatementController {

    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private SituationRepository situationRepository;

    @GetMapping("/api/situations")
    public List<String> getStatementsBySituation(@RequestParam String situation) {
        System.out.println("==== 상황 확인 시작 ====");
System.out.println("받은 상황: " + situation);
Situation sit = situationRepository.findByName(situation);
System.out.println("찾은 결과: " + (sit == null ? "null" : sit.getName()));

        // System.out.println("받은 상황: " + situation);
        // Situation sit = situationRepository.findByName(situation);
        // System.out.println("찾은 Situation: " + (sit != null ? sit.getName() : "null"));

        if (sit == null) {
            throw new RuntimeException("해당 상황을 찾을 수 없습니다: " + situation);
        }

        return statementRepository.findBySituation(sit)
                .stream()
                .map(Statement::getContent)
                .toList();
    }
}
