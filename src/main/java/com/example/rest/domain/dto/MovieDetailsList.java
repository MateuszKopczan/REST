package com.example.rest.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailsList {

    private List<MovieDetails> items;
    private String errorMessage;
}
