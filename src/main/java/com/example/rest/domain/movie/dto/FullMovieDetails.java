package com.example.rest.domain.movie.dto;

import com.example.rest.domain.movie.models.Rating;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FullMovieDetails {

    private String id;
    private String title;
    private String originalTitle;
    private String fullTitle;
    private String type;
    private String year;
    private String image;
    private String releaseDate;
    private String plot;
    private String awards;
    private String errorMessage;

    private Rating rating;
    private Collection<ActorRoleDto> actorList;
    private Collection<MovieGenre> genreList;
    private Collection<SimilarMovieDto> similars;
}
