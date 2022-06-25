package com.example.rest.domain.dto;

import com.example.rest.domain.models.Actor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToMany;
import java.util.List;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FullMovieDetails {

    private String IMDbId;
    private String title;
    private String originalTitle;
    private String fullTitle;
    private String type;
    private String year;
    private String image;
    private String releaseDate;
    private String plot;
    private String awards;

    @ManyToMany
    private List<Actor> actorList;
    private String genres;


    private String errorMessage;
}
