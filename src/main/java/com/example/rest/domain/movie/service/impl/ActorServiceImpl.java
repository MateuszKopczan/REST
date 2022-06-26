package com.example.rest.domain.movie.service.impl;

import com.example.rest.domain.movie.dto.ActorRoleDto;
import com.example.rest.domain.movie.models.Actor;
import com.example.rest.domain.movie.models.ActorRole;
import com.example.rest.domain.movie.repository.ActorRepository;
import com.example.rest.domain.movie.repository.ActorRoleRepository;
import com.example.rest.domain.movie.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final ActorRoleRepository actorRoleRepository;

    @Override
    public List<ActorRole> mapActorRoleDtoCollectionToActorRoleCollection(Collection<ActorRoleDto> actorRoleDtoCollection) {
        System.out.println(actorRoleDtoCollection);
        List<ActorRole> actorRoles = new ArrayList<>();
        for (ActorRoleDto actorRoleDto : actorRoleDtoCollection) {
            if (actorRepository.existsByIMDbId(actorRoleDto.getId())) {
                Actor actor = actorRepository.findByIMDbId(actorRoleDto.getId());
                ActorRole actorRole = ActorRole.builder()
                        .actor(actor)
                        .asCharacter(actorRoleDto.getAsCharacter())
                        .build();
                actorRoleRepository.save(actorRole);
                actorRoles.add(actorRole);
            } else {
                Actor actor = Actor.builder()
                        .IMDbId(actorRoleDto.getId())
                        .image(actorRoleDto.getImage())
                        .name(actorRoleDto.getName())
                        .build();
                actorRepository.save(actor);
                ActorRole actorRole = ActorRole.builder()
                        .actor(actor)
                        .asCharacter(actorRoleDto.getAsCharacter())
                        .build();
                actorRoleRepository.save(actorRole);
                actorRoles.add(actorRole);
            }
        }
        return actorRoles;
    }
}
