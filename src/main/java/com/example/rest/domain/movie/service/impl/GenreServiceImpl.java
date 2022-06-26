package com.example.rest.domain.movie.service.impl;

import com.example.rest.domain.movie.dto.MovieGenre;
import com.example.rest.domain.movie.models.Genre;
import com.example.rest.domain.movie.repository.GenreRepository;
import com.example.rest.domain.movie.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> mapMovieGenreCollectionToGenreCollection(Collection<MovieGenre> movieGenreCollection) {
        List<Genre> genresList = new ArrayList<>();
        for (MovieGenre movieGenre : movieGenreCollection) {
            if (genreRepository.existsByName(movieGenre.getValue())) {
                Genre genre = genreRepository.findByName(movieGenre.getValue());
                genresList.add(genre);
            } else {
                Genre genre = Genre.builder()
                        .name(movieGenre.getValue())
                        .build();
                genreRepository.save(genre);
                genresList.add(genre);
            }
        }
        return genresList;
    }
}
