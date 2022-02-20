package com.stx.daos;

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

    @Query("SELECT DISTINCT a FROM Artifact a " +
            "LEFT JOIN Comment c ON c.user = a.user " +
            "WHERE lower(a.category) LIKE lower(:search) OR " +
            "lower(a.category) LIKE lower(concat('%', :search, '%')) OR " +
            "lower(a.description) LIKE lower(:search) OR " +
            "lower(a.description) LIKE lower(concat('%', :search, '%')) OR " +
            "lower(a.user.username) LIKE lower(:search) OR " +
            "lower(a.user.username) LIKE lower(concat('%', :search, '%')) OR " +
            "lower(c.content) LIKE lower(:search) OR " +
            "lower(c.content) LIKE lower(concat('%', :search, '%')) " +
            "ORDER BY 1 ASC")
    List<Artifact> findAllByCategoryOrUserOrDescriptionOrComment(@Param("search") String search);
}
