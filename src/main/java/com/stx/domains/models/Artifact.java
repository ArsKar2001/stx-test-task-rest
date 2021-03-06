package com.stx.domains.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ARTIFACTS", schema = "PUBLIC")
public class Artifact extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    private LocalDateTime created;
    private String category;
    private String description;

    public User getUser() {
        return user;
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
}
