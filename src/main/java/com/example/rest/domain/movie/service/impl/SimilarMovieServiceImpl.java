package com.example.rest.domain.movie.service.impl;

import com.example.rest.domain.movie.dto.SimilarMovieDto;
import com.example.rest.domain.movie.models.SimilarMovie;
import com.example.rest.domain.movie.repository.SimilarMovieRepository;
import com.example.rest.domain.movie.service.SimilarMovieService;
import com.example.rest.util.NumberParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SimilarMovieServiceImpl implements SimilarMovieService {

    private final SimilarMovieRepository similarMovieRepository;

    @Override
    public List<SimilarMovie> mapSimilarMovieDtoCollectionToSimilarMovieCollection(Collection<SimilarMovieDto> similarMovieDtoCollection) {
        List<SimilarMovie> similarMovieList = new ArrayList<>();
        for (SimilarMovieDto similarMovieDto : similarMovieDtoCollection) {
            if (similarMovieRepository.existsByIMDbId(similarMovieDto.getId())) {
                SimilarMovie similarMovie = similarMovieRepository.findByIMDbId(similarMovieDto.getId());
                similarMovieList.add(similarMovie);
            } else {
                SimilarMovie similarMovie = SimilarMovie.builder()
                        .IMDbId(similarMovieDto.getId())
                        .title(similarMovieDto.getTitle())
                        .image(similarMovieDto.getImage())
                        .imDbRating(NumberParser.parseStringToFloat(similarMovieDto.getImDbRating()))
                        .build();
                similarMovieRepository.save(similarMovie);
                similarMovieList.add(similarMovie);
            }
        }
        return similarMovieList;
    }
}
