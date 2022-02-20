package com.stx.daos;

import com.stx.domains.models.Artifact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArtifactRepo extends JpaRepository<Artifact, UUID> {
}
