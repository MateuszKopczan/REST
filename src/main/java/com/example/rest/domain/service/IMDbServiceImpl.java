package com.example.rest.domain.service;

import com.example.rest.domain.dto.MovieDetailsList;
import com.example.rest.domain.properties.IMDbProperties;
import com.example.rest.domain.repository.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class IMDbServiceImpl implements IMDbService {

    private final MovieRepository movieRepository;
    private final IMDbProperties imDbProperties;
    private final OkHttpClient client = new OkHttpClient().newBuilder().build();
    private final ObjectMapper mapper = new ObjectMapper();


    @Override
    public MovieDetailsList getTop250Movies() throws IOException {
        System.out.println(movieRepository.existsByTitle("TITLE"));
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

    private Request getRequest(String url){
        Request request = new Request.Builder()
                .url(url + imDbProperties.getKey())
                .method("GET", null)
                .build();
        return request;
    }
}
