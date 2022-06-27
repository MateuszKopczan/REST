package com.example.rest.domain.imdb.service.impl;

import com.example.rest.domain.imdb.properties.IMDbProperties;
import com.example.rest.domain.imdb.service.IMDbService;
import com.example.rest.domain.movie.dto.FullMovieDetails;
import com.example.rest.domain.movie.dto.MovieDetailsList;
import com.example.rest.domain.movie.models.Movie;
import com.example.rest.domain.movie.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class IMDbServiceImpl implements IMDbService {

    private MovieService movieService;
    private final IMDbProperties imDbProperties;
    private final OkHttpClient client = new OkHttpClient().newBuilder().build();
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public MovieDetailsList getTop250Movies() throws IOException {
        Request request = getRequest("https://imdb-api.com/en/API/Top250Movies/");
        Response response = client.newCall(request).execute();

        // TODO if != 200
        MovieDetailsList top250Movies = mapper.readValue(response.body().string(), MovieDetailsList.class);

        return top250Movies;
    }

    @Override
    public MovieDetailsList getTop250Tvs() throws IOException {
        Request request = getRequest("https://imdb-api.com/en/API/Top250TVs/");
        Response response = client.newCall(request).execute();
        MovieDetailsList top250Tvs = mapper.readValue(response.body().string(), MovieDetailsList.class);
        return top250Tvs;
    }

    @Override
    public MovieDetailsList getMostPopularMovies() {
        return null;
    }

    @Override
    public MovieDetailsList getMostPopularTVs() {
        return null;
    }

    @Override
    public void saveMovieWithAllDetails(String IMDbId) throws IOException {
        Request request = getRequest("https://imdb-api.com/pl/API/Title/" + imDbProperties.getKey() + "/" + IMDbId);
        Response response = client.newCall(request).execute();

        FullMovieDetails fullMovieDetails = mapper.readValue(response.body().string(), FullMovieDetails.class);
        Movie movie = movieService.createMovieWithFullDetails(fullMovieDetails);
        movieService.save(movie);
    }

    @Override
    public void addAllDetailsToMovie(Movie movie) throws IOException {
        Request request = getRequest("https://imdb-api.com/pl/API/Title/" + imDbProperties.getKey() + "/" + movie.getIMDbId());
        Response response = client.newCall(request).execute();

        FullMovieDetails fullMovieDetails = mapper.readValue(response.body().string(), FullMovieDetails.class);
        movie = movieService.addDetailsToMovie(fullMovieDetails, movie);
        movieService.save(movie);
    }

    private Request getRequest(String url) {
        return new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
    }
}
