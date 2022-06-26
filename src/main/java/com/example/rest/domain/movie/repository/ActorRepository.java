package com.example.rest.domain.movie.repository;

import com.example.rest.domain.movie.models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {

    boolean existsByName(String name);

    boolean existsByIMDbId(String IMDbId);

    Actor findByName(String name);

    Actor findByIMDbId(String IMDbId);
}
