package com.stx.domains.dtos;

import java.util.UUID;

public class CommentCreateRequest {
    private UUID artifactId;
    private String content;

    public UUID getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(UUID artifactId) {
        this.artifactId = artifactId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
