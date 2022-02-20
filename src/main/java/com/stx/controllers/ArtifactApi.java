package com.stx.controllers;

import com.stx.domains.dtos.*;
import com.stx.domains.models.User;
import com.stx.services.ArtifactService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/artifact")
public class ArtifactApi {
    private final Logger logger = Logger.getLogger(getClass());
    private final ArtifactService artifactService;


    public ArtifactApi(ArtifactService artifactService) {
        this.artifactService = artifactService;
    }

    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal User user, @RequestBody ArtifactCreateRequest request) {
        ArtifactDto dto = artifactService.upset(request, user);
        logger.info("Artifact create: " + dto);
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCEPT)
                .body(dto);
    }

    @PostMapping("{uuid}")
    public ResponseEntity<?> update(@AuthenticationPrincipal User user, @RequestBody ArtifactUpdateRequest request, @PathVariable String uuid) {
        ArtifactDto dto = artifactService.update(uuid, request, user);
        logger.info("Artifact update: " + dto);
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCEPT)
                .body(dto);
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user, @PathVariable String uuid) {
        artifactService.delete(uuid, user);
        logger.info("Artifact delete: " + uuid);
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCEPT)
                .body(uuid);
    }

    @GetMapping("{uuid}")
    public ResponseEntity<?> get(@PathVariable String uuid) {
        ArtifactDto dto = artifactService.getArtifact(uuid);
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCEPT)
                .body(dto);
    }

    @GetMapping("sort")
    public ResponseEntity<?> getAll(@RequestBody ArtifactSortRequest request) {
        List<ArtifactDto> dtos = artifactService.getArtifacts(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCEPT)
                .body(dtos);
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        List<ArtifactDto> dtos = artifactService.getArtifacts();
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCEPT)
                .body(dtos);
    }

    @GetMapping("{search}")
    public ResponseEntity<?> getAll(@PathVariable String search) {
        List<ArtifactDto> dtos = artifactService.getArtifacts(search);
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCEPT)
                .body(dtos);
    }
}
