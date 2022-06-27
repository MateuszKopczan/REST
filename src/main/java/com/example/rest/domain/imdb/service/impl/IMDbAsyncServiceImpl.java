package com.example.rest.domain.imdb.service.impl;

import com.example.rest.domain.exception.APIException;
import com.example.rest.domain.imdb.properties.IMDbEndpoints;
import com.example.rest.domain.movie.dto.MovieDetailsList;
import com.example.rest.domain.movie.service.MovieService;
import com.example.rest.domain.ranking.models.Ranking;
import com.example.rest.domain.ranking.service.RankingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class IMDbAsyncServiceImpl extends IMDbServiceImpl {

    private final IMDbEndpoints imDbEndpoints;
    private final MovieService movieService;
    private final RankingService rankingService;
    private final ObjectMapper mapper = new ObjectMapper();

    @Async
    public void getTop250Movies() throws IOException, APIException {
        Request request = getRequest(imDbEndpoints.getRankings().get("top250movies"));
        Response response = getResponse(request);

        MovieDetailsList top250Movies = mapper.readValue(response.body().string(), MovieDetailsList.class);
        if (!top250Movies.getErrorMessage().equals(""))
            throw new APIException(top250Movies.getErrorMessage());

        movieService.saveUniqueMoviesFromCollection(top250Movies.getItems(), "movies");
        Ranking ranking = rankingService.mapMovieDetailsListToRanking(top250Movies, "top250movies");
        rankingService.save(ranking);
    }

    @Async
    public void getTop250Tvs() throws IOException, APIException {
        Request request = getRequest(imDbEndpoints.getRankings().get("top250tvs"));
        Response response = getResponse(request);

        MovieDetailsList top250Tvs = mapper.readValue(response.body().string(), MovieDetailsList.class);
        if (!top250Tvs.getErrorMessage().equals(""))
            throw new APIException(top250Tvs.getErrorMessage());

        movieService.saveUniqueMoviesFromCollection(top250Tvs.getItems(), "tvs");
        Ranking ranking = rankingService.mapMovieDetailsListToRanking(top250Tvs, "top250tvs");
        rankingService.save(ranking);
    }

    @Async
    public void getMostPopularMovies() throws APIException, IOException {
        Request request = getRequest(imDbEndpoints.getRankings().get("mostPopularMovies"));
        Response response = getResponse(request);

        MovieDetailsList top250Tvs = mapper.readValue(response.body().string(), MovieDetailsList.class);
        if (!top250Tvs.getErrorMessage().equals(""))
            throw new APIException(top250Tvs.getErrorMessage());

        movieService.saveUniqueMoviesFromCollection(top250Tvs.getItems(), "movies");
        Ranking ranking = rankingService.mapMovieDetailsListToRanking(top250Tvs, "mostPopularMovies");
        rankingService.save(ranking);
    }

    @Async
    public void getMostPopularTVs() throws APIException, IOException {
        Request request = getRequest(imDbEndpoints.getRankings().get("mostPopularTVs"));
        Response response = getResponse(request);

        MovieDetailsList top250Tvs = mapper.readValue(response.body().string(), MovieDetailsList.class);
        if (!top250Tvs.getErrorMessage().equals(""))
            throw new APIException(top250Tvs.getErrorMessage());

        movieService.saveUniqueMoviesFromCollection(top250Tvs.getItems(), "tvs");
        Ranking ranking = rankingService.mapMovieDetailsListToRanking(top250Tvs, "mostPopularTVs");
        rankingService.save(ranking);
    }

}
