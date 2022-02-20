package com.stx.domains.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class ArtifactDto implements Serializable {
    private UUID id;
    private LocalDateTime created;
    private String category;
    private String description;

    public ArtifactDto() {
    }

    public ArtifactDto(UUID id, LocalDateTime created, String category, String description) {
        this.id = id;
        this.created = created;
        this.category = category;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtifactDto entity = (ArtifactDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.created, entity.created) &&
                Objects.equals(this.category, entity.category) &&
                Objects.equals(this.description, entity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, category, description);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "created = " + created + ", " +
                "category = " + category + ", " +
                "description = " + description + ")";
    }
}
