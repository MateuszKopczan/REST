package com.example.rest.domain.movie.service;

import com.example.rest.domain.movie.dto.SimilarMovieDto;
import com.example.rest.domain.movie.models.SimilarMovie;

import java.util.Collection;
import java.util.List;

public interface SimilarMovieService {

    List<SimilarMovie> mapSimilarMovieDtoCollectionToSimilarMovieCollection(Collection<SimilarMovieDto> similarMovieDtoCollection);
}
