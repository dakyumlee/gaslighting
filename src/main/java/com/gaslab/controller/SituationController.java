package com.gaslab.controller;

import com.gaslab.model.Situation;
import com.gaslab.repository.SituationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SituationController {

    @Autowired
    private SituationRepository situationRepository;

    @GetMapping("/situations")
    public List<Situation> getAllSituations() {
        return situationRepository.findAll();
    }
}
