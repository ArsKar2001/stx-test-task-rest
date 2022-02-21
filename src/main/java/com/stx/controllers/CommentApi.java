package com.stx.controllers;

import com.stx.domains.dtos.CommentCreateRequest;
import com.stx.domains.dtos.CommentDto;
import com.stx.domains.models.User;
import com.stx.services.CommentService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comment")
public class CommentApi {
    private final Logger logger = Logger.getLogger(getClass());
    private final CommentService commentService;

    public CommentApi(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping()
    public ResponseEntity<?> get(@RequestParam String uuid) {
        CommentDto dto = commentService.getComment(uuid);
        return ResponseEntity.ok()
                .body(dto);
    }

    @GetMapping("search")
    public ResponseEntity<?> getAll(@RequestParam String search) {
        List<CommentDto> dtos = commentService.getComments(search);
        return ResponseEntity.ok()
                .body(dtos);
    }

    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal User user, @RequestBody CommentCreateRequest request) {
        CommentDto dto = commentService.create(user, request);
        logger.info("Comment create: " + dto.getId());
        return ResponseEntity.ok()
                .body(dto);
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@RequestParam String uuid, @AuthenticationPrincipal User user, @RequestBody CommentCreateRequest request) {
        CommentDto dto = commentService.upset(user, request, uuid);
        logger.info("Comment update: " + uuid);
        return ResponseEntity.ok()
                .body(dto);
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam String uuid, @AuthenticationPrincipal User user) {
        commentService.delete(user, uuid);
        logger.info("Comment delete: " + uuid);
        return ResponseEntity.ok().build();
    }
}
