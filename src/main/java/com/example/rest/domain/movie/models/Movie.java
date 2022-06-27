package com.example.rest.domain.movie.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;
    private String IMDbId;
    private String title;
    private String fullTitle;
    private String image;
    private String crew;
    private String type;
    private String awards;
    private String plot;
    @JsonIgnore
    private boolean fullDetails = false;

    @OneToOne
    private Rating rating;

    @OneToMany
    private Collection<ActorRole> actors;

    @OneToMany
    private Collection<Genre> genres;

    @OneToMany
    private Collection<SimilarMovie> similars;
}
