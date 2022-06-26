package com.example.rest.domain.imdb.service;

import com.example.rest.domain.movie.dto.FullMovieDetails;
import com.example.rest.domain.movie.dto.ShortMovieDetails;
import com.example.rest.domain.movie.dto.MovieDetailsList;
import com.example.rest.domain.movie.models.Movie;

import java.io.IOException;

public interface IMDbService {

    MovieDetailsList getTop250Movies() throws IOException;
    MovieDetailsList getTop250Tvs() throws IOException;
    MovieDetailsList getMostPopularMovies();
    MovieDetailsList getMostPopularTVs();
    void saveMovieWithAllDetails(String IMDbId) throws IOException;
    void addAllDetailsToMovie(Movie movie) throws IOException;
}
