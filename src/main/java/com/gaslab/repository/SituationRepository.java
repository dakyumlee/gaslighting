package com.gaslab.repository;

import com.gaslab.model.Statement;
import com.gaslab.model.Situation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StatementRepository extends JpaRepository<Statement, Long> {
    List<Statement> findBySituation(Situation situation);
}

