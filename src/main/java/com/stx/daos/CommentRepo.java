package com.stx.daos;

import com.stx.domains.models.Comment;
import com.stx.domains.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepo extends JpaRepository<Comment, UUID> {
    Optional<Comment> getByIdAndUser(UUID id, User user);

    List<Comment> findAllByContentOrderByContentAsc(String content);
}
