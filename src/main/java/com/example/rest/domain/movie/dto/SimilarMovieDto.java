package com.example.rest.domain.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimilarMovieDto {

    private String id;
    private String title;
    private String image;
    private String imDbRating;
}
