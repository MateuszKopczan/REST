package com.example.rest.domain.ranking.controller;

import com.example.rest.domain.movie.models.Movie;
import com.example.rest.domain.ranking.models.Ranking;
import com.example.rest.domain.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/rankings")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/movies/top250")
    public ResponseEntity<Collection<Movie>> getTop250Movies() {
        Ranking ranking = rankingService.getRanking("top250movies");
        return ResponseEntity.ok(ranking.getMovies());
    }

    @GetMapping("/tvs/top250")
    public ResponseEntity<Collection<Movie>> getTop250Tvs() {
        Ranking ranking = rankingService.getRanking("top250tvs");
        return ResponseEntity.ok(ranking.getMovies());
    }

    @GetMapping("movies/popular")
    public ResponseEntity<Collection<Movie>> getMostPopularMoviesRanking() {
        Ranking ranking = rankingService.getRanking("mostPopularMovies");
        return ResponseEntity.ok(ranking.getMovies());
    }

    @GetMapping("tvs/popular")
    public ResponseEntity<Collection<Movie>> getMostPopularTvsRanking() {
        Ranking ranking = rankingService.getRanking("mostPopularTVs");
        return ResponseEntity.ok(ranking.getMovies());
    }
}
