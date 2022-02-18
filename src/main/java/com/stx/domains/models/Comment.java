package com.stx.domains.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(schema = "PUBLIC", name = "COMMENTS")
public class Comment extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ARTIFACT_ID")
    private Artifact artifact;

    private String content;

    public Artifact getArtifact() {
        return artifact;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
