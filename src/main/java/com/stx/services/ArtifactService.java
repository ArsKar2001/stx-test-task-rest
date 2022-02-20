package com.stx.services;

import com.stx.daos.ArtifactRepo;
import com.stx.daos.UserRepo;
import com.stx.domains.dtos.ArtifactCreateRequest;
import com.stx.domains.dtos.ArtifactDto;
import com.stx.domains.mappers.ArtifactMapper;
import com.stx.domains.models.Artifact;
import com.stx.domains.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public ArtifactDto create(ArtifactCreateRequest request, User user) {
        Artifact artifact = mapper.create(request);
        artifact.setUser(user);
        artifact = artifactRepo.save(artifact);
        return mapper.toDTO(artifact);
    }
}
