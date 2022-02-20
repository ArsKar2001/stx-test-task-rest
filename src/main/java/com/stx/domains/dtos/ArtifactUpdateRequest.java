package com.stx.domains.dtos;

import java.util.UUID;

public class ArtifactUpdateRequest {
    private String category;
    private String description;


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
}
