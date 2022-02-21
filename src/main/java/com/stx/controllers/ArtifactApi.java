package com.stx.controllers;

import com.stx.domains.dtos.*;
import com.stx.domains.models.User;
import com.stx.services.ArtifactService;
import org.apache.log4j.Logger;
import org.hibernate.annotations.ParamDef;
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
    public ResponseEntity<?> upset(@AuthenticationPrincipal User user, @RequestBody ArtifactCreateRequest request) {
        ArtifactDto dto = artifactService.upset(request, user);
        logger.info("Artifact: " + dto);
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCEPT)
                .body(dto);
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user, @RequestParam String uuid) {
        artifactService.delete(uuid, user);
        logger.info("Artifact delete: " + uuid);
        return ResponseEntity.ok()
                .body(uuid);
    }

    @GetMapping()
    public ResponseEntity<?> get(@RequestParam String uuid) {
        ArtifactDto dto = artifactService.getArtifact(uuid);
        return ResponseEntity.ok()
                .body(dto);
    }

    @GetMapping("sort")
    public ResponseEntity<?> getAll(@RequestBody ArtifactSortRequest request) {
        List<ArtifactDto> dtos = artifactService.getArtifacts(request);
        return ResponseEntity.ok()
                .body(dtos);
    }

    @GetMapping("all")
    public ResponseEntity<?> getAll() {
        List<ArtifactDto> dtos = artifactService.getArtifacts();
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCEPT)
                .body(dtos);
    }

    @GetMapping("search")
    public ResponseEntity<?> getAll(@RequestParam String search) {
        List<ArtifactDto> dtos = artifactService.getArtifacts(search);
        return ResponseEntity.ok()
                .body(dtos);
    }


}
