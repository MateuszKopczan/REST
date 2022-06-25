package com.example.rest.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String IMDbId;
    private String title;
    private String fullTitle;
    private int year;
    private String image;
    private String crew;
    private String type;
    private String genres;

    @OneToOne
    private Rating rating;
}
