package com.example.rest.domain.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailsList {

    private List<ShortMovieDetails> items;
    private String errorMessage;
}
