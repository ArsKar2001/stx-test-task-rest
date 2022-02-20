package com.stx.services;

import com.stx.daos.ArtifactRepo;
import com.stx.daos.CommentRepo;
import com.stx.domains.dtos.CommentCreateRequest;
import com.stx.domains.dtos.CommentDto;
import com.stx.domains.exceptions.NotFoundException;
import com.stx.domains.mappers.CommentMapper;
import com.stx.domains.models.Artifact;
import com.stx.domains.models.Comment;
import com.stx.domains.models.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {
    private final Logger logger = Logger.getLogger(getClass());
    private final CommentRepo commentRepo;
    private final ArtifactRepo artifactRepo;
    private final CommentMapper mapper;

    public CommentService(CommentRepo commentRepo, ArtifactRepo artifactRepo, CommentMapper mapper) {
        this.commentRepo = commentRepo;
        this.artifactRepo = artifactRepo;
        this.mapper = mapper;
    }

    @Transactional
    public CommentDto create(User user, CommentCreateRequest request) {
        Optional<Artifact> optionalArtifact = artifactRepo.findByUserAndId(user, request.getArtifactId());
        if (optionalArtifact.isEmpty()) {
            throw new NotFoundException("Artifact \"%s\" is not fond".formatted(request.getArtifactId()));
        } else {
            Artifact artifact = optionalArtifact.get();
            Comment comment = mapper.create(request);
            comment.setArtifact(artifact);
            comment.setUser(user);

            comment = commentRepo.save(comment);
            return mapper.toDTO(comment);
        }
    }

    @Transactional
    public CommentDto update(User user, CommentCreateRequest request, String uuid) {
        UUID id = getUuid(uuid);
        return update(user, request, id);
    }

    @Transactional
    public void delete(User user, String uuid) {
        UUID id = getUuid(uuid);
        delete(user, id);
    }

    private void delete(User user, UUID uuid) {
        Optional<Comment> optionalComment = commentRepo.getByIdAndUser(uuid, user);
        if (optionalComment.isEmpty()) {
            throw new NotFoundException(("Comment \"%s\" is not fond").formatted(uuid));
        } else {
            Comment comment = optionalComment.get();
            commentRepo.delete(comment);
        }
    }

    private CommentDto update(User user, CommentCreateRequest request, UUID uuid) {
        Optional<Comment> optionalComment = commentRepo.getByIdAndUser(uuid, user);
        if (optionalComment.isEmpty()) {
            return create(user, request);
        } else {
            Comment comment = optionalComment.get();
            mapper.update(comment, request);
            comment = commentRepo.save(comment);
            return mapper.toDTO(comment);
        }
    }

    private UUID getUuid(String uuid) {
        return UUID.fromString(uuid);
    }

    @Transactional
    public CommentDto getComment(String uuid) {
        UUID id = getUuid(uuid);
        return getComment(id);
    }

    private CommentDto getComment(UUID id) {
        return mapper.toDTO(commentRepo.getById(id));
    }

    public List<CommentDto> getComments(String search) {
        return mapper.toDTO(commentRepo.findAllByContentOrderByContentAsc(search));
    }
}
