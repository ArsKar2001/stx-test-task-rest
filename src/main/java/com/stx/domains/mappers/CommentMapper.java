package com.stx.domains.mappers;

import com.stx.domains.dtos.CommentCreateRequest;
import com.stx.domains.dtos.CommentDto;
import com.stx.domains.models.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentMapper implements ModelDTOMapper<Comment, CommentDto> {
    private final ModelMapper mapper;

    public CommentMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public CommentDto toDTO(Comment t) {
        return mapper.map(t, CommentDto.class);
    }

    @Override
    public Comment toModel(CommentDto f) {
        return mapper.map(f, Comment.class);
    }

    public Comment create(CommentCreateRequest request) {
        return mapper.map(request, Comment.class);
    }

    public void update(Comment comment, CommentCreateRequest request) {
        comment.setContent(request.getContent());
    }

    public List<CommentDto> toDTO(List<Comment> comments) {
        return comments.stream()
                .map(this::toDTO)
                .toList();
    }
}
