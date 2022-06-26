package com.example.rest.domain.movie.service;

import com.example.rest.domain.movie.dto.MovieGenre;
import com.example.rest.domain.movie.models.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreService {

    List<Genre> mapMovieGenreCollectionToGenreCollection(Collection<MovieGenre> movieGenreCollection);
}
