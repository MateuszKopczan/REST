package com.example.rest.domain.service;

import com.example.rest.domain.dto.MovieDetailsList;

import java.io.IOException;

public interface IMDbService {

    MovieDetailsList getTop250Movies() throws IOException;
    MovieDetailsList getTop250Tvs() throws IOException;
    MovieDetailsList getMostPopularMovies();
    MovieDetailsList getMostPopularTVs();
}
