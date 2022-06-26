package com.example.rest.domain.ranking.service.impl;

import com.example.rest.domain.movie.dto.MovieDetailsList;
import com.example.rest.domain.movie.service.MovieService;
import com.example.rest.domain.ranking.models.Ranking;
import com.example.rest.domain.ranking.repository.RankingRepository;
import com.example.rest.domain.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final MovieService movieService;
    private final RankingRepository rankingRepository;

    @Override
    public Ranking getRanking(String name) {
        return rankingRepository.findByName(name);
    }

    @Override
    public void save(Ranking ranking) {
        rankingRepository.save(ranking);
    }

    @Override
    public Ranking mapMovieDetailsListToRanking(MovieDetailsList movieDetailsList, String rankingName) {
        return Ranking.builder()
                .name(rankingName)
                .movies(movieService.mapShortMovieDetailsCollectionToMovieCollection(movieDetailsList.getItems(),
                        rankingName.substring("top250".length())))
                .build();
    }

}
