package com.example.rest.domain.movie.service;

import com.example.rest.domain.exception.APIException;
import com.example.rest.domain.movie.dto.FullMovieDetails;
import com.example.rest.domain.movie.dto.ShortMovieDetails;
import com.example.rest.domain.movie.models.Movie;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

public interface MovieService {

    void save(Movie movie);

    Movie getByTitle(String title);

    Movie getByIMDbId(String imDbId) throws IOException, APIException;

    boolean isFullDetails(Movie movie);

    Movie addDetailsToMovie(FullMovieDetails fullMovieDetails, Movie movie);

    Movie createMovieWithFullDetails(FullMovieDetails fullMovieDetails);

    Movie mapShortMovieDetailsToMovie(ShortMovieDetails movieDetails, String type);

    LinkedList<Movie> mapShortMovieDetailsCollectionToMovieCollection
            (Collection<ShortMovieDetails> movieDetailsCollection, String type);

    void saveUniqueMoviesFromCollection(Collection<ShortMovieDetails> movieDetailsCollection, String type);
}
