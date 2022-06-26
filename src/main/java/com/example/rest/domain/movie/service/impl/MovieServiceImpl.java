package com.example.rest.domain.movie.service.impl;

import com.example.rest.domain.imdb.service.IMDbService;
import com.example.rest.domain.movie.dto.FullMovieDetails;
import com.example.rest.domain.movie.dto.ShortMovieDetails;
import com.example.rest.domain.movie.models.Movie;
import com.example.rest.domain.movie.models.Rating;
import com.example.rest.domain.movie.repository.MovieRepository;
import com.example.rest.domain.movie.repository.RatingRepository;
import com.example.rest.domain.movie.service.ActorService;
import com.example.rest.domain.movie.service.GenreService;
import com.example.rest.domain.movie.service.MovieService;
import com.example.rest.domain.movie.service.SimilarMovieService;
import com.example.rest.util.NumberParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

@Service
@Transactional
//@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private RatingRepository ratingRepository;
    private IMDbService imDbService;
    private ActorService actorService;
    private GenreService genreService;
    private SimilarMovieService similarMovieService;

    @Autowired
    public void setMovieRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    @Autowired
    public void setRatingRepository(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }
    @Autowired
    public void setImDbService(IMDbService imDbService) {
        this.imDbService = imDbService;
    }
    @Autowired
    public void setActorService(ActorService actorService) {
        this.actorService = actorService;
    }
    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }
    @Autowired
    public void setSimilarMovieService(SimilarMovieService similarMovieService) {
        this.similarMovieService = similarMovieService;
    }

    @Override
    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public Movie getByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    @Override
    public Movie getByIMDbId(String imDbId) throws IOException {
        if (movieRepository.existsByIMDbId(imDbId)) {
            Movie movie = movieRepository.findByIMDbId(imDbId);
//            return movie;
            if (movie.isFullDetails())
                return movie;
            else
                imDbService.addAllDetailsToMovie(movie);
        } else
            //           return null;
            imDbService.saveMovieWithAllDetails(imDbId);

        return movieRepository.findByIMDbId(imDbId);
    }

    @Override
    public boolean isFullDetails(Movie movie) {
        return movie.isFullDetails();
    }

    @Override
    public Movie addDetailsToMovie(FullMovieDetails fullMovieDetails, Movie movie) {
        System.out.println("addDetailsToMovie");
        System.out.println(fullMovieDetails);
        movie.setPlot(fullMovieDetails.getPlot());
        movie.setAwards(fullMovieDetails.getAwards());
        movie.setActors(actorService.mapActorRoleDtoCollectionToActorRoleCollection(fullMovieDetails.getActorList()));
        movie.setGenres(genreService.mapMovieGenreCollectionToGenreCollection(fullMovieDetails.getGenreList()));
        movie.setSimilars(similarMovieService.mapSimilarMovieDtoCollectionToSimilarMovieCollection(fullMovieDetails.getSimilars()));
        movie.setFullDetails(true);
        return movie;
    }

    @Override
    public Movie createMovieWithFullDetails(FullMovieDetails fullMovieDetails) {
        System.out.println("createMovieWithFullDetails");
        System.out.println(fullMovieDetails);
        return Movie.builder()
                .IMDbId(fullMovieDetails.getId())
                .title(fullMovieDetails.getTitle())
                .fullTitle(fullMovieDetails.getFullTitle())
                .year(NumberParser.parseStringToInt(fullMovieDetails.getYear()))
                .image(fullMovieDetails.getImage())
                .type(fullMovieDetails.getType())
                .rating(fullMovieDetails.getRating())
                .awards(fullMovieDetails.getAwards())
                .plot(fullMovieDetails.getPlot())
                .actors(actorService.mapActorRoleDtoCollectionToActorRoleCollection(fullMovieDetails.getActorList()))
                .genres(genreService.mapMovieGenreCollectionToGenreCollection(fullMovieDetails.getGenreList()))
                .similars(similarMovieService.mapSimilarMovieDtoCollectionToSimilarMovieCollection(fullMovieDetails.getSimilars()))
                .fullDetails(true)
                .build();
    }

    @Override
    public Movie mapShortMovieDetailsToMovie(ShortMovieDetails movieDetails, String type) {
        return Movie.builder()
                .IMDbId(movieDetails.getId())
                .title(movieDetails.getTitle())
                .fullTitle(movieDetails.getFullTitle())
                .year(NumberParser.parseStringToInt(movieDetails.getYear()))
                .image(movieDetails.getImage())
                .crew(movieDetails.getCrew())
                .type(type)
                .rating(Rating.builder()
                        .imDbRating(NumberParser.parseStringToFloat(movieDetails.getImDbRating()))
                        .imDbRatingCount(NumberParser.parseStringToLong(movieDetails.getImDbRatingCount()))
                        .build())
                .build();
    }

    @Override
    public LinkedList<Movie> mapShortMovieDetailsCollectionToMovieCollection
            (Collection<ShortMovieDetails> movieDetailsCollection, String type) {
        LinkedList<Movie> moviesCollection = new LinkedList<>();
        for (ShortMovieDetails movieDetails : movieDetailsCollection) {
            if (movieRepository.existsByTitle(movieDetails.getTitle())) {
                Movie movie = movieRepository.findByTitle(movieDetails.getTitle());
                moviesCollection.add(movie);
            } else {
                Movie movie = mapShortMovieDetailsToMovie(movieDetails, type);
                moviesCollection.add(movie);
            }
        }
        return moviesCollection;
    }

    @Override
    public void saveUniqueMoviesFromCollection(Collection<ShortMovieDetails> movieDetailsCollection, String type) {
        movieDetailsCollection.stream()
                .filter(movie -> !movieRepository.existsByTitle(movie.getTitle()))
                .map(movie -> mapShortMovieDetailsToMovie(movie, type))
                .forEach(movie -> {
                    ratingRepository.save(movie.getRating());
                    movieRepository.save(movie);
                });
    }
}
