package com.example.rest.domain.service;

import com.example.rest.domain.dto.MovieDetails;
import com.example.rest.domain.dto.MovieDetailsList;
import com.example.rest.domain.models.Movie;
import com.example.rest.domain.ranking.models.Ranking;
import com.example.rest.domain.models.Rating;
import com.example.rest.domain.properties.IMDbProperties;
import com.example.rest.domain.repository.MovieRepository;
import com.example.rest.domain.ranking.repository.RankingRepository;
import com.example.rest.domain.repository.RatingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class IMDbAsyncServiceImpl {

    private final IMDbProperties imDbProperties;
    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;
    private final RankingRepository rankingRepository;
    private final OkHttpClient client = new OkHttpClient().newBuilder().build();
    private final ObjectMapper mapper = new ObjectMapper();

    @Async
    public void getTop250Movies() throws IOException {
        Request request = getRequest("https://imdb-api.com/en/API/Top250Movies/");
        Response response = client.newCall(request).execute();

        // TODO if != 200
        MovieDetailsList top250Movies = mapper.readValue(response.body().string(), MovieDetailsList.class);
        System.out.println(top250Movies.getItems().size());
        System.out.println(top250Movies.getErrorMessage());
        saveUniqueMovies(top250Movies, "movies");
        Ranking ranking = getRanking(top250Movies, "top250movies");
        System.out.println(ranking);
        rankingRepository.save(ranking);
    }

    @Async
    public void getTop250Tvs() throws IOException {
        Request request = getRequest("https://imdb-api.com/en/API/Top250TVs/");
        Response response = client.newCall(request).execute();

        // TODO if != 200
        MovieDetailsList top250Tvs = mapper.readValue(response.body().string(), MovieDetailsList.class);
        saveUniqueMovies(top250Tvs, "tvs");
    }


    @Async
    public void getMostPopularMovies() {

    }


    @Async
    public void getMostPopularTVs() {

    }

    private Request getRequest(String url) {
        return new Request.Builder()
                .url(url + imDbProperties.getKey())
                .method("GET", null)
                .build();
    }

    private void saveUniqueMovies(MovieDetailsList movieDetailsList, String type) {
        movieDetailsList.getItems().stream()
                .filter(movie -> !movieRepository.existsByTitle(movie.getTitle()))
                .map(movie -> mapMovieDetailsToMovie(movie, type))
                .forEach(movie -> {
                    ratingRepository.save(movie.getRating());
                    movieRepository.save(movie);
                });
    }

    private Movie mapMovieDetailsToMovie(MovieDetails movieDetails, String type) {
        return Movie.builder()
                .IMDbId(movieDetails.getId())
                .title(movieDetails.getTitle())
                .fullTitle(movieDetails.getFullTitle())
                .year(Integer.parseInt(movieDetails.getYear()))
                .image(movieDetails.getImage())
                .crew(movieDetails.getCrew())
                .type(type)
                .rating(Rating.builder()
                        .imDbRating(Float.parseFloat(movieDetails.getImDbRating()))
                        .imDbRatingCount(Long.parseLong(movieDetails.getImDbRatingCount()))
                        .build())
                .build();
    }

    private Ranking getRanking(MovieDetailsList movieDetailsList, String name) {
        return Ranking.builder()
                .name(name)
                .movies(getRankingList(movieDetailsList, name.substring("top250".length())))
                .build();
    }

    private LinkedList<Movie> getRankingList(MovieDetailsList movieDetailsList, String type) {
        LinkedList<Movie> moviesRanking = new LinkedList<>();
        for (MovieDetails movieDetails : movieDetailsList.getItems()) {
            if (movieRepository.existsByTitle(movieDetails.getTitle())) {
                System.out.println("EXISTS");
                Movie movie = movieRepository.findByTitle(movieDetails.getTitle());
                moviesRanking.add(movie);
            } else {
                System.out.println("NOT EXISTS");
                Movie movie = mapMovieDetailsToMovie(movieDetails, type);
                moviesRanking.add(movie);
            }
        }
        System.out.println(moviesRanking.size());
        return moviesRanking;
    }
}
