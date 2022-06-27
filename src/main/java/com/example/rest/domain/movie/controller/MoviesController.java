package com.example.rest.domain.movie.controller;

import com.example.rest.domain.exception.APIException;
import com.example.rest.domain.movie.models.Movie;
import com.example.rest.domain.movie.service.MovieService;
import com.example.rest.security.models.User;
import com.example.rest.security.service.UserService;
import com.example.rest.shared.controller.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MoviesController extends BaseController {

    private final MovieService movieService;
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<Collection<Movie>> getMostPopularMovies() {
        return null;
    }

    @GetMapping("/{IMDbId}")
    public ResponseEntity<Movie> getMovieDetails(@PathVariable("IMDbId") String imDbId) throws IOException, APIException {
        System.out.println(getUser().getUsername());

        Movie movie = movieService.getByIMDbId(imDbId); // TODO obsluzyc wyjatek
        return ResponseEntity.ok(movie);
    }

    @PostMapping("/")
    public ResponseEntity<Void> addMovieToWatchList(@RequestBody String imDbId) throws IOException, APIException {
        User user = getUser();
        Movie movie = movieService.getByIMDbId(imDbId);
        userService.addMovieToWatchList(user, movie);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{IMDbId}")
    public ResponseEntity<Void> removeMovieFromWatchList(@PathVariable("IMDbId") String IMDbId) throws IOException, APIException {
        User user = getUser();
        Movie movie = movieService.getByIMDbId(IMDbId);
        userService.removeMovieFromWatchList(user, movie);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> clearWatchList(){
        User user = getUser();
        userService.clearWatchList(user);
        return ResponseEntity.ok().build();
    }
}
