package com.stx.services;

import com.stx.daos.ArtifactRepo;
import com.stx.daos.UserRepo;
import com.stx.domains.dtos.ArtifactCreateRequest;
import com.stx.domains.dtos.ArtifactDto;
import com.stx.domains.dtos.ArtifactSortRequest;
import com.stx.domains.dtos.ArtifactUpdateRequest;
import com.stx.domains.exceptions.NotFoundException;
import com.stx.domains.mappers.ArtifactMapper;
import com.stx.domains.models.Artifact;
import com.stx.domains.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArtifactService {
    private final ArtifactRepo artifactRepo;
    private final UserRepo userRepo;
    private final ArtifactMapper mapper;

    public ArtifactService(ArtifactRepo artifactRepo, UserRepo userRepo, ArtifactMapper mapper) {
        this.artifactRepo = artifactRepo;
        this.userRepo = userRepo;
        this.mapper = mapper;
    }

    private ArtifactDto create(ArtifactCreateRequest request, User user) {
        Artifact artifact = mapper.create(request);
        artifact.setUser(user);
        artifact = artifactRepo.save(artifact);
        return mapper.toDTO(artifact);
    }

    @Transactional
    public ArtifactDto upset(ArtifactCreateRequest request, User user) {
        Optional<Artifact> optionalArtifact = artifactRepo.findByUserAndId(user, request.getId());
        if (optionalArtifact.isEmpty()) {
            return create(request, user);
        } else {
            ArtifactUpdateRequest updateRequest = new ArtifactUpdateRequest();
            updateRequest.setCategory(request.getCategory());
            updateRequest.setDescription(request.getDescription());
            return update(optionalArtifact.get().getId(), updateRequest, user);
        }
    }

    private ArtifactDto update(UUID id, ArtifactUpdateRequest request, User user) {
        Optional<Artifact> optionalArtifact = artifactRepo.findByUserAndId(user, id);
        if (optionalArtifact.isEmpty()) {
            throw new NotFoundException("Artifact \"%s\" is not found".formatted(id));
        } else {
            Artifact artifact = optionalArtifact.get();
            mapper.update(artifact, request);
            artifact = artifactRepo.save(artifact);
            return mapper.toDTO(artifact);
        }
    }

    private void delete(UUID id, User user) {
        Optional<Artifact> optionalArtifact = artifactRepo.findByUserAndId(user, id);
        if (optionalArtifact.isEmpty()) {
            throw new NotFoundException("Artifact \"%s\" is not found".formatted(id));
        } else {
            artifactRepo.deleteById(id);
        }
    }

    @Transactional
    public ArtifactDto update(String id, ArtifactUpdateRequest request, User user) {
        UUID uuid = getUuid(id);
        return update(uuid, request, user);
    }

    @Transactional
    public void delete(String id, User user) {
        UUID uuid = getUuid(id);
        delete(uuid, user);
    }

    @Transactional
    public ArtifactDto getArtifact(String id) {
        UUID uuid = getUuid(id);
        return getArtifact(uuid);
    }

    private ArtifactDto getArtifact(UUID uuid) {
        Artifact artifact = artifactRepo.getById(uuid);
        return mapper.toDTO(artifact);
    }

    @Transactional
    public List<ArtifactDto> getArtifacts(ArtifactSortRequest request) {
        Direction direction = Direction.fromString(request.getDirection());
        return mapper.toDTO(artifactRepo.findAll(Sort.by(direction, request.getFields())));
    }

    private UUID getUuid(String s) {
        return UUID.fromString(s);
    }

    @Transactional
    public List<ArtifactDto> getArtifacts() {
        return mapper.toDTO(artifactRepo.findAll());
    }

    @Transactional
    public List<ArtifactDto> getArtifacts(String search) {
        List<Artifact> artifacts = artifactRepo.findAllByCategoryOrUserOrDescriptionOrComment(search);
        return mapper.toDTO(artifacts);
    }
}
