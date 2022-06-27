package com.example.rest.domain.imdb.service.impl;

import com.example.rest.domain.exception.APIException;
import com.example.rest.domain.imdb.properties.IMDbEndpoints;
import com.example.rest.domain.imdb.service.IMDbService;
import com.example.rest.domain.movie.dto.FullMovieDetails;
import com.example.rest.domain.movie.models.Movie;
import com.example.rest.domain.movie.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
public class IMDbServiceImpl implements IMDbService {

    private MovieService movieService;
    private IMDbEndpoints imDbEndpoints;
    private final OkHttpClient client = new OkHttpClient().newBuilder().build();
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    @Autowired
    public void setImDbEndpoints(IMDbEndpoints imDbEndpoints) {
        this.imDbEndpoints = imDbEndpoints;
    }

    @Override
    public void saveMovieWithAllDetails(String IMDbId) throws IOException, APIException {
        Request request = getRequest(imDbEndpoints.getMovies().get("fullDetails") + IMDbId);
        Response response = getResponse(request);

        FullMovieDetails fullMovieDetails = mapper.readValue(response.body().string(), FullMovieDetails.class);
        if (fullMovieDetails.getErrorMessage() != null)
            throw new APIException(fullMovieDetails.getErrorMessage());

        Movie movie = movieService.createMovieWithFullDetails(fullMovieDetails);
        movieService.save(movie);
    }

    @Override
    public void addAllDetailsToMovie(Movie movie) throws IOException, APIException {
        Request request = getRequest(imDbEndpoints.getMovies().get("fullDetails") + movie.getIMDbId());
        Response response = getResponse(request);

        FullMovieDetails fullMovieDetails = mapper.readValue(response.body().string(), FullMovieDetails.class);
        if (fullMovieDetails.getErrorMessage() != null)
            throw new APIException(fullMovieDetails.getErrorMessage());

        movie = movieService.addDetailsToMovie(fullMovieDetails, movie);
        movieService.save(movie);
    }

    protected Request getRequest(String url) {
        return new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
    }

    protected Response getResponse(Request request) throws IOException, APIException {
        Response response = client.newCall(request).execute();
        validateResponse(response);
        return response;
    }

    private void validateResponse(Response response) throws APIException {
        if (response.code() != 200 || response.body() == null)
            throw new APIException("External API error");
    }
}
