package com.gaslab.repository;

import com.gaslab.model.Situation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SituationRepository extends JpaRepository<Situation, Long> {
    Situation findByName(String name);
}


