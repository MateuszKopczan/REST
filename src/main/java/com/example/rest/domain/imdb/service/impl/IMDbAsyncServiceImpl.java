package com.example.rest.domain.imdb.service.impl;

import com.example.rest.domain.imdb.properties.IMDbProperties;
import com.example.rest.domain.movie.dto.MovieDetailsList;
import com.example.rest.domain.movie.service.MovieService;
import com.example.rest.domain.ranking.models.Ranking;
import com.example.rest.domain.ranking.service.RankingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class IMDbAsyncServiceImpl {

    private final IMDbProperties imDbProperties;
    private final MovieService movieService;
    private final RankingService rankingService;
    private final OkHttpClient client = new OkHttpClient().newBuilder().build();
    private final ObjectMapper mapper = new ObjectMapper();

    @Async
    public void getTop250Movies() throws IOException {
        Request request = getRequest("https://imdb-api.com/en/API/Top250Movies/" + imDbProperties.getKey());
        Response response = client.newCall(request).execute();

        // TODO if != 200
        MovieDetailsList top250Movies = mapper.readValue(response.body().string(), MovieDetailsList.class);

        movieService.saveUniqueMoviesFromCollection(top250Movies.getItems(), "movies");
        Ranking ranking = rankingService.mapMovieDetailsListToRanking(top250Movies, "top250movies");
        rankingService.save(ranking);
    }


    @Async
    public void getTop250Tvs() throws IOException {
        Request request = getRequest("https://imdb-api.com/en/API/Top250TVs/" + imDbProperties.getKey());
        Response response = client.newCall(request).execute();

        // TODO if != 200
        MovieDetailsList top250Tvs = mapper.readValue(response.body().string(), MovieDetailsList.class);
        movieService.saveUniqueMoviesFromCollection(top250Tvs.getItems(), "tvs");
    }

    @Async
    public void getMostPopularMovies() {

    }

    @Async
    public void getMostPopularTVs() {

    }

    private Request getRequest(String url) {
        return new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
    }
}
