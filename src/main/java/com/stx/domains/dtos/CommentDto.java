package com.stx.domains.dtos;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class CommentDto implements Serializable {
    private UUID id;
    private String content;
    private ArtifactDto artifact;
    private UserDto user;

    public CommentDto() {
    }

    public CommentDto(UUID id, String content) {
        this.id = id;
        this.content = content;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArtifactDto getArtifact() {
        return artifact;
    }

    public void setArtifact(ArtifactDto artifact) {
        this.artifact = artifact;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDto entity = (CommentDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.content, entity.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "content = " + content + ")";
    }
}
