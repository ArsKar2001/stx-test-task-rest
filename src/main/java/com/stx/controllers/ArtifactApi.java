package com.stx.controllers;

import com.stx.domains.dtos.ArtifactCreateRequest;
import com.stx.domains.dtos.ArtifactDto;
import com.stx.domains.models.User;
import com.stx.services.ArtifactService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
        try {
            ArtifactDto dto = artifactService.create(request, user);
            logger.info("Artifact create: " + dto);
            return ResponseEntity.ok()
                    .header(HttpHeaders.ACCEPT)
                    .body(dto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .header(HttpHeaders.WARNING)
                    .build();
        }
    }
}
