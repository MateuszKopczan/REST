package com.example.rest.domain.ranking.service;

import com.example.rest.domain.movie.dto.MovieDetailsList;
import com.example.rest.domain.ranking.models.Ranking;

public interface RankingService {

    Ranking getRanking(String name);

    void save(Ranking ranking);

    Ranking mapMovieDetailsListToRanking(MovieDetailsList movieDetailsList, String rankingName);
}
