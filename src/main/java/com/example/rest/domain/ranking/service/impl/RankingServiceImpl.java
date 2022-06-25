package com.example.rest.domain.ranking.service.impl;

import com.example.rest.domain.ranking.models.Ranking;
import com.example.rest.domain.ranking.service.RankingService;
import com.example.rest.domain.ranking.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final RankingRepository rankingRepository;

    @Override
    public Ranking getRanking(String name) {
        return rankingRepository.findByName(name);
    }

}
