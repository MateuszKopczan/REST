package com.example.rest.domain.movie.controller;

import com.example.rest.domain.exception.APIException;
import com.example.rest.domain.movie.models.Movie;
import com.example.rest.domain.movie.service.MovieService;
import com.example.rest.shared.controller.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/api/tvs")
@RequiredArgsConstructor
public class TvsController extends BaseController {

    private final MovieService moviesService;

    @GetMapping("")
    public ResponseEntity<Collection<Movie>> getMostPopularTvs() {

        return null;
    }

    @GetMapping("/{IMDbId}")
    public ResponseEntity<Movie> getTvMovieDetails(@PathVariable("IMDbId") String imDbId) throws IOException, APIException {
        Movie movie = moviesService.getByIMDbId(imDbId);
        return ResponseEntity.ok(movie);
    }

}
