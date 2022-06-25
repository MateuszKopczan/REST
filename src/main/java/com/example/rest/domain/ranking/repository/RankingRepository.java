package com.example.rest.domain.ranking.repository;

import com.example.rest.domain.ranking.models.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingRepository extends JpaRepository<Ranking, Long> {

    Ranking findByName(String name);
}
