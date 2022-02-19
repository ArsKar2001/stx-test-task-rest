package com.stx.domains.dtos;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class UserDto implements Serializable {
    private UUID id;
    private String username;
    private String password;
    private Boolean enabled;
    private Set<ArtifactDto> artifacts;
    private Set<CommentDto> comments;

    public UserDto() {
    }

    public UserDto(UUID id, String username, String password, Boolean enabled, Set<ArtifactDto> artifacts, Set<CommentDto> comments) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.artifacts = artifacts;
        this.comments = comments;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<ArtifactDto> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(Set<ArtifactDto> artifacts) {
        this.artifacts = artifacts;
    }

    public Set<CommentDto> getComments() {
        return comments;
    }

    public void setComments(Set<CommentDto> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto entity = (UserDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.username, entity.username) &&
                Objects.equals(this.password, entity.password) &&
                Objects.equals(this.enabled, entity.enabled) &&
                Objects.equals(this.artifacts, entity.artifacts) &&
                Objects.equals(this.comments, entity.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, enabled, artifacts, comments);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "username = " + username + ", " +
                "password = " + password + ", " +
                "enabled = " + enabled + ", " +
                "artifacts = " + artifacts + ", " +
                "comments = " + comments + ")";
    }
}
