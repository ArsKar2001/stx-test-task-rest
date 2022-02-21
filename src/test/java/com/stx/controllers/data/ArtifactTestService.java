package com.stx.controllers.data;

import com.stx.domains.daos.ArtifactRepo;
import org.springframework.stereotype.Service;

@Service
public class ArtifactTestService {
    private final ArtifactRepo artifactRepo;

    public ArtifactTestService(ArtifactRepo artifactRepo) {
        this.artifactRepo = artifactRepo;
    }


}
