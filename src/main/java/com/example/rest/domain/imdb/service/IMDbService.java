package com.example.rest.domain.imdb.service;

import com.example.rest.domain.exception.APIException;
import com.example.rest.domain.movie.models.Movie;

import java.io.IOException;

public interface IMDbService {

    void saveMovieWithAllDetails(String IMDbId) throws IOException, APIException;

    void addAllDetailsToMovie(Movie movie) throws IOException, APIException;
}
