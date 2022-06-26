package com.example.rest.domain.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActorRoleDto {

    private String id;
    private String image;
    private String name;
    private String asCharacter;
}
