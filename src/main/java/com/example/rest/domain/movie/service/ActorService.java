package com.example.rest.domain.movie.service;

import com.example.rest.domain.movie.dto.ActorRoleDto;
import com.example.rest.domain.movie.models.ActorRole;

import java.util.Collection;
import java.util.List;

public interface ActorService {

    List<ActorRole> mapActorRoleDtoCollectionToActorRoleCollection(Collection<ActorRoleDto> actorRoleDtoCollection);
}
