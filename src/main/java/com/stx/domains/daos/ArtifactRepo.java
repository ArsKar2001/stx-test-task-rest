package com.stx.domains.daos;

import com.stx.domains.models.Artifact;
import com.stx.domains.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArtifactRepo extends JpaRepository<Artifact, UUID> {
    Optional<Artifact> findByUserAndId(User user, UUID id);

    @Query("SELECT a FROM Artifact a " +
            "WHERE lower(a.category) LIKE lower(concat('%', :search, '%')) OR " +
            "lower(a.description) LIKE lower(concat('%', :search, '%')) OR " +
            "EXISTS (SELECT c FROM a.comments c WHERE lower(c.content) LIKE lower(concat('%', :search, '%'))) OR " +
            "lower(a.user.username) LIKE lower(concat('%', :search, '%')) " +
            "GROUP BY a.id " +
            "ORDER BY a.created DESC ")
    List<Artifact> findAllBySearch(@Param("search") String search);
}
